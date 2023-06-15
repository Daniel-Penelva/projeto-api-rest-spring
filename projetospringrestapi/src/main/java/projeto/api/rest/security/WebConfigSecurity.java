package projeto.api.rest.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import projeto.api.rest.service.ImplementacaoUserDetailsService;

/* Vamos utilizar essa classe para mapear URL, endereços, autorizações e bloqueios de acesso a URL*/

@Configuration
@EnableWebSecurity
public class WebConfigSecurity extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private ImplementacaoUserDetailsService implementacaoUserDetailsService;
	
	
	/* Configura as solicitações de acesso por HTTP */
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		/* linha 35 - Ativando a proteção contra usuário que não estão validados por token 
		 * linha 36 - Ativando a permissão para acesso a página inicial do sistema, exemplo, "sistema.com.br/index.html" 
		 * linha 37 - URL de logout, ele redireciona após o user deslogar o sistema 
		 * linha 38 - Mapeia a URL de logout e invalida o usuário*/
		
		http.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
		.disable().authorizeRequests().antMatchers("/").permitAll().antMatchers("/index").permitAll()
		.anyRequest().authenticated().and().logout().logoutSuccessUrl("/index")
		.logoutRequestMatcher(new AntPathRequestMatcher("/logout"));
		
		/* Filtra requisições de login para autenticação */
		
		
		/* Filtra demais requisições para verificar a presenção do TOKEN JWT no HEADER HTTP */
		
	}
	
	@Override
		protected void configure(AuthenticationManagerBuilder auth) throws Exception {
			
		    /* Service que irá consultar o usuário no banco de dados. No password vamos utilizar 'BCryptPasswordEncoder()' que é um 
		     * padrão de codificação da senha do usuário*/
		
			auth.userDetailsService(implementacaoUserDetailsService).passwordEncoder(new BCryptPasswordEncoder());
		}

}
