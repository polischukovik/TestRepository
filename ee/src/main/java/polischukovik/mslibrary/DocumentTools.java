package polischukovik.mslibrary;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSpacing;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STLineSpacingRule;
import org.springframework.beans.factory.annotation.Autowired;

import polischukovik.domain.enums.PropertyNames;

public class DocumentTools {
	@Autowired
	private Properties prop;
	
	private String pDestFileName;
	
	public DocumentTools() {
		pDestFileName = prop.get(PropertyNames.IO_DEST_FILE_NAME);
	}

	public void write(XWPFDocument doc) throws IOException {
		OutputStream os = new FileOutputStream(new File(pDestFileName));

		if(doc == null){
			System.err.println("Document reference is null");
			os.close();
			return;
		}
		
		doc.write(os);
		os.close();
	}
	
	public static void setSingleLineSpacing(XWPFParagraph para) {
	    CTPPr ppr = para.getCTP().getPPr();
	    if (ppr == null) ppr = para.getCTP().addNewPPr();
	    CTSpacing spacing = ppr.isSetSpacing()? ppr.getSpacing() : ppr.addNewSpacing();
	    spacing.setAfter(BigInteger.valueOf(0));
	    spacing.setBefore(BigInteger.valueOf(0));
	    spacing.setLineRule(STLineSpacingRule.AUTO);
	    spacing.setLine(BigInteger.valueOf(240));
	}
}
