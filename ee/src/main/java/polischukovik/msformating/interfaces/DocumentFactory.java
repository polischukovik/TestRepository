package polischukovik.msformating.interfaces;

import java.util.List;

import org.apache.poi.xwpf.usermodel.XWPFDocument;

public interface DocumentFactory {
	public XWPFDocument createDocument(List<Class<? extends DocumentComponentComposer>> domponentComposers) throws ClassNotFoundException;
}
