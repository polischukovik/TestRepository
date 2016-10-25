package polischukovik.msformating;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import polischukovik.domain.Test;
import polischukovik.domain.enums.PropertyNames;
import polischukovik.msformating.interfaces.DocumentComponentComposer;
import polischukovik.msformating.interfaces.DocumentFactory;
import polischukovik.mslibrary.Properties;

@Component
public class SimpleDocumentFactoryImpl implements DocumentFactory{
	@SuppressWarnings("unused")
	@Autowired
	private Properties prop;//unused here	
	private XWPFDocument doc;
	
	private static List<PropertyNames> requiredProps = Arrays.asList();
	
	public SimpleDocumentFactoryImpl() {
	}

	@Override
	public XWPFDocument createDocument(Test test, List<? extends DocumentComponentComposer> componentComposers) throws ClassNotFoundException {
		doc = new XWPFDocument();
		
		for(DocumentComponentComposer dc: componentComposers){
			dc.constructComponent(test, doc);
		}
	
		return doc;
	}

	public static List<PropertyNames> getRequiredProperties() {
		return new ArrayList<>(requiredProps);
	}
}