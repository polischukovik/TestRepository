package polischukovik.aspects;

import java.io.File;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import polischukovik.domain.enums.PropertyNames;
import polischukovik.mslibrary.QuestioRawnHandlerImpl;

@Aspect
@Component
public class LoggingQuestionRawHandlerImpl {
	
	private Logger logger = LogManager.getLogger(QuestioRawnHandlerImpl.class.getName());
	
	@Pointcut("execution(* polischukovik.mslibrary.QuestioRawnHandlerImpl.parseSource(..))"
			+ "&& args(path)")
	private void parseSource(String path){};
	
	@Before("parseSource(path)")
	public void startParseSource(String path){
		logger.debug("Parsing questions from raw file:  " + new File(path).getAbsolutePath());
		
		logger.debug(String.format("\tRequired parameters(%d):", QuestioRawnHandlerImpl.getRequiredProperties().size()));
		for(PropertyNames propertyName : QuestioRawnHandlerImpl.getRequiredProperties()){
			logger.debug(String.format("\t\t%s", propertyName));
		}
	}
	
	@AfterReturning("parseSource(path)")
	public void endParseSource(String path){
		logger.debug("Successfully finnished parsing raw file:  " + new File(path).getAbsolutePath());
	}
	
	@AfterThrowing("parseSource(path)")
	public void startParseSourceException(String path){
		logger.fatal("Failed to parse:  " + new File(path).getAbsolutePath());
	}
}
