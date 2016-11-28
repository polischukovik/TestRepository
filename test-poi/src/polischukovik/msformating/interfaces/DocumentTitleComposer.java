package polischukovik.msformating.interfaces;

import org.apache.poi.xwpf.usermodel.XWPFDocument;

import polischukovik.domain.Test;

public interface DocumentTitleComposer {
	public void addDocumentTite(Test test, XWPFDocument doc);
}
