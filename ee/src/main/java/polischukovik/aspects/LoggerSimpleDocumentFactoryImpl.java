package polischukovik.aspects;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import polischukovik.domain.Test;
import polischukovik.domain.enums.PropertyNames;
import polischukovik.msformating.SimpleDocumentFactoryImpl;
import polischukovik.msformating.interfaces.DocumentComponentComposer;

@Aspect
@Component
public class LoggerSimpleDocumentFactoryImpl {
	
	private Logger logger = LogManager.getLogger(SimpleDocumentFactoryImpl.class.getName());
	
	@Pointcut("execution(* polischukovik.msformating.SimpleDocumentFactoryImpl.createDocument(..))"
			+ "&& args(test, componentComposers)")
	private void createDocumentPointcut(Test test, List<DocumentComponentComposer> componentComposers){}
	
	@Before("createDocumentPointcut(test, componentComposers)")
	private void startCreateDocument(Test test, List<DocumentComponentComposer> componentComposers){
		logger.debug(String.format("Creating document:"));
		
		logger.debug(String.format("\tRequired parameters(%d):", SimpleDocumentFactoryImpl.getRequiredProperties().size()));
		for(PropertyNames propertyName : SimpleDocumentFactoryImpl.getRequiredProperties()){
			logger.debug(String.format("\t\t%s", propertyName));
		}
		
		logger.debug(String.format("From test class\t%s", test));
		
		logger.debug(String.format("Using following document composers:"));
		for(DocumentComponentComposer dcc : componentComposers){
			logger.debug(String.format("\t", dcc.getComposerName()));
		}		
	};
	
	@AfterReturning("createDocumentPointcut(test, componentComposers)")
	private void endCreateDocument(Test test, List<DocumentComponentComposer> componentComposers){
		logger.debug(String.format("Document created"));
	}
	
	@AfterThrowing("createDocumentPointcut(test, componentComposers)")
	private void ExceptionCreateDocument(Test test, List<DocumentComponentComposer> componentComposers){
		logger.fatal(String.format("Failed to creat document"));
	}
}
