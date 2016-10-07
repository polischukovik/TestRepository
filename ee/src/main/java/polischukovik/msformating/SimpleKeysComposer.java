package polischukovik.msformating;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.springframework.beans.factory.annotation.Autowired;

import polischukovik.domain.Question;
import polischukovik.domain.Test;
import polischukovik.domain.Variant;
import polischukovik.domain.enums.PropertyNames;
import polischukovik.msformating.interfaces.DocumentComponentComposer;
import polischukovik.Application;
import polischukovik.mslibrary.Properties;

public class SimpleKeysComposer implements DocumentComponentComposer{
	@Autowired
	private static Properties prop;
	
	private static final String pPunctuationKeyAnswer = prop.get(PropertyNames.P_PUNCTUATION_KEY_ANSWER);
	private static final String resKeyTytle = prop.get(PropertyNames.T_KEY_TITLE);
	
	@Override
	public void constructComponent(Test test, XWPFDocument doc) {
		//Add title
		XWPFParagraph p = doc.createParagraph();
		p.setAlignment(ParagraphAlignment.CENTER);
		p.setPageBreak(true);
		XWPFRun r = p.createRun();
		
		r.setText(resKeyTytle);
		
		//Add keys for each variants
		for(Variant v : test.getVariants()){
			XWPFParagraph pVariant = doc.createParagraph();
			XWPFRun rVariant = pVariant.createRun();
			
			rVariant.setText(String.format("%s", v.getName()));
			
			XWPFParagraph pQuestion = doc.createParagraph();
			
			//Create row for each answer
			for(Question q : v.getQuestions()){
				XWPFRun rQuestion = pQuestion.createRun();
				
				rQuestion.setText(String.format("%s %s %s", q.getId(), pPunctuationKeyAnswer, v.getKeys().get(q)));
				rQuestion.addBreak();
			}
		}
	}

	@Override
	public List<PropertyNames> getRequiredProp() {
		return new ArrayList<>(Arrays.asList(
				new PropertyNames[]{
						PropertyNames.P_PUNCTUATION_KEY_ANSWER, 
						PropertyNames.T_KEY_TITLE}));
	}
}
