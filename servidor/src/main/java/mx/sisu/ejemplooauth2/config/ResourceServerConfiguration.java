package mx.sisu.ejemplooauth2.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;

@Configuration
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter{

    @Autowired
    TokenStore tokenStore;
    
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources
        .resourceId("resource")
        .tokenStore(tokenStore);
    }
    @Override
    public void configure(final HttpSecurity http) throws Exception {
    	http.antMatcher("/api/**")
            .authorizeRequests().anyRequest()
		    .access("#oauth2.hasScope('read')");
 }
}

