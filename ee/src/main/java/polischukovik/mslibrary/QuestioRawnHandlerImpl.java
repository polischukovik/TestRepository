package polischukovik.mslibrary;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
@Component
public class QuestioRawnHandlerImpl implements QuestioRawnHandler{
	
	@Autowired
	private Properties prop;
	
	//TODO change access to nio
	private Scanner sourceFile;
	private String questionData = "";
	private String pMark;
	private String sourceFilePath;
	
	public QuestioRawnHandlerImpl() {
	}

	@SuppressWarnings("unused")
	private static void verifySource(){
		if(false){
			throw new IllegalArgumentException("Source file is totally wrong!");
		}
	}

	public List<QuestionRaw> parseSource() throws FileNotFoundException {		

		pMark = prop.get(PropertyNames.PARSING_MARK_QUESTION);
		sourceFilePath = prop.get(PropertyNames.IO_SOURCE_FILE_NAME);
		
		sourceFile = new Scanner(new File(sourceFilePath));
		while(sourceFile.hasNext()){
			questionData += sourceFile.nextLine() + "\n";
		}
		
		List<QuestionRaw> questions = new ArrayList<>();
		
		verifySource();
		processRawData(questions);
		
		return questions;
	}
	
	private void processRawData(List<QuestionRaw> questions){	

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
}
