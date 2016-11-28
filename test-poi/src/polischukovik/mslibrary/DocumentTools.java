package polischukovik.mslibrary;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSpacing;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STLineSpacingRule;

public class DocumentTools {
	public static void setSingleLineSpacing(XWPFParagraph para) {
	    CTPPr ppr = para.getCTP().getPPr();
	    if (ppr == null) ppr = para.getCTP().addNewPPr();
	    CTSpacing spacing = ppr.isSetSpacing()? ppr.getSpacing() : ppr.addNewSpacing();
	    spacing.setAfter(BigInteger.valueOf(0));
	    spacing.setBefore(BigInteger.valueOf(0));
	    spacing.setLineRule(STLineSpacingRule.AUTO);
	    spacing.setLine(BigInteger.valueOf(240));
	}
	
	public static void write(OutputStream os, XWPFDocument doc) throws IOException {
		if(os == null){
			System.err.println("Output strem is not initialized");
			return;
		}
		doc.write(os);		
	}

}
