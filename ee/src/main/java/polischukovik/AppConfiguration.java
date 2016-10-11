package polischukovik;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import polischukovik.domain.Test;
import polischukovik.domain.enums.NumeratorType;
import polischukovik.domain.enums.PropertyNames;
import polischukovik.msformating.SimpleKeysComposer;
import polischukovik.msformating.SimpleTitleComposer;
import polischukovik.msformating.SimpleVariantComposer;
import polischukovik.msformating.interfaces.DocumentComponentComposer;
import polischukovik.msformating.interfaces.DocumentFactory;
import polischukovik.mslibrary.Properties;
import polischukovik.services.QuestioRawnHandler;
import polischukovik.services.TestFactory;

@Configuration
@ComponentScan(basePackages={"polischukovik.msformating","polischukovik.mslibrary","polischukovik"})
@PropertySource("classpath:/app.properties")
public class AppConfiguration {
	private final static String IO_SOURCE				= "io.source";
	private final static String IO_DEST					= "io.dest";
	private final static String TEST_NAME				= "basic.test.name";
	private final static String T_VARIANT_TITLE			= "text.variant.title";
	private final static String T_KEY_TITLE				= "text.key.title";
	private final static String VARIANTS				= "basic.variants";
	private final static String QUESTIONS				= "basic.questions";
	private final static String MARK					= "parsing.mark";
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
		//																								DEFAULT VALUE
		prop.add(PropertyNames.IO_SOURCE_FILE_NAME,			env.getProperty(IO_SOURCE),					"source.txt");
		prop.add(PropertyNames.IO_DEST_FILE_NAME,			env.getProperty(IO_DEST),					"file.docx");
		prop.add(PropertyNames.BASIC_TEST_NAME,				env.getProperty(TEST_NAME),					"Test name default");
		prop.add(PropertyNames.T_VARIANT_TITLE,				env.getProperty(T_VARIANT_TITLE),			"Variant");
		prop.add(PropertyNames.T_KEY_TITLE,					env.getProperty(T_KEY_TITLE),				"Key title");
		prop.add(PropertyNames.BASIC_VARIANTS,				env.getProperty(VARIANTS),					"2");
		prop.add(PropertyNames.BASIC_QUESTIONS,				env.getProperty(QUESTIONS),					"30");
		prop.add(PropertyNames.PARSING_MARK_QUESTION,		env.getProperty(MARK),						"&");
		prop.add(PropertyNames.P_SHUFFLE_ANSWERS,			env.getProperty(SHUFFLE_ANSWERS),			new Boolean("false").toString());
		prop.add(PropertyNames.P_SHUFFLE_QUESTION,			env.getProperty(SHUFFLE_QUESTIONS),			new Boolean("false").toString());
		prop.add(PropertyNames.P_PUNCTUATION_ANSWER,		env.getProperty(ANSWER_PUNCTUATION),		")");
		prop.add(PropertyNames.P_PUNCTUATION_QUESTION,		env.getProperty(QUESTION_PUNCTUATION),		".");
		prop.add(PropertyNames.P_PUNCTUATION_KEY_ANSWER, 	env.getProperty(KEY_ANSWER_PUNCTUATION),	"-");
		prop.add(PropertyNames.F_QUESTION_BOLD, 			env.getProperty(F_QUESTION_BOLD), 			new Boolean("false").toString());
		prop.add(PropertyNames.F_QUESTION_SPACING, 			env.getProperty(F_QUESTION_SPACING), 		new Boolean("false").toString());
		prop.add(PropertyNames.S_NUMERATION_VARIANT, 		env.getProperty(P_VARIANT_NUMERATION), 		NumeratorType.ROMAN.toString());
		prop.add(PropertyNames.S_NUMERATION_QUESTION, 		env.getProperty(P_QUESTION_NUMERATION), 	NumeratorType.NUMERIC.toString());
		prop.add(PropertyNames.S_ANSWER_NUMERATION, 		env.getProperty(P_ANSWER_NUMERATION), 		NumeratorType.ALPHABETIC.toString());

		return prop;
	}

    @Bean
    @Autowired
    public Test getTests(TestFactory tf, QuestioRawnHandler qh) throws FileNotFoundException{
    	return tf.createTest(qh.parseSource());
    }
    
    @Bean
    @Autowired
    public List<DocumentComponentComposer> getComponentComposers(SimpleTitleComposer st, SimpleVariantComposer sv, SimpleKeysComposer sk){
    	List<DocumentComponentComposer> list = new ArrayList<>();
    	list.add(new SimpleVariantComposer());


    	return list;
    }
    
    @Bean
    @Autowired
    public XWPFDocument getDocument(DocumentFactory df, Test test, List<? extends DocumentComponentComposer> componentComposers) throws ClassNotFoundException{
    	/*
		 * Those composers would sequentially be applied to doc
		 */
    	return df.createDocument(test, componentComposers);
    }

    
}
