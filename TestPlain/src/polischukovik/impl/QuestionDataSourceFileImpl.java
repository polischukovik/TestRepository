package polischukovik.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import polischukovik.domain.QuestionRaw;
import polischukovik.domain.enums.PropertyName;
import polischukovik.domain.enums.QuestionType;
import polischukovik.main.Main;
import polischukovik.properties.Properties;
import polischukovik.properties.RequiredPropertyNameProvider;
import polischukovik.services.QuestionDataSource;

/*
 * QuestionHandler responds for:
 * 		loading source file,
 * 		verifying it's integrity
 * 		parsing results into Question class
 */
@Component
public class QuestionDataSourceFileImpl implements QuestionDataSource, RequiredPropertyNameProvider{
	
	@Autowired
	private Properties prop;
	
	private static List<PropertyName> requiredProps = Arrays.asList(
			 PropertyName.PARSING_MARK_QUESTION
			 ,PropertyName.PARSING_MARK_CORRECT_ANSWER);
	
	//TODO change access to nio
	private String pMark;
	private String pQustionMark;
	
	public QuestionDataSourceFileImpl() {
		prop = Main.ctx.getBean(Properties.class);
	}

	@SuppressWarnings("unused")
	private static void verifySource(){
		if(false){
			throw new IllegalArgumentException("Source file is totally wrong!");
		}
	}
	
	@Override
	public List<QuestionRaw> parseSource(String sourceStr){	
		pMark = prop.get(PropertyName.PARSING_MARK_QUESTION);
		pQustionMark = prop.get(PropertyName.PARSING_MARK_CORRECT_ANSWER);
		
		List<QuestionRaw> questionData = new ArrayList<>();
		String[] rawQ = sourceStr.split(pMark);
		
		//remove whitespaces;
		for(int i = 0; i < rawQ.length; i++){
			rawQ[i] = rawQ[i].trim();
		}
		
		//first row is question
		//next rows are answers
		for(int i = 0; i < rawQ.length; i++){
			String item = rawQ[i];
			if("".equals(item)){
				continue;
			}
			
			//To be or not to be?
			String q = item.substring(0, item.indexOf("\n")).trim();
			//Yes\nNo\nMaybe
			//
			String rawAnswers = item.substring(item.indexOf("\n")).trim();
			//yes, no, maybe
			String[] a = rawAnswers.split("\n");
			Integer[] correctAnswerId = new Integer[0];
			
			for(int j = 0; j < a.length; j++){
				int index = (a[j].indexOf(pQustionMark));
				if(index >= 0){
					a[j] = a[j].substring(index+1);
					Integer[] tmp = correctAnswerId;
					correctAnswerId = new Integer[correctAnswerId.length + 1];
					System.arraycopy(tmp, 0, correctAnswerId, 0, tmp.length);
					
					correctAnswerId[correctAnswerId.length - 1] = j;
				}
				
				a[j]= a[j].trim();
			}
			
			//remove whitespaces;
			for(int j = 0; j < a.length; j++){
				a[j] = a[j].trim();
			}
			questionData.add(new QuestionRaw(QuestionType.SINGLE_ANSWER, q, a, correctAnswerId));
		}
		
		System.err.println();
		return questionData;
	}

	@Override
	public List<PropertyName> getRequiredProperties() {
		return new ArrayList<>(requiredProps);
	}

}
