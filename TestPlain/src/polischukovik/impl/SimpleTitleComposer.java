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

import polischukovik.domain.Test;
import polischukovik.domain.enums.PropertyName;
import polischukovik.main.Main;
import polischukovik.properties.Properties;
import polischukovik.properties.RequiredPropertyNameProvider;
import polischukovik.services.DocumentComponentComposer;

@Component
public class SimpleTitleComposer implements DocumentComponentComposer, RequiredPropertyNameProvider {
	@SuppressWarnings("unused")
	@Autowired
	private Properties prop;	
	
	private static List<PropertyName> requiredProps = Arrays.asList();
	
	private final String composerName = this.getClass().getName();

	public SimpleTitleComposer() {
		prop = Main.ctx.getBean(Properties.class);
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

	public List<PropertyName> getRequiredProperties() {
		return new ArrayList<>(requiredProps);
	}
}
