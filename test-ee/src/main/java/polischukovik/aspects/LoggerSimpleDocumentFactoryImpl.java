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

import polischukovik.domain.Test;
import polischukovik.domain.enums.PropertyName;
import polischukovik.impl.SimpleDocumentFactoryImpl;
import polischukovik.services.DocumentComponentComposer;

@Aspect
@Component
public class LoggerSimpleDocumentFactoryImpl {
	
	private Log logger = LogFactory.getLog(SimpleDocumentFactoryImpl.class);
	
	@Pointcut("execution(* polischukovik.impl.SimpleDocumentFactoryImpl.createDocument(..))"
			+ "&& args(test, componentComposers)")
	private void createDocumentPointcut(Test test, List<DocumentComponentComposer> componentComposers){}
	
	@Before("createDocumentPointcut(test, componentComposers)")
	private void startCreateDocument(Test test, List<DocumentComponentComposer> componentComposers){
		logger.debug(String.format("Creating document:"));
		
		SimpleDocumentFactoryImpl instance;
		try {
			instance = (SimpleDocumentFactoryImpl) Class.forName(SimpleDocumentFactoryImpl.class.getName()).newInstance();
			
			logger.debug(String.format("\tRequired parameters(%d):", instance.getRequiredProperties().size()));
			for(PropertyName propertyName :  instance.getRequiredProperties()){
				logger.debug(String.format("\t\t%s", propertyName));
			}
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			e.printStackTrace();
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
