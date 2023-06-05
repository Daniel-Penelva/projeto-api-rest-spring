package projeto.api.rest.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import projeto.api.rest.model.Usuario;
import projeto.api.rest.repository.UsuarioRepository;

/* Arquitetura RestFull */
@RestController
@RequestMapping(value = "/usuario")
public class IndexController {

	@Autowired
	private UsuarioRepository usuarioRepository;

	/* Serviço RestFull */
	@GetMapping(value = "/{id}", produces = "application/json")
	public ResponseEntity<Usuario> init(@PathVariable(value = "id") Long id) {

		Optional<Usuario> usuario = usuarioRepository.findById(id);

		return new ResponseEntity<Usuario>(usuario.get(), HttpStatus.OK); // http://localhost:8080/usuario/1
	}
	
	
	// Consultar lista de usuários
	@GetMapping(value = "/", produces = "application/json")
	public ResponseEntity<List<Usuario>> listaUsuario(){
		
		List<Usuario> listUsu = (List<Usuario>) usuarioRepository.findAll();
		
		return new ResponseEntity<List<Usuario>>(listUsu, HttpStatus.OK); // http://localhost:8080/usuario/
	}

}
