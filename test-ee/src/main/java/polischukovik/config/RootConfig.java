package polischukovik.config;

import java.util.Arrays;

import javax.annotation.Resource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import polischukovik.domain.enums.NumeratorType;
import polischukovik.domain.enums.PropertyName;
import polischukovik.domain.enums.PropertyType;
import polischukovik.impl.SimpleKeysComposer;
import polischukovik.impl.SimpleTitleComposer;
import polischukovik.impl.SimpleVariantComposer;
import polischukovik.properties.Properties;
import polischukovik.services.DocumentComponentComposer;
import polischukovik.services.DocumentFactory;
import polischukovik.services.IOTools;
import polischukovik.services.QuestionDataSource;
import polischukovik.services.TestFactory;
import polischukovik.ui.UserInterfaceSet;

@Configuration
@EnableAspectJAutoProxy
@ComponentScan("polischukovik")
@PropertySource("classpath:/app.properties")
public class RootConfig {
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
	
	@Resource
    private Environment env;
	
    @Bean
	public Properties getApplicationPropeties(){

		Properties prop = new Properties();		
		//															PROPERTY GROUP											
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
		prop.setValue(PropertyName.F_QUESTION_BOLD, 				env.getProperty(F_QUESTION_BOLD, 			new Boolean("false").toString()));    
		prop.setValue(PropertyName.F_QUESTION_SPACING, 				env.getProperty(F_QUESTION_SPACING, 		new Boolean("false").toString()));    
		prop.setValue(PropertyName.S_NUMERATION_VARIANT, 			env.getProperty(P_VARIANT_NUMERATION, 		NumeratorType.ROMAN.toString()));     
		prop.setValue(PropertyName.S_NUMERATION_QUESTION, 			env.getProperty(P_QUESTION_NUMERATION, 		NumeratorType.NUMERIC.toString()));   
		prop.setValue(PropertyName.S_NUMERATION_ANSWER, 			env.getProperty(P_ANSWER_NUMERATION, 		NumeratorType.ALPHABETIC.toString()));
				

		return prop;
	}
	
	@Bean
	public UserInterfaceSet getInterfaceSet(QuestionDataSource questionDataSource, 
			TestFactory testFactory, DocumentFactory documentFactory, IOTools ioTools,
			SimpleTitleComposer titleComposer, SimpleVariantComposer variantComposer,
			SimpleKeysComposer keysComposer){
		return new UserInterfaceSet(0
				, questionDataSource
				, testFactory
				, documentFactory
				, ioTools
				, Arrays.asList(
						  (DocumentComponentComposer) titleComposer
						, (DocumentComponentComposer) variantComposer
						, (DocumentComponentComposer) keysComposer));
	}

}
