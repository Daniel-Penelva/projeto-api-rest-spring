package projeto.api.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@EntityScan(basePackages = {"projeto.api.rest.model"})
@ComponentScan(basePackages = {"projeto.*"})
@EnableJpaRepositories(basePackages = {"projeto.api.rest.repository"})
@EnableTransactionManagement
@EnableWebMvc
@RestController
@EnableAutoConfiguration
public class ProjetospringrestapiApplication implements WebMvcConfigurer{

	public static void main(String[] args) {
		SpringApplication.run(ProjetospringrestapiApplication.class, args);
	}
	
	
	/* Para Mapeamento Global que refletem em todo o sistema */
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		
		// Libera o acesso a todos os controllers e todos os end-points (Métodos HTTP).
		//registry.addMapping("/**");
		
		// Liberar todos os end-points que estão dentro do usuário, no caso, todo o /usuario que estão dentro do IndexController
		//registry.addMapping("/usuario");
		
		// Liberar todos os acessos do /usuario que estão dentro do IndexController
		//addMapping retorna um 'pathPattern' do tipo String. E nele podemos trabalhar com cabeçalho, método, origins ...
		//allowedMethods("*") o asteristico é para liberar para todos os métodos HTTP (end-points).
		//registry.addMapping("/usuario/**").allowedMethods("*");
		
		//Para os métodos que você especificar para ficar liberado, no caso, o POST e o DELETE
		//registry.addMapping("/usuario/**").allowedMethods("POST", "DELETE");
		
		// Também pode dar a origins para liberar requisições POST e DELETE que vierem somente do servidor da google.com.
		//registry.addMapping("/usuario/**").allowedMethods("POST", "DELETE").allowedOrigins("https://www.google.com/");
		
		// Para mais de um servidor, tb pode...
		//registry.addMapping("/usuario/**").allowedMethods("POST", "DELETE").allowedOrigins("https://www.google.com/", "https://github.com/");
		
		// Para liberar o acesso para sua máquina local
		//registry.addMapping("/usuario/**").allowedMethods("POST", "DELETE").allowedOrigins("http://8080");
		
		registry.addMapping("/usuario/**")
		        .allowedOrigins("\"https://www.google.com/\"")
		        .allowedMethods("GET", "POST")
		        .allowedHeaders("Authorization", "Content-Type")
		        .allowCredentials(true).maxAge(3600);
		
	}

}
