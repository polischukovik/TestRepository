package polischukovik.services;

import java.util.List;

import polischukovik.domain.QuestionRaw;
import polischukovik.domain.Test;
import polischukovik.mslibrary.Properties;

public interface TestFactory {
	public Test createTest(List<QuestionRaw> questions);
}