package training.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
            .inMemoryAuthentication()
                .withUser("alex").password("asdf").roles("USER").and()
                .withUser("user").password("asdf").roles("DENIDE");
    }

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.authorizeRequests()
			.antMatchers("/resources/**", "/webjars/**").permitAll() 
			.antMatchers("/**").hasRole("USER")   
			.anyRequest().authenticated()
			.and()
		.formLogin()
			.loginPage("/login") 
			.permitAll()		
			.and()
		.logout()
			.permitAll()
			.and()
		.exceptionHandling()
			.accessDeniedPage("/403")
			;
		
	}
}
