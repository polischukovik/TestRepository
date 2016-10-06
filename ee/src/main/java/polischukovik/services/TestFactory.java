package polischukovik.services;

import polischukovik.domain.Test;
import polischukovik.mslibrary.Properties;

public interface TestFactory {
	public Test createTest(Properties prop);
}