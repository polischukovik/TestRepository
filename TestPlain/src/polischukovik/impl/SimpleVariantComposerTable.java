package polischukovik.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import polischukovik.domain.Answer;
import polischukovik.domain.Question;
import polischukovik.domain.Test;
import polischukovik.domain.Variant;
import polischukovik.domain.enums.PropertyName;
import polischukovik.main.Main;
import polischukovik.mslibrary.MSLib;
import polischukovik.properties.Properties;
import polischukovik.properties.RequiredPropertyNameProvider;
import polischukovik.services.DocumentComponentComposer;

@Component
public class SimpleVariantComposerTable implements DocumentComponentComposer, RequiredPropertyNameProvider {
	
	@Autowired
	private Properties prop;

	private static List<PropertyName> requiredProps = Arrays.asList(
			 PropertyName.F_QUESTION_BOLD
			 ,PropertyName.F_QUESTION_SPACING
			 ,PropertyName.P_PUNCTUATION_QUESTION
			 ,PropertyName.P_PUNCTUATION_ANSWER
			 ,PropertyName.T_VARIANT_TITLE,
			 PropertyName.F_QUESTION_A_C_W);

	private String pFQuestionBold;
	@SuppressWarnings("unused")
	private String pQuestionSpacing;
	private String pQuestionPunctuation;
	private String pAnswerPuncuation;
	private String pVariantTitle;
	private String fAnswerCellW;
	
	private final String composerName = this.getClass().getName();
	
	public SimpleVariantComposerTable() {
		prop = Main.ctx.getBean(Properties.class);
	}
	
	@Override
	public void constructComponent(Test test, XWPFDocument doc) {
		MSLib.addSection(doc);
		
		pFQuestionBold = prop.get(PropertyName.F_QUESTION_BOLD);
		pQuestionSpacing = prop.get(PropertyName.F_QUESTION_SPACING);
		pQuestionPunctuation = prop.get(PropertyName.P_PUNCTUATION_QUESTION);
		pAnswerPuncuation = prop.get(PropertyName.P_PUNCTUATION_ANSWER);
		pVariantTitle = prop.get(PropertyName.T_VARIANT_TITLE);
		fAnswerCellW = prop.get(PropertyName.F_QUESTION_A_C_W);
		
		List<Variant> variants = test.getVariants();		
		for(Variant v : variants){
			addCaption(doc, v);
						
			List<Question> questions = v.getQuestions();
			for(Question q : questions){
				addQuestion(doc, q);
				
				List<Answer> answers = q.getAnswers();		
				addAnswers(doc, answers);
			}						
		}
	}

	private void addAnswers(XWPFDocument doc, List<Answer> answers) {
		XWPFTable table = doc.createTable();
		
		MSLib.setTableWidthToSection(doc, table);
				
		XWPFTableRow row = table.getRow(0);
		row.addNewTableCell();
		for(int i = 0; i < answers.size(); i++){
			Answer a = answers.get(i);
			
			row.getCell(0).setText(a.getLabel() + pAnswerPuncuation);
			row.getCell(1).setText(a.getAnswer());
			
			MSLib.setSingleLineSpacing(row.getCell(0).getParagraphs().get(0));
			MSLib.setSingleLineSpacing(row.getCell(1).getParagraphs().get(0));
			
			MSLib.setCellWidth(row.getCell(0), Double.valueOf(fAnswerCellW));
			
			if(i != answers.size() - 1){
				row = table.createRow();
			}	
		}		
	}

	private void addQuestion(XWPFDocument doc, Question q) {
		XWPFParagraph questionParagpaph = doc.createParagraph();

		MSLib.setSingleLineSpacing(questionParagpaph);
		
		XWPFRun questionRun = questionParagpaph.createRun();
		questionRun.setText(String.format("%s%s %s",q.getId(), pQuestionPunctuation, q.getQuestion()));
		
		/*
		 * Set question Run to bold if parameter presented
		 */							
		questionRun.setBold(Boolean.valueOf(pFQuestionBold));
	}

	private void addCaption(XWPFDocument doc, Variant v) {
		//Add caption
		XWPFParagraph p0 = doc.createParagraph();
		XWPFRun r0 = p0.createRun();
		p0.setAlignment(ParagraphAlignment.CENTER);
		p0.setPageBreak(true);
		
		String vLabel = v.getName();		
		r0.setText(String.format("%s %s", pVariantTitle, vLabel));
		r0.setBold(true);
	}
	
	@Override
	public String getComposerName() {
		return composerName;
	}

	public List<PropertyName> getRequiredProperties() {
		return new ArrayList<>(requiredProps);
	}
}
