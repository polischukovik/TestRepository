package polischukovik.aspects;

import java.io.File;
import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import polischukovik.domain.enums.PropertyName;
import polischukovik.impl.QuestionDataSourceFileImpl;

@Aspect
@Component
public class LoggingQuestionRawHandlerImpl {
	
	private Log logger = LogFactory.getLog(QuestionDataSourceFileImpl.class);
	
	@Pointcut("execution(* polischukovik.impl.QuestionDataSourceFileImpl.parseSource(..))"
			+ "&& args(path)")
	private void parseSource(String path){};
	
	@Before("parseSource(path)")
	public void startParseSource(String path) throws IOException{
		logger.debug("Parsing questions from raw file:  " + path + "\n");
		
		QuestionDataSourceFileImpl instance;
		try {
			instance = (QuestionDataSourceFileImpl) Class.forName(QuestionDataSourceFileImpl.class.getName()).newInstance();
			
			logger.debug(String.format("\tRequired parameters(%d):", instance.getRequiredProperties().size()));
			for(PropertyName propertyName :  instance.getRequiredProperties()){
				logger.debug(String.format("\t\t%s", propertyName));
			}
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		
	}
	
	@AfterReturning("parseSource(path)")
	public void endParseSource(String path) throws IOException{
		logger.debug("Successfully finnished parsing raw file:  " + path +"\n");
	}
	
	@AfterThrowing("parseSource(path)")
	public void startParseSourceException(String path){
		logger.fatal("Failed to parse:  "  + path +"\n");
	}
}
