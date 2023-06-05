package projeto.api.rest.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import projeto.api.rest.model.Usuario;

/* Arquitetura RestFull */
@RestController
@RequestMapping(value = "/usuario")
public class IndexController {
	
	List<Usuario> listaUsu = new ArrayList<Usuario>();

	/* Serviço RestFull */
	@GetMapping(value = "/", produces = "application/json")
	public ResponseEntity init() {
		
		Usuario usuario = new Usuario();
		usuario.setId(1L);
		usuario.setLogin("d4n.andrade@gmail.com");
		usuario.setSenha("123");
		usuario.setNome("Daniel");
		
		Usuario usuario1 = new Usuario();
		usuario1.setId(2L);
		usuario1.setLogin("biana@gmail.com");
		usuario1.setSenha("123");
		usuario1.setNome("Biana");
		
		Usuario usuario3 = new Usuario();
		usuario3.setId(1L);
		usuario3.setLogin("walter@gmail.com");
		usuario3.setSenha("123");
		usuario3.setNome("Walter");
		
		listaUsu.add(usuario);
		listaUsu.add(usuario1);
		listaUsu.add(usuario3);	
			
		// return ResponseEntity.ok(listaUsu);  // http://localhost:8080/usuario/
		
		// ou assim: 
		 return new ResponseEntity(listaUsu, HttpStatus.OK);
	}

}
