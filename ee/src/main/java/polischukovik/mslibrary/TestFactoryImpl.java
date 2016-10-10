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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import polischukovik.domain.Answer;
import polischukovik.domain.Question;
import polischukovik.domain.QuestionRaw;
import polischukovik.domain.Test;
import polischukovik.domain.Variant;
import polischukovik.domain.enums.NumeratorType;
import polischukovik.domain.enums.PropertyNames;
import polischukovik.services.TestFactory;
@Component
public class TestFactoryImpl implements TestFactory {
	@Autowired
	private Properties prop;
	private List<QuestionRaw> questions;

	private String pTestName;	
	private int pVariants;
	private int pQuestions;
	private String pShuffleQuestions;
	private String pShuffleAnswers;
	private String pVariantNumStyle; 
	private String pQuestionNumStyle;
	private String pAnswerNumStyle;
	
	private Random rnd;

	public TestFactoryImpl() {
	}

	public Test createTest(List<QuestionRaw> questions) {	
		this.questions = questions;	
		/*
		 * Read properties
		 */
		pTestName = prop.get(PropertyNames.BASIC_TEST_NAME);
		pVariants = Integer.valueOf(prop.get(PropertyNames.BASIC_VARIANTS));
		pQuestions = Integer.valueOf(prop.get(PropertyNames.BASIC_QUESTIONS));		
		pShuffleQuestions = prop.get(PropertyNames.P_SHUFFLE_QUESTION);
		pShuffleAnswers = prop.get(PropertyNames.P_SHUFFLE_ANSWERS);
		pVariantNumStyle = prop.get(PropertyNames.S_NUMERATION_VARIANT);
		pQuestionNumStyle = prop.get(PropertyNames.S_NUMERATION_QUESTION);
		pAnswerNumStyle = prop.get( PropertyNames.S_ANSWER_NUMERATION);
						
		if(Boolean.valueOf(pShuffleQuestions) || Boolean.valueOf(pShuffleAnswers)){
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
		Numerator numsVariant = new Numerator(Numerator.valueOf(pVariantNumStyle));
					
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
		
		if(Boolean.valueOf(pShuffleQuestions)){
			Collections.shuffle(sortedQuestions, rnd);
		}
		
		/*
		 * Set question numeration style
		 */
		Numerator nums = new Numerator(Numerator.valueOf(pQuestionNumStyle));
		
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
		if(Boolean.valueOf(pShuffleAnswers)){
			Collections.shuffle(listAnswer, rnd);
		}	
		
		/*
		 * Set answer numeration style
		 */
		Numerator answerNums = new Numerator(Numerator.valueOf(pAnswerNumStyle));
		
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
}
