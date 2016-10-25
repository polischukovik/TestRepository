package polischukovik.mslibrary;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSpacing;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STLineSpacingRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import polischukovik.domain.enums.PropertyNames;

@Component
public class IOTools {
	@Autowired
	private Properties prop;
	
	private static List<PropertyNames> requiredProps = Arrays.asList(
			 PropertyNames.IO_DEST_FILE_NAME);
	
	private String pDestFileName;
	
	public IOTools() {		
	}

	public void write(XWPFDocument doc) throws Exception {
		pDestFileName = prop.get(PropertyNames.IO_DEST_FILE_NAME);
		OutputStream os = new FileOutputStream(new File(pDestFileName));

		if(doc == null){
			os.close();
			throw new Exception("Document reference is null");			
		}
		
		doc.write(os);
		os.close();
	}
	
	public static List<PropertyNames> getRequiredProperties(){
		return new ArrayList<>(requiredProps);
	}
}
