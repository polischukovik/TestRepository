package polischukovik.mslibrary;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTBody;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTDocument1;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPageMar;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPageSz;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSectPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblWidth;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTcPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STPageOrientation;

public class MSLib {
	
	private static Map<String, Dimention> pageFormatList;
	static{
		pageFormatList = new HashMap<>();
		pageFormatList.put("A4", new Dimention(BigInteger.valueOf(16838), BigInteger.valueOf(11906)));
	}
	private static CTPageSz pageSize;
	private static CTPageMar margings;

	public static void setDocumentParameters(XWPFDocument doc) {
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
		pageSize.setW(pageFormatList.get("A4").getWidth());
		pageSize.setH(pageFormatList.get("A4").getHeight());
		
		margings.setTop(cm2dxa(2.54));
		margings.setBottom(cm2dxa(2.54));
		margings.setLeft(cm2dxa(2.54));
		margings.setRight(cm2dxa(2.54));
		
	}

	public static void setTableWidthToSection(XWPFDocument doc, XWPFTable table) {
		CTTblPr tblPr = table.getCTTbl().getTblPr();
		CTTblWidth tblPrW = tblPr.addNewTblW();
		tblPrW.setW(BigInteger.valueOf(pageSize.getW().longValue() - margings.getLeft().longValue() - margings.getRight().longValue()));
	}

	public static void setAutofitCell(XWPFTableCell cell) {
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
			
		cTTblWidth.setW(cm2dxa(0.75));
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

}
