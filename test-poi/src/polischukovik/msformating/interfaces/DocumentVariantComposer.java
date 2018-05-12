package polischukovik.msformating.interfaces;

import org.apache.poi.xwpf.usermodel.XWPFDocument;

import polischukovik.domain.Test;

public interface DocumentVariantComposer {

	void addVariants(Test test, XWPFDocument doc);

}
