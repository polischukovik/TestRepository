package polischukovik.main;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import polischukovik.domain.enums.NumeratorType;
import polischukovik.domain.enums.PropertyName;
import polischukovik.domain.enums.PropertyType;
import polischukovik.impl.IOToolsImpl;
import polischukovik.impl.QuestionDataSourceFileImpl;
import polischukovik.impl.SimpleDocumentFactoryImpl;
import polischukovik.impl.SimpleKeysComposer;
import polischukovik.impl.SimpleTitleComposer;
import polischukovik.impl.SimpleVariantComposer;
import polischukovik.impl.SimpleVariantComposerTable;
import polischukovik.impl.TestFactoryImpl;
import polischukovik.properties.Properties;
import polischukovik.services.IOTools;
import polischukovik.services.QuestionDataSource;
import polischukovik.services.TestFactory;

@Configuration
@PropertySource("classpath:application.properties")
@ComponentScan
public class Main {

	@Resource
    private Environment env;
	
	@Bean
	public Properties getPropp(){
		prop = new Properties();		
		prop.addProperty(PropertyName.IO_SOURCE_FILE_NAME,			"input.output"			,PropertyType.FILE	);
		prop.addProperty(PropertyName.IO_DEST_FILE_NAME,			"input.output"			,PropertyType.FILE	);
		prop.addProperty(PropertyName.BASIC_TEST_NAME,				"test.configuration"	,PropertyType.STRING	);
		prop.addProperty(PropertyName.T_VARIANT_TITLE,				"test.configuration"	,PropertyType.STRING	);
		prop.addProperty(PropertyName.T_KEY_TITLE,					"test.configuration"	,PropertyType.STRING	);
		prop.addProperty(PropertyName.BASIC_VARIANTS,				"test.configuration"	,PropertyType.INTEGER	);
		prop.addProperty(PropertyName.BASIC_QUESTIONS,				"test.configuration"	,PropertyType.INTEGER	);
		prop.addProperty(PropertyName.PARSING_MARK_QUESTION,		"parsing.options"		,PropertyType.STRING	);
		prop.addProperty(PropertyName.PARSING_MARK_CORRECT_ANSWER,	"parsing.options"		,PropertyType.STRING	);
		prop.addProperty(PropertyName.P_SHUFFLE_ANSWERS,			"test.configuration"	,PropertyType.BOOLEAN	);
		prop.addProperty(PropertyName.P_SHUFFLE_QUESTION,			"test.configuration"	,PropertyType.BOOLEAN	);
		prop.addProperty(PropertyName.P_PUNCTUATION_ANSWER,			"formatting"			,PropertyType.STRING	);
		prop.addProperty(PropertyName.P_PUNCTUATION_QUESTION,		"formatting"			,PropertyType.STRING	);
		prop.addProperty(PropertyName.P_PUNCTUATION_KEY_ANSWER, 	"formatting"			,PropertyType.STRING	);
		prop.addProperty(PropertyName.F_QUESTION_BOLD, 				"formatting"			,PropertyType.BOOLEAN	);
		prop.addProperty(PropertyName.F_QUESTION_SPACING, 			"formatting"			,PropertyType.BOOLEAN	);
		prop.addProperty(PropertyName.S_NUMERATION_VARIANT, 		"formatting"			,PropertyType.SELECT_NT	);
		prop.addProperty(PropertyName.S_NUMERATION_QUESTION, 		"formatting"			,PropertyType.SELECT_NT	);
		prop.addProperty(PropertyName.S_NUMERATION_ANSWER, 			"formatting"			,PropertyType.SELECT_NT	);
		prop.addProperty(PropertyName.FILE_UPLOAD, 					"formatting"			,PropertyType.FILE		);
		prop.addProperty(PropertyName.F_QUESTION_A_C_W, 			"formatting"			,PropertyType.INTEGER		);
		prop.addProperty(PropertyName.F_QUESTION_A_C_W, 			"formatting"			,PropertyType.INTEGER		);
		prop.addProperty(PropertyName.F_PAGE_COL_S, 				"formatting"			,PropertyType.INTEGER		);
		prop.addProperty(PropertyName.F_PAGE_COL_N, 				"formatting"			,PropertyType.INTEGER		);
		prop.addProperty(PropertyName.F_PAGE_FORMAT,				"formatting"			,PropertyType.STRING		);
		prop.addProperty(PropertyName.F_PAGE_M, 					"formatting"			,PropertyType.STRING		);
		
		prop.setValue(PropertyName.IO_SOURCE_FILE_NAME,				env.getProperty(IO_SOURCE,					"source.txt"));                       
		prop.setValue(PropertyName.IO_DEST_FILE_NAME,				env.getProperty(IO_DEST,					"file.docx"));                        
		prop.setValue(PropertyName.BASIC_TEST_NAME,					env.getProperty(TEST_NAME,					"Test name default"));                
		prop.setValue(PropertyName.T_VARIANT_TITLE,					env.getProperty(T_VARIANT_TITLE,			"Variant"));                          
		prop.setValue(PropertyName.T_KEY_TITLE,						env.getProperty(T_KEY_TITLE,				"Key title"));                        
		prop.setValue(PropertyName.BASIC_VARIANTS,					env.getProperty(VARIANTS,					"2"));                                
		prop.setValue(PropertyName.BASIC_QUESTIONS,					env.getProperty(QUESTIONS,					"30"));                               
		prop.setValue(PropertyName.PARSING_MARK_QUESTION,			env.getProperty(MARK,						"&"));                                
		prop.setValue(PropertyName.PARSING_MARK_CORRECT_ANSWER,		env.getProperty(MARK_Q,						"*"));                            
		prop.setValue(PropertyName.P_SHUFFLE_ANSWERS,				env.getProperty(SHUFFLE_ANSWERS,			new Boolean("false").toString()));    
		prop.setValue(PropertyName.P_SHUFFLE_QUESTION,				env.getProperty(SHUFFLE_QUESTIONS,			new Boolean("false").toString()));    
		prop.setValue(PropertyName.P_PUNCTUATION_ANSWER,			env.getProperty(ANSWER_PUNCTUATION,			")"));                                
		prop.setValue(PropertyName.P_PUNCTUATION_QUESTION,			env.getProperty(QUESTION_PUNCTUATION,		"."));                                
		prop.setValue(PropertyName.P_PUNCTUATION_KEY_ANSWER, 		env.getProperty(KEY_ANSWER_PUNCTUATION,		"-"));                                
		prop.setValue(PropertyName.F_QUESTION_BOLD, 				env.getProperty(F_QUESTION_BOLD, 			new Boolean("true").toString()));    
		prop.setValue(PropertyName.F_QUESTION_SPACING, 				env.getProperty(F_QUESTION_SPACING, 		new Boolean("false").toString()));    
		prop.setValue(PropertyName.S_NUMERATION_VARIANT, 			env.getProperty(P_VARIANT_NUMERATION, 		NumeratorType.ROMAN.toString()));     
		prop.setValue(PropertyName.S_NUMERATION_QUESTION, 			env.getProperty(P_QUESTION_NUMERATION, 		NumeratorType.NUMERIC.toString()));   
		prop.setValue(PropertyName.S_NUMERATION_ANSWER, 			env.getProperty(P_ANSWER_NUMERATION, 		NumeratorType.ALPHABETIC.toString()));
		prop.setValue(PropertyName.FILE_UPLOAD, 					env.getProperty(FILE_UPLOAD, 				""));
		prop.setValue(PropertyName.F_QUESTION_A_C_W, 				env.getProperty(F_QUESTION_A_C_W, 			"0.5"));
		prop.setValue(PropertyName.F_PAGE_COL_S, 					env.getProperty(F_PAGE_COL_S, 				"1"));
		prop.setValue(PropertyName.F_PAGE_COL_N, 					env.getProperty(F_PAGE_COL_N, 				"2"));
		prop.setValue(PropertyName.F_PAGE_FORMAT, 					env.getProperty(F_PAGE_FORMAT, 				"A4"));
		prop.setValue(PropertyName.F_PAGE_M, 						env.getProperty(F_PAGE_M, 					"1.35 1.35 1.35 1.35"));
		
		return prop;		
	}
	
