package polischukovik;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.text.Document;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import polischukovik.domain.QuestionRaw;
import polischukovik.domain.Test;
import polischukovik.msformating.SimpleDocumentFactoryImpl;
import polischukovik.msformating.SimpleKeysComposer;
import polischukovik.msformating.SimpleTitleComposer;
import polischukovik.msformating.SimpleVariantComposer;
import polischukovik.msformating.interfaces.DocumentComponentComposer;
import polischukovik.msformating.interfaces.DocumentFactory;
import polischukovik.mslibrary.DocumentTools;
import polischukovik.mslibrary.QuestioRawnHandlerImpl;
import polischukovik.mslibrary.TestFactoryImpl;
import polischukovik.services.QuestioRawnHandler;
import polischukovik.services.TestFactory;

public class Application{

	public static void main(String[] args) throws IOException {
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfiguration.class);

		XWPFDocument doc = ctx.getBean(XWPFDocument.class);		
		DocumentTools dt =  ctx.getBean(DocumentTools.class);
		
		dt.write(doc);
		
		ctx.close();
	
	}
}