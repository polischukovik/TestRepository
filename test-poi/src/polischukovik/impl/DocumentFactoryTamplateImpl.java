package polischukovik.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
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
	private String pColumnMarging;
	private String pColumnNum;
	private String pPageFormat;
	private String pMargin;
	
	private static List<PropertyName> requiredProps = Arrays.asList(
			PropertyName.F_PAGE_COL_N
			, PropertyName.F_PAGE_COL_S
			, PropertyName.F_PAGE_FORMAT
			, PropertyName.F_PAGE_M);
	
	public DocumentFactoryTamplateImpl() {
		prop = Main.ctx.getBean(Properties.class);
	}

	public XWPFDocument createDocument(Test test, List<ComposerTitleAdvanced> componentComposers) throws Exception {
		pColumnNum = prop.get(PropertyName.F_PAGE_COL_N);
		pColumnMarging = prop.get(PropertyName.F_PAGE_COL_S);
		pMargin = prop.get(PropertyName.F_PAGE_M);
		pPageFormat = prop.get(PropertyName.F_PAGE_FORMAT);

		//{Title}
		@SuppressWarnings("unused")
		XWPFDocument template = readTamplate("template.docx");
       
        int i =0;
        for (XWPFParagraph p : template.getParagraphs())
        {
        	System.out.println("Parafraph: " + i++);
            System.out.println("Text: " + p.getText());                
        }
        
        doc = new XWPFDocument();
        doc.createParagraph().createRun().setText("original text");;        
        
//        for(ComposerTitleAdvanced dc: componentComposers){
//        	doc = processComponent(template, dc);
//        }			
//        
		MSLib.setDocumentParameters(doc, pPageFormat, pMargin.split(" "));
		MSLib.setPageColumnLayout(doc, Double.valueOf(pColumnMarging), Integer.valueOf(pColumnNum));
		
//		for(DocumentComponentComposer dc: componentComposers){
//			dc.constructComponent(test, doc);
//		}
	
		return merge(template,doc);
	}
	
	public static XWPFDocument merge(XWPFDocument src1Document, XWPFDocument src2Document) throws Exception {    
	    CTBody src1Body = src1Document.getDocument().getBody();
	    CTBody src2Body = src2Document.getDocument().getBody();        
	    appendBody(src1Body, src2Body);
	    return src1Document;
	}

	private static void appendBody(CTBody srcBody, CTBody targetBody) throws Exception {
	    XmlOptions optionsOuter = new XmlOptions();
	    optionsOuter.setSaveOuter();
	    String appendString = targetBody.xmlText(optionsOuter);
	    String srcString = srcBody.xmlText();
	    String prefix = srcString.substring(0,srcString.indexOf(">")+1);
	    String mainPart = srcString.substring(srcString.indexOf(">")+1,srcString.lastIndexOf("<"));
	    String sufix = srcString.substring( srcString.lastIndexOf("<") );
	    String addPart = appendString.substring(appendString.indexOf(">") + 1, appendString.lastIndexOf("<"));
	    CTBody makeBody = CTBody.Factory.parse(prefix+mainPart+addPart+sufix);
	    srcBody.set(makeBody);
	}

	private XWPFDocument processComponent(XWPFDocument doc2, ComposerTitleAdvanced dc) {
		XWPFDocument doc = new XWPFDocument();
		for(XWPFParagraph p : doc2.getParagraphs()){
//			XWPFParagraph oo = new XWPFParagraph(prgrph, part)
//			int pos = doc.getPosOfParagraph(doc.getLastParagraph());
			CTP ctp = doc.createParagraph().getCTP();
			ctp = p.getCTP();				
		}
		return doc;
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

	public List<PropertyName> getRequiredProperties() {
		return new ArrayList<>(requiredProps);
	}
}