package projeto.api.rest.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import projeto.api.rest.model.Usuario;
import projeto.api.rest.repository.UsuarioRepository;

/* Arquitetura RestFull */
@CrossOrigin(origins = "https://google.com/")
@RestController
@RequestMapping(value = "/usuario")
public class IndexController {

	@Autowired
	private UsuarioRepository usuarioRepository;

	/* Serviço RestFull */
	// Consultar usuário por id
	@GetMapping(value = "/{id}", produces = "application/json")
	public ResponseEntity<Usuario> init(@PathVariable(value = "id") Long id) {

		Optional<Usuario> usuario = usuarioRepository.findById(id);

		return new ResponseEntity<Usuario>(usuario.get(), HttpStatus.OK); // http://localhost:8080/usuario/1
	}

	// Consultar lista de usuários
	@GetMapping(value = "/", produces = "application/json")
	public ResponseEntity<List<Usuario>> listaUsuario() {

		List<Usuario> listUsu = (List<Usuario>) usuarioRepository.findAll();

		return new ResponseEntity<List<Usuario>>(listUsu, HttpStatus.OK); // http://localhost:8080/usuario/
	}

	// Enviar dados de usuário
	@PostMapping(value = "/", produces = "application/json")
	public ResponseEntity<Usuario> cadastrar(@RequestBody Usuario usuario) {

		// Criando a referência do telefone com o usuário - vai amarrar os telefones a esse usuário
		for (int pos = 0; pos < usuario.getTelefones().size(); pos++) {
			usuario.getTelefones().get(pos).setUsuario(usuario);
		}

		Usuario usuarioSalvo = usuarioRepository.save(usuario);

		return new ResponseEntity<Usuario>(usuarioSalvo, HttpStatus.OK); // http://localhost:8080/usuario/
	}

	// Para simular como se estivesse chamando um outro método
	@PostMapping(value = "/vendausuario", produces = "application/json")
	public ResponseEntity<Usuario> cadastrarVenda(@RequestBody Usuario usuario) {

		Usuario usuarioSalvo = usuarioRepository.save(usuario);

		return new ResponseEntity<Usuario>(usuarioSalvo, HttpStatus.OK); // http://localhost:8080/usuario/vendausuario/
	}

	// Para simular como se estivesse chamando um outro método
	@PostMapping(value = "/{iduser}/idvenda/{idvenda}", produces = "application/json")
	public ResponseEntity cadastrarVendaUsu(@PathVariable Long iduser, @PathVariable Long idVenda) {

		return new ResponseEntity("id user: " + iduser + " id venda: " + idVenda, HttpStatus.OK); // http://localhost:8080/usuario/5/idvenda/80
	}

	// Atualizar dados de usuário - Tem que passar o id no json do Postman
	@PutMapping(value = "/", produces = "application/json")
	public ResponseEntity<Usuario> atualizar(@RequestBody Usuario usuario) {
		
		// Criando a referência do telefone com o usuário - vai amarrar os telefones a esse usuário
		for (int pos = 0; pos < usuario.getTelefones().size(); pos++) {
			usuario.getTelefones().get(pos).setUsuario(usuario);
		}

		// Neste caso o método save fará a função de atualizar uma vez que estamos
		// mapeando como PutMapping
		Usuario usuarioAtualizar = usuarioRepository.save(usuario);

		return new ResponseEntity<Usuario>(usuarioAtualizar, HttpStatus.OK); // http://localhost:8080/usuario/
	}

	// Para simular como se estivesse chamando um outro método
	@PutMapping(value = "/{iduser}", produces = "application/json")
	public ResponseEntity atualizarUsuId(@PathVariable Long iduser) {
		
		return new ResponseEntity("Atualização com sucesso", HttpStatus.OK); // http://localhost:8080/usuario/5/
	}

	// Deletar dados do usuário - Tem que passar o id no json do Postman
	/*
	 * 
	 * @DeleteMapping(value = "/{id}", produces = "application/json") public String
	 * deletarUsu(@PathVariable("id") Long id){
	 * 
	 * usuarioRepository.deleteById(id);
	 * 
	 * return "Deletado com Sucesso!"; }
	 * 
	 */

	// Consultar usuário por id
	@DeleteMapping(value = "/{id}", produces = "application/json")
	public ResponseEntity<Usuario> deletarUsuPorId(@PathVariable(value = "id") Long id) {

		usuarioRepository.deleteById(id);

		return new ResponseEntity("Usuário Deletado!", HttpStatus.OK); // http://localhost:8080/usuario/1
	}

}
