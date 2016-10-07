package polischukovik.msformating;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import polischukovik.domain.Test;
import polischukovik.msformating.interfaces.DocumentComponentComposer;
import polischukovik.msformating.interfaces.DocumentFactory;
import polischukovik.mslibrary.Properties;

public class SimpleDocumentFactoryImpl implements DocumentFactory{
	@SuppressWarnings("unused")
	private Properties prop;//unused here
	
	private Test test;
	private XWPFDocument doc;
	
	private DocumentComponentComposer documentTitleComposer = new SimpleTitleComposer();
	private DocumentComponentComposer documentVariantComposer = new SimpleVariantComposer();
	private DocumentComponentComposer documentKeysComposer = new SimpleKeysComposer();

	public SimpleDocumentFactoryImpl(Test test, Properties prop) {
		this.test = test;
		this.prop = prop;		
	}
	
	public XWPFDocument createDocument(){		
		doc = new XWPFDocument();
		documentTitleComposer.constructComponent(test, doc);
		documentVariantComposer.constructComponent(test, doc);		
		documentKeysComposer.constructComponent(test, doc);
		return doc;		
	}
}