package polischukovik.services;

import java.io.FileNotFoundException;
import java.util.List;

import polischukovik.domain.QuestionRaw;

public interface QuestionDataSource {
	public List<QuestionRaw> parseSource(String sourceFile) throws FileNotFoundException;
}
