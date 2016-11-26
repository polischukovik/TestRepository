package polischukovik.services;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import polischukovik.domain.Test;

public interface DocumentComponentComposer {
	public void constructComponent(Test test, XWPFDocument doc);
	public String getComposerName();
	
}
