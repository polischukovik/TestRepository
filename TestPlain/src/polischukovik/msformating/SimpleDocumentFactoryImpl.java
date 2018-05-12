package polischukovik.msformating;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import polischukovik.domain.Test;
import polischukovik.msformating.interfaces.DocumentFactory;
import polischukovik.msformating.interfaces.DocumentKeysComposer;
import polischukovik.msformating.interfaces.DocumentTitleComposer;
import polischukovik.msformating.interfaces.DocumentVariantComposer;
import polischukovik.mslibrary.Properties;

public class SimpleDocumentFactoryImpl implements DocumentFactory{
	@SuppressWarnings("unused")
	private Properties prop;//unused here
	
	private Test test;
	private XWPFDocument doc;
	
	private DocumentTitleComposer documentTitleComposer = new SimpleTitleComposer();
	private DocumentVariantComposer documentVariantComposer = new SimpleVariantComposer();
	private DocumentKeysComposer documentKeysComposer = new SimpleKeysComposer();

	public SimpleDocumentFactoryImpl(Test test, Properties prop) {
		this.test = test;
		this.prop = prop;		
	}
	
	public XWPFDocument createDocument(){		
		doc = new XWPFDocument();
		documentTitleComposer.addDocumentTite(test, doc);
		documentVariantComposer.addVariants(test, doc);		
		documentKeysComposer.addKeys(test, doc);
		return doc;		
	}
}