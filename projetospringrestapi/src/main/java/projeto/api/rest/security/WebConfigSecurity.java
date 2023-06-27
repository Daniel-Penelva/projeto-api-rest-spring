package projeto.api.rest.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
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
		
		/* linha 39 - Ativando a proteção contra usuário que não estão validados por token 
		 * linha 40 - Ativando a permissão para acesso a página inicial do sistema, exemplo, "sistema.com.br/index.html" 
		 * linha 41 - URL de logout, ele redireciona após o user deslogar o sistema 
		 * linha 42 - garante a compatibilidade e funcionalidade adequada em cenários que envolvem comunicação entre diferentes domínios (CORS).
		 * linha 43 - Mapeia a URL de logout e invalida o usuário
		 * linha 44 - Filtra requisições de login para autenticação
		 * linha 45 - Filtra demais requisições para verificar a presenção do TOKEN JWT no HEADER HTTP */
		
		http.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
		.disable().authorizeRequests().antMatchers("/").permitAll().antMatchers("/index").permitAll()
		.antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
		.anyRequest().authenticated().and().logout().logoutSuccessUrl("/index")
		.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
		.and().addFilterBefore(new JWTLoginFilter("/login", authenticationManager()), UsernamePasswordAuthenticationFilter.class)
		.addFilterBefore(new JWTApiAutenticacaoFilter(), UsernamePasswordAuthenticationFilter.class);
		
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
			
		/* Service que irá consultar o usuário no banco de dados. No password vamos utilizar 'BCryptPasswordEncoder()' que é um 
		 * padrão de codificação da senha do usuário*/
		
		auth.userDetailsService(implementacaoUserDetailsService).passwordEncoder(new BCryptPasswordEncoder());
	}

}
