package polischukovik.services;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.xwpf.usermodel.XWPFDocument;

public interface IOTools {
	public String read() throws FileNotFoundException, IOException;
	public XWPFDocument readDoc(String path) throws IOException;
	
	public void write(XWPFDocument doc) throws IOException;	
}
