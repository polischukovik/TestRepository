package polischukovik.msformating.interfaces;

import java.util.List;

import org.apache.poi.xwpf.usermodel.XWPFDocument;

import polischukovik.domain.Test;

public interface DocumentFactory {
	public XWPFDocument createDocument(Test test, List<? extends DocumentComponentComposer> df) throws ClassNotFoundException;

}
