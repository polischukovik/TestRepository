package polischukovik.mslibrary;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTBody;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTColumns;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTDocument1;
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
	    spacing.setLine(BigInteger.valueOf(240));
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

}
