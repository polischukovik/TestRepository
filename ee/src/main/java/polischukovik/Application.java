package polischukovik;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
	private static List<Class<? extends DocumentComponentComposer>> componentComposers;

	private static QuestioRawnHandler questionRawHandler;
	private static TestFactory testFactory;
	private static DocumentFactory documentFactory;

	public static void main(String[] args) throws IOException {
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfiguration.class);
		
		questionRawHandler = ctx.getBean(QuestioRawnHandler.class);
		testFactory = ctx.getBean(TestFactory.class);
		documentFactory = ctx.getBean(DocumentFactory.class);
		
		List<QuestionRaw> questions = questionRawHandler.parseSource();
		
		Test test = testFactory.createTest(questions);
		
		if(test == null){
			System.err.println("Failed to generate test. Exiting");
			return;
		}		
		/*
		 * Those composers would sequentially be applied to doc
		 */
		componentComposers = new ArrayList<>();
		componentComposers.add(SimpleTitleComposer.class);
		componentComposers.add(SimpleVariantComposer.class);
		componentComposers.add(SimpleKeysComposer.class);

		documentFactory = new SimpleDocumentFactoryImpl(test);		
		
		try {

			XWPFDocument doc = documentFactory.createDocument(componentComposers);
			new DocumentTools().write(doc);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}
		
		ctx.close();
	
	}
}