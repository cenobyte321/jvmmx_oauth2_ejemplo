package mx.sisu.ejemplooauth2.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@Order(-11)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth
                .inMemoryAuthentication()
                    .withUser("myuser")
                    .password("mypassword")
                    .roles("USER")
                .and()
                    .withUser("test")
				    .password("testpassword")
                .roles("USER");
	}

    @Override
    protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.logout()
        	.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
        	.invalidateHttpSession(true)
        	.logoutSuccessUrl("/login.html");
		 
		 http.authorizeRequests()
	        .antMatchers("/login.html")
	        .permitAll()
	        
        .and()
	        .formLogin()
	        .loginPage("/login.html")
	        .permitAll()
	        .loginProcessingUrl("/login")
	        .usernameParameter("username")
	        .passwordParameter("password")
	        .and()
			.requestMatchers().antMatchers("/login","/logout","/oauth/authorize")
			.and()
				.authorizeRequests().anyRequest().authenticated()
        .and().exceptionHandling().accessDeniedPage("/denied.html");

    }

}



