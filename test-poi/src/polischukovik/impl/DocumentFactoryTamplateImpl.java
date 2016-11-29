package polischukovik.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlOptions;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTBody;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import polischukovik.domain.Test;
import polischukovik.domain.enums.PropertyName;
import polischukovik.main.Main;
import polischukovik.mslibrary.MSLib;
import polischukovik.properties.Properties;
import polischukovik.properties.RequiredPropertyNameProvider;
import polischukovik.services.DocumentComponentComposer;
import polischukovik.services.DocumentFactory;

@Component
public class DocumentFactoryTamplateImpl implements RequiredPropertyNameProvider{
	@Autowired
	private Properties prop;//unused here	
	private XWPFDocument doc;
	
	private static List<PropertyName> requiredProps = Arrays.asList();
	
	public DocumentFactoryTamplateImpl() {
		prop = Main.ctx.getBean(Properties.class);
	}

	public XWPFDocument createDocument(Test test, List<ComposerTitleAdvanced> componentComposers) throws Exception {
		//{Title}
		XWPFDocument template = readTamplate("template.docx");
        
        doc = new XWPFDocument();
        doc.createParagraph().createRun().setText("[New text]");;        
        
        MSLib.replacePlaceholderWithBody("{placeholder}", template, componentComposers.get(0).constructComponent(test));

		return template;
	}
	
	private XWPFDocument readTamplate(String pathFileTamplate) {
		File file = null;
        XWPFDocument document = null;
        try
        {
            file = new File(pathFileTamplate);
            FileInputStream fis = new FileInputStream(file.getAbsolutePath());
            document = new XWPFDocument(fis);           
        }
        catch (Exception exep)
        {
            exep.printStackTrace();
        }
		return document;
	}

	private XWPFDocument processComponent(XWPFDocument doc2, ComposerTitleAdvanced dc) {
		XWPFDocument doc = new XWPFDocument();
		for(XWPFParagraph p : doc2.getParagraphs()){
			CTP ctp = doc.createParagraph().getCTP();
			ctp = p.getCTP();				
		}
		return doc;
	}

	public List<PropertyName> getRequiredProperties() {
		return new ArrayList<>(requiredProps);
	}
}