	private final static String IO_SOURCE				= "io.source";
	private final static String IO_DEST					= "io.dest";
	private final static String TEST_NAME				= "basic.test.name";
	private final static String T_VARIANT_TITLE			= "text.variant.title";
	private final static String T_KEY_TITLE				= "text.key.title";
	private final static String VARIANTS				= "basic.variants";
	private final static String QUESTIONS				= "basic.questions";
	private final static String MARK					= "parsing.mark";
	private final static String MARK_Q					= "parsing.mark.question";
	private final static String SHUFFLE_QUESTIONS		= "parameter.shuffle.questions";
	private final static String SHUFFLE_ANSWERS			= "parameter.shuffle.answers";
	private final static String ANSWER_PUNCTUATION		= "punctuation.answer";
	private final static String QUESTION_PUNCTUATION	= "punctuation.question";
	private final static String KEY_ANSWER_PUNCTUATION	= "punctuation.answer.key";
	private final static String F_QUESTION_BOLD			= "formatting.question.bold";
	private final static String F_QUESTION_SPACING		= "formatting.question.spacing";
	private final static String P_VARIANT_NUMERATION	= "style.variant.numeration";
	private final static String P_QUESTION_NUMERATION	= "style.question.numeration";
	private final static String P_ANSWER_NUMERATION		= "style.answer.numeration";
	private final static String FILE_UPLOAD				= "io.file.input";
	private final static String F_QUESTION_A_C_W 		= "formatting.question.answer.cell.width";
	private final static String F_PAGE_COL_N 			= "formatting.page.column.number";
	private final static String F_PAGE_COL_S 			= "formatting.page.column.spacing";
	private final static String F_PAGE_FORMAT			= "formatting.page.format";
	private final static String F_PAGE_M				= "formatting.page.margin";
	
