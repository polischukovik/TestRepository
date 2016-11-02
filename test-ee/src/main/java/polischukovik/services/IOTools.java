package polischukovik.services;

import java.io.FileNotFoundException;

import org.apache.poi.xwpf.usermodel.XWPFDocument;

public interface IOTools {
	public String read() throws FileNotFoundException;
	public void write(XWPFDocument doc) throws Exception;
}
