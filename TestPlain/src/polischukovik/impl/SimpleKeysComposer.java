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
public class SimpleKeysComposer implements DocumentComponentComposer, RequiredPropertyNameProvider{
	@Autowired
	private Properties prop;
	
	private static List<PropertyName> requiredProps = Arrays.asList(
			  PropertyName.P_PUNCTUATION_KEY_ANSWER
			 ,PropertyName.T_KEY_TITLE);
	
	private final String composerName = this.getClass().getName();
	
	private String pPunctuationKeyAnswer;
	private String resKeyTytle;
	
	public SimpleKeysComposer() {
		prop = Main.ctx.getBean(Properties.class);
	}
	
	@Override
	public void constructComponent(Test test, XWPFDocument doc) {
		MSLib.addSection(doc);
		
		pPunctuationKeyAnswer = prop.get(PropertyName.P_PUNCTUATION_KEY_ANSWER);
		resKeyTytle = prop.get(PropertyName.T_KEY_TITLE);
		
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
	public String getComposerName() {
		return composerName;
	}
	
	public List<PropertyName> getRequiredProperties() {
		return new ArrayList<>(requiredProps);
	}
}
