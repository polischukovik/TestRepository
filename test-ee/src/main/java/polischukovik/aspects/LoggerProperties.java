package polischukovik.aspects;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import polischukovik.properties.Properties;

@Aspect
@Component
public class LoggerProperties {
	
	private Log logger = LogFactory.getLog(Properties.class);	
	
	@Autowired
	private Properties prop;
	
    @Pointcut("execution(* polischukovik.mslibrary.QuestioRawnHandlerImpl.parseSource(..))")
    public void constructorProperties(){}
    
	@Before("constructorProperties()")
	public void afterPropertiesConstructor(){
//		logger.debug(String.format("Enumerating application parameters:"));
//		for(PropertyNames propertyName : prop.getAllProperties().keySet()){
//			logger.debug(String.format("\tParameter:\t%s\t=>\t%s", propertyName, prop.get(propertyName)));
//		}
	}
}
