package polischukovik;

import java.io.IOException;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import polischukovik.mslibrary.DocumentTools;

public class Application{

	public static void main(String[] args) throws IOException {
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfiguration.class);

		XWPFDocument doc = ctx.getBean(XWPFDocument.class);		
		DocumentTools dt =  ctx.getBean(DocumentTools.class);
		
		dt.write(doc);
		
		ctx.close();
	
	}
}