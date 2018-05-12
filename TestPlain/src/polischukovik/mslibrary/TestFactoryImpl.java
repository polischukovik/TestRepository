package polischukovik.mslibrary;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;
import java.util.stream.Collectors;

import polischukovik.domain.Answer;
import polischukovik.domain.Question;
import polischukovik.domain.QuestionRaw;
import polischukovik.domain.Test;
import polischukovik.domain.Variant;
import polischukovik.domain.enums.NumeratorType;
import polischukovik.domain.enums.PropertyNames;
import polischukovik.services.TestFactory;

public class TestFactoryImpl implements TestFactory {
	
	private List<QuestionRaw> questions;
	private static Properties prop;
	
	private int pVariants;
	private String pTestName;	
	private boolean pShuffleQuestions;
	private NumeratorType pQuestionNumStyle;
	private NumeratorType pAnswerNumStyle;
	private NumeratorType pVariantNumStyle; 
	private int pQuestions;
	private boolean pShuffleAnswers;
	
	private Random rnd;

	public TestFactoryImpl(List<QuestionRaw> questions) {
		this.questions = questions;	
	}

	public Test createTest(Properties prop) {
		TestFactoryImpl.prop = prop;
		
		/*
		 * Read properties
		 */
		pTestName = TestFactoryImpl.getProperties().get(PropertyNames.RES_TEST_NAME, "Test name default");
		pVariants = Integer.valueOf(TestFactoryImpl.getProperties().get(PropertyNames.BASIC_VARIANTS, "2"));
		pQuestions = Integer.valueOf(TestFactoryImpl.getProperties().get(PropertyNames.BASIC_QUESTIONS, "30"));		
		pShuffleQuestions = TestFactoryImpl.getProperties().getBoolean(PropertyNames.SHUFFLE_QUESTION, true);
		pShuffleAnswers = TestFactoryImpl.getProperties().getBoolean(PropertyNames.SHUFFLE_ANSWERS, true);
		pVariantNumStyle = TestFactoryImpl.getProperties().getNumerationStyle(PropertyNames.S_NUMERATION_VARIANT, NumeratorType.ROMAN);
		pQuestionNumStyle = TestFactoryImpl.getProperties().getNumerationStyle(PropertyNames.S_NUMERATION_QUESTION, NumeratorType.NUMERIC);
		pAnswerNumStyle = TestFactoryImpl.getProperties().getNumerationStyle( PropertyNames.S_ANSWER_NUMERATION, NumeratorType.ALPHABETIC);
						
		if(pShuffleQuestions || pShuffleAnswers){
			rnd = new Random(System.nanoTime());
		}		
		
		Test test = new Test(pTestName, createVariants());
		
		return test;
	}

	private List<Variant> createVariants() {
		List<Variant> variants = new ArrayList<>();
		
		/*
		 * Set variant numeration style
		 */
		Numerator numsVariant = new Numerator(pVariantNumStyle);
					
		for(int i = 0; i < pVariants; i++){
			List<Question> questionList = createQuestions();
			
			Variant v = new Variant(numsVariant.getNext(), questionList, createKeys(questionList));			
			variants.add(v);
		}
		
		return variants;
	}

	private List<Question> createQuestions() {
		List<QuestionRaw> sortedQuestions = new ArrayList<>(questions);
		List<Question> result = new ArrayList<>(); 
		
		if(pShuffleQuestions){
			Collections.shuffle(sortedQuestions, rnd);
		}
		
		/*
		 * Set question numeration style
		 */
		Numerator nums = new Numerator(pQuestionNumStyle);
		
		for(int j = 0; (j < pQuestions) && (j < sortedQuestions.size()); j++){
			List<Answer> answers = createAnswers(sortedQuestions.get(j));
			
			Question prep = new Question(nums.getNext(), sortedQuestions.get(j).getType(), sortedQuestions.get(j).getQuestion(), answers);		
			result.add(prep);
		}
		
		return result;
	}

	private List<Answer> createAnswers(QuestionRaw questionRaw) {
		List<Answer> answers = new ArrayList<>();
		List<String> listAnswer = new ArrayList<>(Arrays.asList(questionRaw.getAnswers()));
		List<Integer> correctList = Arrays.asList(questionRaw.getCorrect());
		Map<String, Boolean> answerCorrectMap = new TreeMap<>();//Map to know which answer is correct after shuffle

		for(int i = 0; i < listAnswer.size(); i++){
			answerCorrectMap.put(listAnswer.get(i), correctList.contains(i));
		}
		/*
		 * Shuffle answers
		 */
		if(pShuffleAnswers){
			Collections.shuffle(listAnswer, rnd);
		}	
		
		/*
		 * Set answer numeration style
		 */
		Numerator answerNums = new Numerator(pAnswerNumStyle);
		
		for(String s : listAnswer){
			answers.add(new Answer(answerNums.getNext(), s, answerCorrectMap.get(s)));
		}
		return answers;
	}
	
	private Map<Question, String> createKeys(List<Question> questionList) {
		Map<Question, String> result = new HashMap<>();
		for (Question q : questionList) {
			String answers = q.getAnswers().stream().filter(f -> f.isCorrect()).map(i -> i.getLabel())
					.collect(Collectors.joining(", "));
			result.put(q, answers);
		}
		return result;
	}
	
	public static Properties getProperties(){
		return prop;
	}
}
