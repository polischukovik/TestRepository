package polischukovik.services;

import java.io.FileNotFoundException;
import java.util.List;

import polischukovik.domain.QuestionRaw;
import polischukovik.domain.enums.PropertyNames;

public interface QuestioRawnHandler {
	public List<QuestionRaw> parseSource(String sourceFile) throws FileNotFoundException;
}
