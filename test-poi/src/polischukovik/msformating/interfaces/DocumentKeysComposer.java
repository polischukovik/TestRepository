package polischukovik.msformating.interfaces;

import org.apache.poi.xwpf.usermodel.XWPFDocument;

import polischukovik.domain.Test;

public interface DocumentKeysComposer {
	public void addKeys(Test test, XWPFDocument doc);
}
