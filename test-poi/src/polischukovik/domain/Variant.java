package polischukovik.domain;

import java.util.List;
import java.util.Map;

public class Variant {
	private String name;
	private List<Question> questions;
	private Map<Question, String> keys; //could be <Question, List<String>>>
	
	public Variant(String name, List<Question> questions, Map<Question, String> map) {
		super();
		this.name = name;
		this.questions = questions;
		this.keys = map;
	}
	
	public void add(Question question){
		questions.add(question);
	}

	public String getName() {
		return name;
	}

	public List<Question> getQuestions() {
		return questions;
	}

	public Map<Question, String> getKeys() {
		return keys;
	}	
}
