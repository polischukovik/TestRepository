package polischukovik.msformating.interfaces;

import java.util.List;

import org.apache.poi.xwpf.usermodel.XWPFDocument;

import polischukovik.domain.Test;
import polischukovik.domain.enums.PropertyNames;

public interface DocumentComponentComposer {
	public void constructComponent(Test test, XWPFDocument doc);
	public List<PropertyNames> getRequiredProp();
}
