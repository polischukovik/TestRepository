package polischukovik.aspects;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import polischukovik.impl.TestFactoryImpl;

@Aspect
@Component
public class LoggerTestFactoryImpl {
	
	private Log logger = LogFactory.getLog(TestFactoryImpl.class);
	
	@Pointcut("execution(* polischukovik.impl.TestFactoryImpl.createTest(..))"
			+ "&& args(questions)")
	private void withArgs(Object questions){};
	
	@Before("withArgs(questions)")
	public void startCreateQuestions(Object questions){
		logger.debug(String.format("\tCreating Test class. Number of questions: %d", ((List<?>) questions).size()));	
	}
	
	@AfterReturning("withArgs(questions)")
	public void endCreateQuestions(Object questions){
		logger.debug(String.format("\tTest class have been successfully created"));
	}
	
	@AfterThrowing("withArgs(questions)")
	public void startCreateQuestionsException(Object questions){
		logger.fatal("Failed to parse questions");
	}

}
