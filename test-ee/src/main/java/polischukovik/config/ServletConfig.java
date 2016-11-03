package polischukovik.config;

import java.util.Locale;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
@EnableWebMvc
@ComponentScan("polischukovik")
public class ServletConfig extends WebMvcConfigurerAdapter{
	
	@Bean
	public InternalResourceViewResolver getViewResolver(){
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix("/WEB-INF/views/");
		resolver.setSuffix(".jsp");
		return resolver;
	}
	
	@Bean(name="messageSource")
	public ReloadableResourceBundleMessageSource resourceBundleMessageSource(){
        ReloadableResourceBundleMessageSource messageSource=new ReloadableResourceBundleMessageSource();
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setBasenames("classpath:messages");
        return messageSource;
    }
	
	@Bean(name="localeResolver")
	public SessionLocaleResolver sessionLocaleResolver(){
	    SessionLocaleResolver localeResolver=new SessionLocaleResolver();
	    localeResolver.setDefaultLocale(new Locale("en"));
	    return localeResolver;
	}   

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		LocaleChangeInterceptor localeChangeInterceptor=new LocaleChangeInterceptor();
		localeChangeInterceptor.setParamName("language");
	    registry.addInterceptor(localeChangeInterceptor);
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
		registry.addResourceHandler("/webjars/**").addResourceLocations("/webjars/");
	}
}
