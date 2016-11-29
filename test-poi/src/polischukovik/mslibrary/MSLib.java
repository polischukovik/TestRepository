package polischukovik.mslibrary;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlOptions;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTBody;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTColumns;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTDocument1;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTFonts;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPageMar;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPageSz;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSectPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSpacing;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblWidth;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTcPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STLineSpacingRule;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STPageOrientation;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STTblWidth;

public class MSLib {
	
	private static Map<String, Dimention> pageFormatList;
	static{
		pageFormatList = new HashMap<>();
		pageFormatList.put("A4", new Dimention(BigInteger.valueOf(16838), BigInteger.valueOf(11906)));
	}
	private static CTPageSz pageSize;
	private static CTPageMar margings;
	private static CTSectPr section;
	private static CTColumns cols;

	public static void setDocumentParameters(XWPFDocument doc, String pageFormat, String ...margin) {
		CTDocument1 document = doc.getDocument();
		CTBody body = document.getBody();

		if (!body.isSetSectPr()) {
		     body.addNewSectPr();
		}
		CTSectPr section = body.getSectPr();
		
		if (!section.isSetPgMar()) {
			section.addNewPgMar();
		}
		margings = section.getPgMar();

		if(!section.isSetPgSz()) {
		    section.addNewPgSz();
		}
		pageSize = section.getPgSz();

		pageSize.setOrient(STPageOrientation.PORTRAIT);
		pageSize.setW(pageFormatList.get(pageFormat).getWidth());
		pageSize.setH(pageFormatList.get(pageFormat).getHeight());
		
		margings.setTop(cm2dxa(Double.valueOf(margin[0])));
		margings.setBottom(cm2dxa(Double.valueOf(margin[1])));
		margings.setLeft(cm2dxa(Double.valueOf(margin[2])));
		margings.setRight(cm2dxa(Double.valueOf(margin[3])));
		
	}

	public static void setTableWidthToSection(XWPFDocument doc, XWPFTable table) {
		CTTblPr tblPr = table.getCTTbl().getTblPr();
		CTTblWidth tblPrW = tblPr.addNewTblW();
		tblPrW.setW(BigInteger.valueOf((pageSize.getW().longValue() - margings.getLeft().longValue() - margings.getRight().longValue()) / 2 - cols.getSpace().longValue() - 5));
	}

	public static void setCellWidth(XWPFTableCell cell, double cm) {
		CTTc cTTc = cell.getCTTc();
		
		CTTcPr cTTcPr;
		if (!cTTc.isSetTcPr()) {
			cTTcPr = cTTc.addNewTcPr();
		} else {
			cTTcPr = cTTc.getTcPr();
		}
		
		CTTblWidth cTTblWidth;
		if (!cTTcPr.isSetTcW()) {
			cTTblWidth = cTTcPr.addNewTcW();
		} else {
			cTTblWidth = cTTcPr.getTcW();
		}
			
		cTTblWidth.setW(cm2dxa(cm));
	}

	public static void setPageColumnLayout(XWPFDocument doc, Double cm, Integer pColumnNum) {
		section = doc.getDocument().getBody().getSectPr();
		cols = section.addNewCols();
		cols.setNum(BigInteger.valueOf(pColumnNum));
		cols.setSpace(cm2dxa(cm));
		
	}
	
	public static void setSingleLineSpacing(XWPFParagraph para) {
	    CTPPr ppr = para.getCTP().getPPr();
	    if (ppr == null) ppr = para.getCTP().addNewPPr();
	    CTSpacing spacing = ppr.isSetSpacing()? ppr.getSpacing() : ppr.addNewSpacing();
	    spacing.setAfter(BigInteger.valueOf(0));
	    spacing.setBefore(BigInteger.valueOf(0));
	    spacing.setLineRule(STLineSpacingRule.AT_LEAST);
	    spacing.setLine(BigInteger.valueOf(200));
	}
	
	public static void setSingleTableSpacing(XWPFTable table) {
	    CTTblPr ppr = table.getCTTbl().getTblPr();
	    if (ppr == null) ppr = table.getCTTbl().addNewTblPr();
	    CTTblWidth spacing = ppr.isSetTblCellSpacing() ? ppr.getTblCellSpacing() : ppr.addNewTblCellSpacing();
	    spacing.setType(STTblWidth.AUTO);
	    spacing.setW(BigInteger.valueOf(0));
	}
	
