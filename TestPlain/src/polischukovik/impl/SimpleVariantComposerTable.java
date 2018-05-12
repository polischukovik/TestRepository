package polischukovik.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.UnderlinePatterns;
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
			
			addTail(doc);
		}
	}

	private void addTail(XWPFDocument doc) {
		XWPFParagraph p = doc.createParagraph();
		p.setAlignment(ParagraphAlignment.RIGHT);
		XWPFRun r = p.createRun();
		r.setText("Затверджено на засіданні кафедри");
		r.setBold(true);
		r.setItalic(true);

		XWPFRun r0 = p.createRun();
		r0.addCarriageReturn();
		r0.setText("теорії та історії держави і права");
		r0.setBold(true);
		r0.setItalic(true);
		
		XWPFParagraph p1 = doc.createParagraph();
		p1.setAlignment(ParagraphAlignment.LEFT);
		XWPFRun r1 = p1.createRun();
		r1.setText("Протокол №_____ від «_____» ___________ 2016 року");
		
		XWPFRun r2 = p1.createRun();
		r2.addCarriageReturn();
		r2.setText("Екзаменатор _______________(Поліщук Н.Р.)");
	}

	private void addAnswers(XWPFDocument doc, List<Answer> answers) {
		XWPFTable table = doc.createTable();
		
		MSLib.setTableWidthToSection(doc, table);
		
		MSLib.indentTableFromLeft(table, 0.1);
				
		XWPFTableRow row = table.getRow(0);
		row.addNewTableCell();
		for(int i = 0; i < answers.size(); i++){
			Answer a = answers.get(i);
			
//			row.getCell(0).setText(a.getLabel() + pAnswerPuncuation);
//			row.getCell(1).setText(a.getAnswer());
			XWPFParagraph p1 = row.getCell(0).getParagraphs().get(0);
			XWPFParagraph p2 = row.getCell(1).getParagraphs().get(0);
			
			XWPFRun run1 = p1.createRun();
			XWPFRun run2 = p2.createRun();

			MSLib.setFontStyle(run1, 10, "Times New Roman");
			MSLib.setFontStyle(run2, 10, "Times New Roman");
			
			run1.setText(a.getLabel() + pAnswerPuncuation);
			run2.setText(a.getAnswer());
			
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
		
		MSLib.setFontStyle(questionRun, 11, "Times New Roman");
		
		/*
		 * Set question Run to bold if parameter presented
		 */							
		questionRun.setBold(Boolean.valueOf(pFQuestionBold));
	}

	private void addCaption(XWPFDocument doc, Variant v) {
		//Add caption
		//Hardcoded

		XWPFParagraph p = doc.createParagraph();
		p.setAlignment(ParagraphAlignment.RIGHT);
		p.setPageBreak(true);
		XWPFRun r = p.createRun();
		r.setText("Форма № Н-5.05");
		r.setBold(true);
		r.setItalic(true);
		//r.setFontSize(fontSize);
		
		XWPFParagraph p1 = doc.createParagraph();
		p1.setAlignment(ParagraphAlignment.CENTER);
		XWPFRun r1 = p1.createRun();
		r1.setText("НАЦІОНАЛЬНИЙ УНІВЕРСИТЕТ БІОРЕСУРСІВ ТА ПРИРОДОКОРИСТУВАННЯ УКРАЇНИ");
		r1.setBold(true);
		//r1.setFontSize(fontSize);
		
		XWPFParagraph p2 = doc.createParagraph();
		p2.setAlignment(ParagraphAlignment.LEFT);
		XWPFRun r2 = p2.createRun();
		r2.setText("ОС «Бакалавр»");
		r2.setItalic(true);
		r2.setUnderline(UnderlinePatterns.SINGLE);
		//r2.setFontSize(fontSize);
		
		XWPFParagraph p3 = doc.createParagraph();
		p3.setAlignment(ParagraphAlignment.LEFT);
		XWPFRun r3 = p3.createRun();
		r3.setText("Спеціальність: ");
		//r3.setFontSize(fontSize);
		
		XWPFRun r6 = p3.createRun();
		r6.setItalic(true);
		r6.setUnderline(UnderlinePatterns.SINGLE);
		r6.setText("205 - Лісове господарство");
		//r6.setFontSize(fontSize);
		
		XWPFRun r7 = p3.createRun();
		r7.addCarriageReturn();
		r7.setItalic(true);
		r7.setUnderline(UnderlinePatterns.SINGLE);
		r7.setText("               206 - Садово-паркове господарство");
		//r7.setFontSize(fontSize);
		
		XWPFParagraph p4 = doc.createParagraph();
		p4.setAlignment(ParagraphAlignment.LEFT);
		XWPFRun r4 = p4.createRun();
		r4.setText("Дисципліна: ");
		//r4.setFontSize(fontSize);
		
		XWPFRun r5 = p4.createRun();
		r5.setItalic(true);
		r5.setUnderline(UnderlinePatterns.SINGLE);
		r5.setFontFamily("Times New Roman");
		r5.setText("«Правознавство»");
		//r5.setFontSize(fontSize);
		
		XWPFParagraph p0 = doc.createParagraph();
		XWPFRun r0 = p0.createRun();
		p0.setAlignment(ParagraphAlignment.CENTER);
		
		String vLabel = v.getName();		
		r0.setText(String.format("%s %s", pVariantTitle, vLabel));
		r0.setBold(true);
		
		for(XWPFParagraph par : doc.getParagraphs()){
			MSLib.setSingleLineSpacing(par);
			for(XWPFRun run : par.getRuns()){
				MSLib.setFontStyle(run, 10, "Times New Roman");
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
