package mx.sisu.ejemplooauth2.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;


@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter{

	@Autowired
	AuthenticationManager authenticationManager;
	
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
        .authenticationManager(authenticationManager)
        .tokenStore(tokenStore())
        .tokenEnhancer(tokenEnhancer())
        ;
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
            clients
            .inMemory()
            	.withClient("client_authorization_code")
            	.secret("secret")
            	.authorizedGrantTypes("authorization_code", "refresh_token")
            	.scopes("read")
            	.redirectUris("http://example.com", "http://localhost:8081/client/")
            	.resourceIds("resource")
            .and()
            	.withClient("client_implicit")
            	.secret("secret")
            	.authorizedGrantTypes("implicit")
            	.scopes("read")
            	.resourceIds("resource")
            .and()
                .withClient("client_password")
                .secret("secret")
                .authorizedGrantTypes("password", "refresh_token")
                .scopes("read", "write")
                .resourceIds("resource").accessTokenValiditySeconds(8000)
            .and()
            	.withClient("client_client_credentials")
            	.secret("secret")
            	.authorizedGrantTypes("client_credentials")
            	.scopes("other")
            	.resourceIds("resource")
                ;
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception
    {
       oauthServer.checkTokenAccess("isAuthenticated()");    
    }

   

    @Bean
    public TokenStore tokenStore() {
        return new InMemoryTokenStore();
    }
    
    @Bean
    public TokenEnhancer tokenEnhancer() {
        return new CustomTokenEnhancer();
    }

}