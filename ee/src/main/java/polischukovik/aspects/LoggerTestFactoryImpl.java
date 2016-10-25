package polischukovik.aspects;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import polischukovik.domain.enums.PropertyNames;
import polischukovik.mslibrary.Properties;
import polischukovik.mslibrary.TestFactoryImpl;
import polischukovik.services.TestFactory;

@Aspect
@Component
public class LoggerTestFactoryImpl {
	private Logger logger = LogManager.getLogger(TestFactoryImpl.class.getName());
	
	@Pointcut("execution(* polischukovik.mslibrary.TestFactoryImpl.createTest(..))"
			+ "&& args(questions)")
	private void withArgs(Object questions){};
	
	@Pointcut("execution(* polischukovik.mslibrary.TestFactoryImpl.createTest(..))")
	private void withNoArgs(){};
	
	@Before("withArgs(questions)")
	public void startCreateQuestions(Object questions){
		logger.debug(String.format("\tCreating Test class. Number of questions: %d", ((List<?>) questions).size()));
		
		logger.debug(String.format("\tRequired parameters(%d):", TestFactoryImpl.getRequiredProperties().size()));
		for(PropertyNames propertyName : TestFactoryImpl.getRequiredProperties()){
			logger.debug(String.format("\t\t%s", propertyName));
		}		
	}
	
	@AfterReturning("withArgs(questions)")
	public void endCreateQuestions(Object questions){
		logger.debug(String.format("\tTest class have been successfully created"));
	}
	
	@AfterThrowing("withNoArgs()")
	public void startCreateQuestionsException(){
		logger.fatal("Failed to parse questions");
	}

}
