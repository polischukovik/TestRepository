package polischukovik.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import polischukovik.domain.enums.QuestionType;

public class Question {
	
	private String id;
	private QuestionType type;
	private String question;
	private List<Answer> answers;
	
	public Question(String id, QuestionType type, String question, List<Answer> answers) {
		super();
		this.id = id;
		this.type = type;
		this.question = question;
		
		this.answers = new ArrayList<>();
		this.answers = answers;
	}
	
	public void shuffleAnswers(Random rnd){
		Collections.shuffle(answers, rnd);		
	}
	
	public String getId() {
		return id;
	}
	public QuestionType getType() {
		return type;
	}
	public String getQuestion() {
		return question;
	}
	public List<Answer> getAnswers() {
		return answers;
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}
}
