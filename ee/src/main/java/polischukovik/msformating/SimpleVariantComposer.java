package polischukovik.msformating;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.springframework.beans.factory.annotation.Autowired;

import polischukovik.domain.Answer;
import polischukovik.domain.Question;
import polischukovik.domain.Test;
import polischukovik.domain.Variant;
import polischukovik.domain.enums.PropertyNames;
import polischukovik.msformating.interfaces.DocumentComponentComposer;
import polischukovik.mslibrary.DocumentTools;
import polischukovik.Application;
import polischukovik.mslibrary.Properties;

public class SimpleVariantComposer implements DocumentComponentComposer {
	@Autowired
	private static Properties prop;

	private static final String pFQuestionBold = prop.get(PropertyNames.F_QUESTION_BOLD);
	private static final String pQuestionSpacing = prop.get(PropertyNames.F_QUESTION_SPACING);
	private static final String pQuestionPunctuation = prop.get(PropertyNames.P_PUNCTUATION_QUESTION);
	private static final String pAnswerPuncuation = prop.get(PropertyNames.P_PUNCTUATION_ANSWER);
	private static final String pMark = prop.get(PropertyNames.T_VARIANT_TITLE);

	@Override
	public void constructComponent(Test test, XWPFDocument doc) {
		List<Variant> variants = test.getVariants();		
		for(Variant v : variants){
			//Add caption
			XWPFParagraph p0 = doc.createParagraph();
			XWPFRun r0 = p0.createRun();
			p0.setAlignment(ParagraphAlignment.CENTER);
			p0.setPageBreak(true);
			
			String vLabel = v.getName();		
			r0.setText(String.format("%s %s", pMark, vLabel));
			r0.setBold(true);
						
			List<Question> questions = v.getQuestions();
			for(Question q : questions){
				XWPFParagraph questionParagpaph = doc.createParagraph();

				DocumentTools.setSingleLineSpacing(questionParagpaph);
				
				XWPFRun questionRun = questionParagpaph.createRun();
				questionRun.setText(String.format("%s%s %s",q.getId(), pQuestionPunctuation, q.getQuestion()));
				
				/*
				 * Set question Run to bold if parameter presented
				 */							
				questionRun.setBold(Boolean.valueOf(pFQuestionBold));
				
				List<Answer> answers = q.getAnswers();		
				XWPFParagraph answerParagraph = doc.createParagraph();
				
				/*
				 * Remove spacing between paragraphs
				 */
				if(Boolean.valueOf(pQuestionSpacing)){
					DocumentTools.setSingleLineSpacing(answerParagraph);
				}

				for(int i = 0; i < answers.size(); i++){
					Answer a = answers.get(i);
					XWPFRun answerRun = answerParagraph.createRun();
					
					answerRun.setText(String.format("%s%s %s", a.getLabel(), pAnswerPuncuation, a.getAnswer()));
					
					if(i != answers.size() - 1){
						answerRun.addBreak();
					}	
				}
			}						
		}
	}
	
	@Override
	public List<PropertyNames> getRequiredProp() {
		return new ArrayList<>(Arrays.asList(
				new PropertyNames[]{
						PropertyNames.F_QUESTION_BOLD,
						PropertyNames.P_PUNCTUATION_QUESTION,
						PropertyNames.P_PUNCTUATION_ANSWER,
						PropertyNames.T_VARIANT_TITLE, 
						PropertyNames.F_QUESTION_SPACING}));
	}
}
