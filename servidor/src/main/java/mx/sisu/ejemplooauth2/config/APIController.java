package mx.sisu.ejemplooauth2.config;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value="/api")
public class APIController {

	@Autowired
	TokenStore tokenStore;
	
	@RequestMapping(value="/saluda", method= RequestMethod.GET)
	public ResponseEntity<String> test(){
		return new ResponseEntity<String>("Hola mundo", HttpStatus.OK);
	}
	
	@RequestMapping(value="/user/posts")
	public ResponseEntity<List<PostDTO>> getPosts(){
		
		List<PostDTO> posts = new ArrayList<>();
		for(int i = 0; i < 10; i++){
			PostDTO post = new PostDTO();
			post.setAutor("Autor " + i);
			post.setCuerpo("Cuerpo " + i);
			post.setTitulo("Título " + i);
			post.setFechaPublicacion(new Timestamp(System.currentTimeMillis()));
			posts.add(post);
		}
		return new ResponseEntity<>(posts, HttpStatus.OK);
	}
	
	@RequestMapping(value="/user/getId")
	public ResponseEntity<String> getId(){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String clientName = ((OAuth2Authentication) auth).getUserAuthentication().getName();
		return new ResponseEntity<String>(clientName, HttpStatus.OK);
	}

}
