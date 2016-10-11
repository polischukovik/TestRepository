package polischukovik.services;

import java.io.FileNotFoundException;
import java.util.List;

import polischukovik.domain.QuestionRaw;

public interface QuestioRawnHandler {
	public List<QuestionRaw> parseSource() throws FileNotFoundException;
}
