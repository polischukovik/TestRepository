package polischukovik.aspects;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import polischukovik.domain.PropertyComponent;
import polischukovik.properties.Properties;
import polischukovik.web.service.TestService;

//@Aspect
@Component
public class LoggerInitialize {
	
	private Log logger = LogFactory.getLog(TestService.class);
	
	@Autowired
	private Properties prop;
	
	@Pointcut("execution(* polischukovik.web.controller.TestController.createTest(..))")
	private void createTest(){};
	
	@Before("createTest()")
	public void beforeCreateTest(){
		logger.debug("Listing Properties:");
		for( PropertyComponent pc : prop.getAll()){
			logger.debug(String.format("Property name[%s]\t\tvalue[%s]", pc.getName(), pc.getValue()));
		}

	}
}
