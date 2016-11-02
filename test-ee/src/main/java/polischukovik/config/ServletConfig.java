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
	
	@Bean
	public ReloadableResourceBundleMessageSource resourceBundleMessageSource(){
        ReloadableResourceBundleMessageSource messageSource=new ReloadableResourceBundleMessageSource();
        String[] resources= {"classpath:messages"};
        messageSource.setBasenames(resources);
        return messageSource;
    }
	
	@Bean
	public SessionLocaleResolver sessionLocaleResolver(){
	    SessionLocaleResolver localeResolver=new SessionLocaleResolver();
	    localeResolver.setDefaultLocale(new Locale("en"));
	    return localeResolver;
	}   
	
	@Bean 
	public LocaleChangeInterceptor localeChangeInterceptor(){
	    LocaleChangeInterceptor localeChangeInterceptor=new LocaleChangeInterceptor();
	    localeChangeInterceptor.setParamName("locale");
	    return localeChangeInterceptor;
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
	    registry.addInterceptor(localeChangeInterceptor());
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**").addResourceLocations("/resource/");
		registry.addResourceHandler("/webjars/**").addResourceLocations("/webjars/");
	}
}