	public static BigInteger cm2dxa(double cm){
		return BigInteger.valueOf((long) Math.floor(cm / 2.54 * 72 * 20));
	}
	
	public static double dxa2cm(BigInteger dxa){
		return Math.floor(dxa.longValue() / 20 / 72 * 2.54);
	}
	
	public static BigInteger pt2dxa(BigInteger pt){
		return BigInteger.valueOf((long) Math.floor(pt.longValue() * 72 * 20));
	}
	
	public static BigInteger dxa2pt(BigInteger dxa){
		return BigInteger.valueOf((long) Math.floor(dxa.longValue() / 20 / 72));
	}

	public static void addSection(XWPFDocument doc) {		
		doc.getDocument().getBody().addNewSectPr();
	}

	public static void indentTableFromLeft(XWPFTable table, double cm) {
		CTTblPr tblPr = table.getCTTbl().getTblPr();
		CTTblWidth tblPrInd = tblPr.addNewTblInd();
		tblPrInd.setW(cm2dxa(cm));
	}

	public static void setFontStyle(XWPFRun run, int fontSize, String string) {
		run.setFontSize(fontSize);
		run.setFontFamily("Times New Roman");
		CTFonts rFonts = run.getCTR().getRPr().getRFonts();
		rFonts.setHAnsi("Times New Roman");
		rFonts.setCs("Times New Roman");
	}	
	
	public static void replacePlaceholderWithBody(String placeholder, XWPFDocument template, XWPFDocument sourceDocument){
		String documentTag =  getDocumentStartTag(template.getDocument().xmlText());
		String documentEndTag =  getDocumentEndTag();
		
		XmlOptions optionsOuter = new XmlOptions();
	    optionsOuter.setSaveOuter();
		
		CTBody aBody = template.getDocument().getBody();
		String templateString = aBody.xmlText();
		
		CTBody bBody = sourceDocument.getDocument().getBody();
		String sourceString = bBody.xmlText(optionsOuter);
		
		List<String> pStrings = MSLib.getParagraphContainingText(placeholder, templateString);
		
		for(String p : pStrings){
			templateString.replaceAll(Pattern.quote(p), sourceString);
		}
		
		CTBody makeBody = null;
		try {
			makeBody = CTBody.Factory.parse(documentTag + templateString + documentEndTag);
		} catch (XmlException e) {
			e.printStackTrace();
		}
		aBody.set(makeBody);		
	}
	/*
	 * Looking a PLACEHOLDER, searches <w:p before and </w:p> after it. 
	 * Creates A List of paragraphs to replace in future
	 */
	public static List<String> getParagraphContainingText(String placeholder, String templateString) {
		List<Integer[]> ranges = new ArrayList<>();
		List<String> result = new ArrayList<>(); 
		String start = "<w:p ";
		String end = "</w:p>";
		
		for(int i = 0; i < templateString.length(); i++){
			int index = templateString.indexOf(placeholder, i);
			if(index > 0){
				Integer[] range = new Integer[]{0, 0};
				range[0] = templateString.substring(0, index).lastIndexOf(start);
				range[1] = templateString.indexOf(end, index) + end.length();
				
				if(ranges.stream().filter(t -> t[0].equals(range[0]) && t[1].equals(range[1])).collect(Collectors.toList()).size() == 0){
					ranges.add(range);
				}
				i = index + 1;
			}else{
				break;
			}
		}		
		for(Integer[] r : ranges){
			result.add(templateString.substring(r[0], r[1]));
		}		
		return result;
	}
	
	private static String getDocumentStartTag(String srcString) {
		String tagname = "w:document";
		return extractRootTagElement(srcString).replace("xml-fragment", tagname);
	}
	
	private static String getDocumentEndTag() {
		String tagname = "w:document";
		return "</" + tagname + ">";
	}
	
	private static String extractRootTagElement(String srcString) {
		String startTag = "<";
		String endTag = ">";
		String tagname = "xml-fragment";
		int indexStart = srcString.indexOf(startTag + tagname );
		int indexEnd = srcString.indexOf(endTag) + 1;
		return srcString.substring(indexStart, indexEnd);
	}

}
