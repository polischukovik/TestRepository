package polischukovik.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import polischukovik.domain.Test;
import polischukovik.domain.enums.PropertyName;
import polischukovik.main.Main;
import polischukovik.mslibrary.MSLib;
import polischukovik.properties.Properties;
import polischukovik.properties.RequiredPropertyNameProvider;
import polischukovik.services.DocumentComponentComposer;

@Component
public class DocumentFactoryTamplateImpl implements RequiredPropertyNameProvider{
	@SuppressWarnings("unused")
	@Autowired
	private Properties prop;//unused here	
	
	private static List<PropertyName> requiredProps = Arrays.asList();
	
	public DocumentFactoryTamplateImpl() {
		prop = Main.ctx.getBean(Properties.class);
	}

	public XWPFDocument createDocument(List<DocumentComponentComposer> componentComposers, Object ...params) throws Exception {
		Test test = (Test) params[0];
		XWPFDocument template = (XWPFDocument) params[1];
        
        for(DocumentComponentComposer composer : componentComposers){
        	MSLib.replacePlaceholderWithBody(composer.getPlaceHolder(), template, composer.constructComponent(test));
        }
        
		return template;
	}


	public List<PropertyName> getRequiredProperties() {
		return new ArrayList<>(requiredProps);
	}
}