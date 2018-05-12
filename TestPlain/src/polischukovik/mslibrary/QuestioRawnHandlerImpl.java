package polischukovik.mslibrary;

import java.io.File;
import java.util.Scanner;
import polischukovik.domain.QuestionRaw;
import polischukovik.domain.enums.PropertyNames;
import polischukovik.domain.enums.QuestionType;
import polischukovik.services.QuestioRawnHandler;

import java.util.ArrayList;
import java.util.List;

/*
 * QuestionHandler responds for:
 * 		loading source file,
 * 		verifying it's integrity
 * 		parsing results into Question class
 */
public class QuestioRawnHandlerImpl implements QuestioRawnHandler{
	private static String filePath;
	//TODO change access to nio
	private static Scanner sourceFile;
	private static String questionData = "";
	
	private static Properties prop;

	QuestioRawnHandlerImpl() {
		
	}

	@SuppressWarnings("unused")
	private static void verifySource(){
		if(false){
			throw new IllegalArgumentException("Source file is totally wrong!");
		}
	}
	
	private static void processRawData(List<QuestionRaw> questions){	
		String pMark;
		try {
			pMark = prop.get(PropertyNames.PARSING_MARK_QUESTION, "&");
		}catch (Exception e) {
			e.printStackTrace();
			return;
		}
		
		String[] rawQ = questionData.split(pMark);
		
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
				int index = (a[j].indexOf("*"));
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
			questions.add(new QuestionRaw(QuestionType.SINGLE_ANSWER, q, a, correctAnswerId));
		}
		
		System.err.println();
	}

	public List<QuestionRaw> parseSource(String sourceFilePath, Properties prop) {
		filePath = sourceFilePath;		
		QuestioRawnHandlerImpl.prop = prop;
		
		if(prop == null){
			return null;
		}
			
		try{
		sourceFile = new Scanner(new File(filePath));
		while(sourceFile.hasNext()){
			questionData += sourceFile.nextLine() + "\n";
		}
		}catch(Exception e){
			throw new IllegalArgumentException("Unable to read source file: " + filePath + "\n" +  e.getMessage());
		}		
		List<QuestionRaw> questions = new ArrayList<>();
		
		verifySource();
		processRawData(questions);
		
		return questions;
	}

}
