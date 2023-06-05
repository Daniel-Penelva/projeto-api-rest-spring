package projeto.api.rest.controller;

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

	/* Serviço RestFull */
	@GetMapping(value = "/", produces = "application/json")
	public ResponseEntity init() {
		
		Usuario usuario = new Usuario();
		usuario.setId(1L);
		usuario.setLogin("d4n.andrade@gmail.com");
		usuario.setSenha("123");
		usuario.setNome("Daniel");
			
		return ResponseEntity.ok(usuario);  // http://localhost:8080/usuario/ 
	}

}
