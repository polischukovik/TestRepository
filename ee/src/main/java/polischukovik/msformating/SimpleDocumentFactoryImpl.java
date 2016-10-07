package polischukovik.msformating;

import java.util.List;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.beans.factory.annotation.Autowired;

import polischukovik.domain.Test;
import polischukovik.msformating.interfaces.DocumentComponentComposer;
import polischukovik.msformating.interfaces.DocumentFactory;
import polischukovik.mslibrary.Properties;

public class SimpleDocumentFactoryImpl implements DocumentFactory{
	@Autowired
	private Properties prop;//unused here
	
	private Test test;
	private XWPFDocument doc;
	
	public SimpleDocumentFactoryImpl(Test test) {
		this.test = test;
	}

	@Override
	public XWPFDocument createDocument(List<Class<? extends DocumentComponentComposer>> domponentComposers) throws ClassNotFoundException {
		doc = new XWPFDocument();
		
		for(Class<? extends DocumentComponentComposer> clazz: domponentComposers){
			try {
				clazz.newInstance().constructComponent(test, doc);
			} catch (InstantiationException | IllegalAccessException e) {
				throw new ClassNotFoundException(String.format("Cannot instantiate required class: %s", clazz.getName()));
			}
		}
	
		return doc;
	}
}