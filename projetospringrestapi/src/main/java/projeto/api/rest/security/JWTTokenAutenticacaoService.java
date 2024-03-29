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
		
		// Código implementado para o projeto do Angular 8 - libera a resposta para um servidor diferente do projeto angular: localhost:4200
		// Se esse header for igual a nulo, então tem que adicionar esse parametro.
		liberacaoCors(response);
		
		ApplicationContextLoad.getApplicationContextLoad()
		.getBean(UsuarioRepository.class).atualizarTokenUser(JWT, username);
		
		/* Escreve o token como resposta no corpo HTTP */
		response.getWriter().write("{\"Authorization\": \""+token+"\"}");
		
	}
	
	
	/* Retorna o usuário validado com o token, ou caso não seja valido retorna null */
	public Authentication getAuthentication(HttpServletRequest request, HttpServletResponse response) {
		
		/* Pega o token enviado no cabeçalho HTTP */
		String token = request.getHeader(HEADER_STRING);
		
	try {
			
		if(token != null) {
			
			String tokenLimpo = token.replace(TOKEN_PREFIX, "").trim();
			
				/* Faz a validação do token do usuário na requisição - entender o processo:
				 * linha 85 - o token chega da seguinte maneira: (Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9) 
				 * linha 86 - vai ficar assim: (eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9)
				 * linha 87 - retorna só o usuário, ficaria assim: Daniel Penelva
				 * */
				
				String user = Jwts.parser()
						.setSigningKey(SECRET)
						.parseClaimsJws(tokenLimpo)
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
						
						// Se o token cadastrado no BD for igual ao que veio da requisição, vai ser validado.
						if(tokenLimpo.equalsIgnoreCase(usuario.getToken())) {
						
							return new UsernamePasswordAuthenticationToken(usuario.getLogin(), usuario.getSenha(), usuario.getAuthorities());
						
						} // if 4
						
					} //if 3
					
				} // if 2
			
		} // if 1
		
	   } catch (io.jsonwebtoken.ExpiredJwtException e) {
			try {
				response.getOutputStream().println("Seu token está expirado! Faça o login ou informe um novo token para autenticação");
				
			} catch (IOException e1) {
				
			}
	   }
		
		// Código implementado para o projeto do Angular 8 - libera a resposta para um servidor diferente do projeto angular: localhost:4200
		// Se esse header for igual a nulo, então tem que adicionar esse parametro.
		liberacaoCors(response);
		
		// Usuário não autorizado
			return null;
	}
	
	
	private void liberacaoCors(HttpServletResponse response) {
		if(response.getHeader("Access-Control-Allow-Origin") == null) {
			response.addHeader("Access-Control-Allow-Origin", "*");
		}
		
		if(response.getHeader("Access-Control-Allow-Headers") == null) {
			response.addHeader("Access-Control-Allow-Headers", "*");
		}
		
		if(response.getHeader("Access-Control-Request-Headers") == null) {
			response.addHeader("Access-Control-Request-Headers", "*");
		}
		
		// Liberando Cors para deletar o usuario
		if(response.getHeader("Access-Control-Allow-Methods") == null) {
			response.addHeader("Access-Control-Allow-Methods", "*");
		}
	}

}


