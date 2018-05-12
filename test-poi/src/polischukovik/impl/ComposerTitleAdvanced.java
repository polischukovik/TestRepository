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
public class ComposerTitleAdvanced implements RequiredPropertyNameProvider, DocumentComponentComposer {
	@SuppressWarnings("unused")
	@Autowired
	private Properties prop;	
	
	private static List<PropertyName> requiredProps = Arrays.asList();
	
	private final String composerName = this.getClass().getName();
	private final String placeholder = "{title}";

	public ComposerTitleAdvanced() {
		prop = Main.ctx.getBean(Properties.class);
	}

	@Override
	public XWPFDocument constructComponent(Test test) {
		XWPFDocument dummyDoc = new XWPFDocument();
		XWPFParagraph p = dummyDoc.createParagraph();
		p.setAlignment(ParagraphAlignment.CENTER);
		XWPFRun r = p.createRun();
		r.addBreak();
		r.addBreak();
		r.addBreak();
		r.addBreak();
		r.setText(test.getCaption());		
		r.addBreak();
		
		return dummyDoc; 
	}
	
	@Override
	public String getPlaceHolder(){
		return placeholder;
	}

	@Override
	public String getComposerName() {
		return composerName;
	}
	
	@Override
	public List<PropertyName> getRequiredProperties() {
		return new ArrayList<>(requiredProps);
	}

	@Override
	public String toString() {
		return "ComposerTitleAdvanced [composerName=" + composerName + ", placeholder=" + placeholder
				+ ", getRequiredProperties()=" + getRequiredProperties() + "]";
	}
}
