package polischukovik;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import polischukovik.domain.QuestionRaw;
import polischukovik.domain.Test;
import polischukovik.msformating.SimpleDocumentFactoryImpl;
import polischukovik.msformating.SimpleKeysComposer;
import polischukovik.msformating.SimpleTitleComposer;
import polischukovik.msformating.SimpleVariantComposer;
import polischukovik.msformating.interfaces.DocumentComponentComposer;
import polischukovik.mslibrary.DocumentTools;
import polischukovik.mslibrary.QuestioRawnHandlerImpl;
import polischukovik.mslibrary.TestFactoryImpl;
import polischukovik.services.QuestioRawnHandler;
import polischukovik.services.TestFactory;

@SpringBootApplication
@ComponentScan({ "polischukovik" })
public class Application{
	private static ConfigurableApplicationContext context;
	
	private static List<Class<? extends DocumentComponentComposer>> domponentComposers;

	private static QuestioRawnHandler questionRawHandler;
	private static TestFactory testFactory;
	private static SimpleDocumentFactoryImpl documentFactory;

	public static void main(String[] args) throws IOException {

		context = SpringApplication.run(Application.class, args);
	
		questionRawHandler = new QuestioRawnHandlerImpl();

		//throw new IllegalArgumentException("Unable to read source file: " + filePath + "\n" +  e.getMessage());	
		List<QuestionRaw> questions = questionRawHandler.parseSource();
		
		testFactory = new TestFactoryImpl(questions);		 
		Test test = testFactory.createTest();
		
		if(test == null){
			System.err.println("Failed to generate test. Exiting");
			return;
		}		
		/*
		 * Those composers would sequentially be applied to doc
		 */
		domponentComposers = new ArrayList<>();
		domponentComposers.add(SimpleTitleComposer.class);
		domponentComposers.add(SimpleVariantComposer.class);
		domponentComposers.add(SimpleKeysComposer.class);

		documentFactory = new SimpleDocumentFactoryImpl(test);		
		
		try {
			new DocumentTools().write(documentFactory.createDocument(domponentComposers));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}
		
		
		System.err.println("done");
	
	}
	
	 public static ConfigurableApplicationContext getAppCtx(){
	    	return context;
	    }
	    
	    public static void exit(){
	    	SpringApplication.exit(Application.getAppCtx(), new ExitCodeGenerator() {

			    @Override
			    public int getExitCode() {
			      return 0;
			    }
			  });
	    }

}