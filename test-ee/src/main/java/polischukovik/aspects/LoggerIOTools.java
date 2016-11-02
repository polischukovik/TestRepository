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

import polischukovik.domain.enums.PropertyName;
import polischukovik.impl.IOToolsImpl;

@Aspect
@Component
public class LoggerIOTools {
	
	private Log logger = LogFactory.getLog(IOToolsImpl.class);
	
	@Pointcut("execution(* polischukovik.mslibrary.IOTools.write(..))"
			+ "&& args(doc)")
	private void writeDocument(XWPFDocument doc){}
	
	@Before("writeDocument(doc)")
	private void beforeXWPFDocumentWrite(XWPFDocument doc){
		
		logger.debug("Attempting to create document");
		
		IOToolsImpl instance;
		try {
			instance = (IOToolsImpl) Class.forName(IOToolsImpl.class.getName()).newInstance();
			
			logger.debug(String.format("\tRequired parameters(%d):", instance.getRequiredProperties().size()));
			for(PropertyName propertyName :  instance.getRequiredProperties()){
				logger.debug(String.format("\t\t%s", propertyName));
			}
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			e.printStackTrace();
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
