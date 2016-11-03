package polischukovik.aspects;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import polischukovik.impl.IOToolsImpl;

@Aspect
@Component
public class LoggerIOTools {
	
	private Log logger = LogFactory.getLog(IOToolsImpl.class);
	
	@Pointcut("execution(* polischukovik.impl.IOToolsImpl.write(..))"
			+ "&& args(doc)")
	private void writeDocument(XWPFDocument doc){}
	
	@Pointcut("execution(* polischukovik.impl.IOToolsImpl.read(..))")
	private void readPointcut(){}
	
	@Before("readPointcut()")
	private void beforeRead(){		
		logger.debug("Attempting to read file");		
	}
	
	@AfterReturning("readPointcut()")
	private void afterRead(){
		logger.debug("Successfully compleated reading file");
	}
	
	@AfterThrowing("readPointcut()")
	private void exceptionRead(){
		logger.fatal("Failed to read document");
	}
	
	@Before("writeDocument(doc)")
	private void beforeWrite(XWPFDocument doc){		
		logger.debug("Attempting to create document");		
	}
	
	@AfterReturning("writeDocument(doc)")
	private void afterWrite(XWPFDocument doc){
		logger.debug("Document has been successfully saved");
	}
	
	@AfterThrowing("writeDocument(doc)")
	private void exceptiontWrite(XWPFDocument doc){
		logger.fatal("Failed to create document");
	}

}
