package polischukovik.aspects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import polischukovik.domain.enums.PropertyNames;
import polischukovik.mslibrary.IOTools;

@Aspect
@Component
public class LoggerIOTools {
	
	private Logger logger = LogManager.getLogger(IOTools.class.getName());
	
	@Pointcut("execution(* polischukovik.mslibrary.IOTools.write(..))"
			+ "&& args(doc)")
	private void writeDocument(XWPFDocument doc){}
	
	@Before("writeDocument(doc)")
	private void beforeXWPFDocumentWrite(XWPFDocument doc){
		
		logger.debug("Attempting to create document");
		
		logger.debug(String.format("\tRequired parameters(%d):", IOTools.getRequiredProperties().size()));
		for(PropertyNames propertyName : IOTools.getRequiredProperties()){
			logger.debug(String.format("\t\t%s", propertyName));
		}	
	}
	
	@AfterReturning("writeDocument(doc)")
	private void afterXWPFDocumentWrite(XWPFDocument doc){
		
		logger.debug("Document has been successfully saved");
	}
	
	@AfterThrowing("writeDocument(doc)")
	private void afterExceptionXWPFDocumentWrite(XWPFDocument doc){
		
		logger.fatal("Failed to create document");
	}

}
