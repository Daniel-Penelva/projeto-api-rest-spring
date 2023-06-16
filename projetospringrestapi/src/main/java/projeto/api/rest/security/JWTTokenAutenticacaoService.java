package projeto.api.rest.security;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import projeto.api.rest.ApplicationContextLoad;
import projeto.api.rest.model.Usuario;
import projeto.api.rest.repository.UsuarioRepository;

@Service
@Component
public class JWTTokenAutenticacaoService {
	
	/* Tempo de validade do Token - abaixo está definido 2 dias convertido em milissegundos */
	private static final long EXPIRATION_TIME = 172800000;
	
	/* Uma senha única para compor a autenticação e ajudar na segurança*/
	private static final String SECRET = "SenhaExtremamenteSecreta";
	
	/* Prefixo padrão de token */
	private static final String TOKEN_PREFIX = "Bearer";
	
	/* Para identificar a localização do nosso token, lembrando que fica no cabeçalho (header) a localização. */
	private static final String HEADER_STRING = "Authorization";
	
	
	/* Gerando token de autenticação e adicionando ao cabeçalho e resposta HTTP */
	public void addAuthentication(HttpServletResponse response, String username) throws IOException{
		
		
		/*Mostragem do token:
		 * linha 41 - chama o gerador de token.
		 * linha 42 -  adiciona o usuário. 
		 * linha 43 - tempo de exiparação do token
		 * linha 44 - algoritmo de geração de senha e compactação.
		 * */
		
		String JWT = Jwts.builder()
				.setSubject(username)
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
				.signWith(SignatureAlgorithm.HS512, SECRET).compact();
		
		
		/* Junta o prefixo com o token -> Bearer + o token (JWT) - Por exemplo: (Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9) */
		String token = TOKEN_PREFIX + " " + JWT;
		
		
		/* Adiciona o token no cabeçalho HTTP - Por exemplo: (Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9) */
		response.addHeader(HEADER_STRING, token);
		
		
		/* Escreve o token como resposta no corpo HTTP */
		response.getWriter().write("{\"Authorization\": \""+token+"\"}");
		
	}
	
	
	/* Retorna o usuário validado com o token, ou caso não seja valido retorna null */
	public Authentication getAuthentication(HttpServletRequest request) {
		
		/* Pega o token enviado no cabeçalho HTTP */
		String token = request.getHeader(HEADER_STRING);
		
		if(token != null) {
			
				/* Faz a validação do token do usuário na requisição - entender o processo:
				 * linha 78 - o token chega da seguinte maneira: (Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9) 
				 * linha 79 - vai ficar assim: (eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9)
				 * linha 80 - retorna só o usuário, ficaria assim: Daniel Penelva
				 * */
				
				String user = Jwts.parser()
						.setSigningKey(SECRET)
						.parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
						.getBody().getSubject();
				
				
				if(user != null) {
					
					/* OBS.
					 * Por algum motivo a anotação @Autowired não está injetando as dependências no UsuarioDepository, logo para 
					 * contornar esse problema vamos criar uma classe auxiliar 'ApplicationContextLoad' para capturar dentro da 
					 * memória do Spring essa instância da Classe UsuarioRepository que é onde está a nossa autenticação de login.
					 * O applicationContextLaod ele seria todos os controllers, servlets, os daos, os repositorys que foram carregados
					 * na memória quando o projeto sobe, então o Spring traz para nós nesse contexto de aplicação. 
					 */
					
					Usuario usuario = ApplicationContextLoad.getApplicationContextLoad()
							.getBean(UsuarioRepository.class).findUserByLogin(user);
					
					if(usuario != null) {
						
						return new UsernamePasswordAuthenticationToken(usuario.getLogin(), usuario.getSenha(), usuario.getAuthorities());
						
					} //if 3
					
				} // if 2
			
		} // if 1
		
		// Usuário não autorizado
			return null;
	}

}
