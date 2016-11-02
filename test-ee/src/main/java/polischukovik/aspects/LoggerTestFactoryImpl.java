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

import polischukovik.domain.enums.PropertyName;
import polischukovik.impl.TestFactoryImpl;

@Aspect
@Component
public class LoggerTestFactoryImpl {
	private Log logger = LogFactory.getLog(TestFactoryImpl.class);
	
	@Pointcut("execution(* polischukovik.mslibrary.TestFactoryImpl.createTest(..))"
			+ "&& args(questions)")
	private void withArgs(Object questions){};
	
	@Pointcut("execution(* polischukovik.mslibrary.TestFactoryImpl.createTest(..))")
	private void withNoArgs(){};
	
	@Before("withArgs(questions)")
	public void startCreateQuestions(Object questions){
		logger.debug(String.format("\tCreating Test class. Number of questions: %d", ((List<?>) questions).size()));
		
		TestFactoryImpl instance; 
		
		try {
			instance = (TestFactoryImpl) Class.forName(TestFactoryImpl.class.getName()).newInstance();
			
			logger.debug(String.format("\tRequired parameters(%d):", instance.getRequiredProperties().size()));
			for(PropertyName propertyName : instance.getRequiredProperties()){
				logger.debug(String.format("\t\t%s", propertyName));
			}		
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			logger.error("Failed to retrieve Component properties", e);
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
