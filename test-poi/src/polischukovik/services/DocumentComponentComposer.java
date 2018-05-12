package polischukovik.services;

import org.apache.poi.xwpf.usermodel.XWPFDocument;

import polischukovik.domain.Test;

public interface DocumentComponentComposer {
	public XWPFDocument constructComponent(Test test);
	public String getComposerName();
	public String getPlaceHolder();
	
}
