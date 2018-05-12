package polischukovik.domain;

public class Answer {	
	private String label;
	private String answer;
	private boolean isCorrect;
	
	public Answer(String label, String answer, boolean isCorrect) {
		super();
		this.label = label;
		this.answer = answer;
		this.isCorrect = isCorrect;
	}

	public String getAnswer() {
		return answer;
	}

	public String getLabel() {
		return label;
	}

	public boolean isCorrect() {
		return isCorrect;
	}
}
