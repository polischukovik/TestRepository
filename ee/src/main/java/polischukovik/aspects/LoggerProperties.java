package polischukovik.aspects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import polischukovik.domain.enums.PropertyNames;
import polischukovik.mslibrary.Properties;

@Aspect
@Component
public class LoggerProperties {
	
	private Logger logger = LogManager.getLogger(Properties.class.getName());	
	
	@Autowired
	private Properties prop;
	
    @Pointcut("execution(* polischukovik.mslibrary.QuestioRawnHandlerImpl.parseSource(..))")
    public void constructorProperties(){}
    
	@Before("constructorProperties()")
	public void afterPropertiesConstructor(){
		logger.debug(String.format("Enumerating application parameters:"));
		for(PropertyNames propertyName : prop.getAllProperties().keySet()){
			logger.debug(String.format("\tParameter:\t%s\t=>\t%s", propertyName, prop.get(propertyName)));
		}
	}
}
