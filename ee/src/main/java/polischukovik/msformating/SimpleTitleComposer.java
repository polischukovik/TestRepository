package polischukovik.msformating;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import polischukovik.domain.Test;
import polischukovik.domain.enums.PropertyNames;
import polischukovik.msformating.interfaces.DocumentComponentComposer;
import polischukovik.mslibrary.Properties;

@Component
public class SimpleTitleComposer implements DocumentComponentComposer {
	@SuppressWarnings("unused")
	@Autowired
	private Properties prop;	
	
	private static List<PropertyNames> requiredProps = Arrays.asList();
	
	private final String composerName = this.getClass().getName();

	public SimpleTitleComposer() {
	}

	@Override
	public void constructComponent(Test test, XWPFDocument doc) {
		XWPFParagraph p = doc.createParagraph();
		p.setAlignment(ParagraphAlignment.CENTER);
		XWPFRun r = p.createRun();
		r.addBreak();
		r.addBreak();
		r.addBreak();
		r.addBreak();
		r.setText(test.getCaption());		
		r.addBreak();		
	}

	@Override
	public String getComposerName() {
		return composerName;
	}

	public static List<PropertyNames> getRequiredProperties() {
		return new ArrayList<>(requiredProps);
	}
}