	public static Properties prop;
	public static ApplicationContext ctx;

	private static IOTools iOTools;
	private static QuestionDataSource questionRawHandler;
	private static TestFactory testFactory;
	private static SimpleDocumentFactoryImpl documentFactory;

	public static void main(String[] args) throws IOException {		
		ctx = new AnnotationConfigApplicationContext(Main.class);
		
		iOTools = new IOToolsImpl();
		questionRawHandler = new QuestionDataSourceFileImpl();
		testFactory = new TestFactoryImpl();
		documentFactory = new SimpleDocumentFactoryImpl();
		
		try {
			iOTools.write(
					documentFactory.createDocument(
							testFactory.createTest(
									questionRawHandler.parseSource(
											iOTools.read())), Arrays.asList(new SimpleTitleComposer(), new SimpleVariantComposerTable(), new SimpleKeysComposer())));			
		} catch (FileNotFoundException | ClassNotFoundException | IllegalStateException e) {
			String reason = String.format("2Service error: %s is thrown<br>Reason: %s",e.getClass().getName(),e.getMessage());
			System.err.println(reason);
		} catch (IOException e) {
			String reason = String.format("3Service error: %s is thrown<br>Reason: %s",e.getClass().getName(),e.getMessage());
			System.err.println(reason);
		} catch (Exception e){
			e.printStackTrace();
			String reason = String.format("4Service error: %s is thrown<br>Reason: %s",e.getClass().getName(),e.getMessage());
			System.err.println(reason);
		}
		
		System.err.println("done");
	
	}
}
