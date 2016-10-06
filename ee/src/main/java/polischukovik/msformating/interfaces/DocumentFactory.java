package polischukovik.msformating.interfaces;

import java.io.IOException;
import java.io.OutputStream;

import org.apache.poi.xwpf.usermodel.XWPFDocument;

public interface DocumentFactory {
	public XWPFDocument createDocument();
}
