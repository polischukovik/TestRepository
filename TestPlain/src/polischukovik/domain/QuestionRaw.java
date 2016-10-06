package polischukovik.domain;

import polischukovik.domain.enums.QuestionType;

public class QuestionRaw {
	private QuestionType type;
	private String question;
	private String[] answers;
	private Integer[] correct;
	public QuestionRaw(QuestionType type, String question, String[] answers, Integer[] correct) {
		super();
		this.type = type;
		this.question = question;
		this.answers = answers;
		this.correct = correct;
	}
	public QuestionType getType() {
		return type;
	}
	public String getQuestion() {
		return question;
	}
	public String[] getAnswers() {
		return answers;
	}
	public Integer[] getCorrect() {
		return correct;
	}
}
