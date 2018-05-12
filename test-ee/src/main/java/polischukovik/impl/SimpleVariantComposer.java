package polischukovik.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import polischukovik.domain.Answer;
import polischukovik.domain.Question;
import polischukovik.domain.Test;
import polischukovik.domain.Variant;
import polischukovik.domain.enums.PropertyName;
import polischukovik.properties.Properties;
import polischukovik.properties.RequiredPropertyNameProvider;
import polischukovik.services.DocumentComponentComposer;

@Component
public class SimpleVariantComposer implements DocumentComponentComposer, RequiredPropertyNameProvider {
	
	@Autowired
	private Properties prop;

	private static List<PropertyName> requiredProps = Arrays.asList(
			 PropertyName.F_QUESTION_BOLD
			 ,PropertyName.F_QUESTION_SPACING
			 ,PropertyName.P_PUNCTUATION_QUESTION
			 ,PropertyName.P_PUNCTUATION_ANSWER
			 ,PropertyName.T_VARIANT_TITLE);

	private String pFQuestionBold;
	private String pQuestionSpacing;
	private String pQuestionPunctuation;
	private String pAnswerPuncuation;
	private String pVariantTitle;
	
	private final String composerName = this.getClass().getName();
	
	public SimpleVariantComposer() {
	}
	
	@Override
	public void constructComponent(Test test, XWPFDocument doc) {

		pFQuestionBold = prop.get(PropertyName.F_QUESTION_BOLD);
		pQuestionSpacing = prop.get(PropertyName.F_QUESTION_SPACING);
		pQuestionPunctuation = prop.get(PropertyName.P_PUNCTUATION_QUESTION);
		pAnswerPuncuation = prop.get(PropertyName.P_PUNCTUATION_ANSWER);
		pVariantTitle = prop.get(PropertyName.T_VARIANT_TITLE);
		
		List<Variant> variants = test.getVariants();		
		for(Variant v : variants){
			//Add caption
			XWPFParagraph p0 = doc.createParagraph();
			XWPFRun r0 = p0.createRun();
			p0.setAlignment(ParagraphAlignment.CENTER);
			p0.setPageBreak(true);
			
			String vLabel = v.getName();		
			r0.setText(String.format("%s %s", pVariantTitle, vLabel));
			r0.setBold(true);
						
			List<Question> questions = v.getQuestions();
			for(Question q : questions){
				XWPFParagraph questionParagpaph = doc.createParagraph();

				DocumentComponentComposer.setSingleLineSpacing(questionParagpaph);
				
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
					DocumentComponentComposer.setSingleLineSpacing(answerParagraph);
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
	public String getComposerName() {
		return composerName;
	}

	public List<PropertyName> getRequiredProperties() {
		return new ArrayList<>(requiredProps);
	}
}
