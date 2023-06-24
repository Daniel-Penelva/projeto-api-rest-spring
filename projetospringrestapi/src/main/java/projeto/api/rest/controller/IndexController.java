package projeto.api.rest.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import projeto.api.rest.model.Usuario;
import projeto.api.rest.repository.UsuarioRepository;

/* Arquitetura RestFull */
@RestController
@RequestMapping(value = "/usuario")
public class IndexController {

	@Autowired
	private UsuarioRepository usuarioRepository;

	/** No código abaixo vai ser consultado por id - (http://localhost:8080/usuario/1)
	 * @GetMapping(value = "/{id}", produces = "application/json"):
	 *  -> A anotação @GetMapping indica que este método é acionado quando há uma requisição HTTP GET para a URL especificada.
	 *  -> value = "/{id}" define a URL do endpoint, onde {id} é uma variável de caminho que será preenchida com um valor real 
	 *     na solicitação.
	 *  -> produces = "application/json" indica que o endpoint produzirá uma resposta no formato JSON.
	 * 
	 * Optional<Usuario> usuario = usuarioRepository.findById(id);
	 *  -> Optional é um contêiner que pode ou não conter um valor. Neste caso, está sendo usado para lidar com a possibilidade 
	 *     de não encontrar um usuário com o ID especificado.
	 *  -> usuarioRepository é um objeto que realiza operações de acesso a dados relacionados aos usuários. O método 
	 *     findById(id) busca um usuário pelo ID especificado.
	 * 
	 * return new ResponseEntity<Usuario>(usuario.get(), HttpStatus.OK);
	 *  -> cria uma nova instância de ResponseEntity com o usuário encontrado e o status HTTP 200 (OK).
	 *  -> usuario.get() recupera o valor do objeto Optional<Usuario>, ou seja, o usuário encontrado.
	 *  -> HttpStatus.OK é o status HTTP 200 (OK), indicando que a solicitação foi bem-sucedida.
	 * 
	 * No geral, esse script define um endpoint que espera uma requisição GET em /usuario/{id}, onde {id} é um parâmetro 
	 * variável representando o ID de um usuário. O script busca o usuário com o ID fornecido usando um repositório de 
	 * usuário (usuarioRepository) e retorna uma resposta JSON contendo o usuário encontrado, com o status HTTP 200 (OK).
	 * */
	
	@GetMapping(value = "/{id}", produces = "application/json")
	public ResponseEntity<Usuario> initV1(@PathVariable(value = "id") Long id) {

		Optional<Usuario> usuario = usuarioRepository.findById(id);
		System.out.println("Executando versão 1");
		return new ResponseEntity<Usuario>(usuario.get(), HttpStatus.OK); 
	}
	
	
	/*
		// versionamento de API
		@GetMapping(value = "/{id}", produces = "application/json", headers="X-API-Version=v2")
		public ResponseEntity<Usuario> initV2(@PathVariable(value = "id") Long id) {
	
			Optional<Usuario> usuario = usuarioRepository.findById(id);
			System.out.println("Executando versão 2");
			return new ResponseEntity<Usuario>(usuario.get(), HttpStatus.OK); 
		}
    */
	
	/** No código abaixo vai ser consultado uma lista de usuários -http://localhost:8080/usuario/ 
	 * @GetMapping(value = "/", produces = "application/json"):
	 *  -> A anotação @GetMapping indica que este método é acionado quando há uma requisição HTTP GET para a URL especificada.
	 *  -> value = "/" define a URL do endpoint como a raiz, ou seja, o caminho base da API.
	 *  -> produces = "application/json" indica que o endpoint produzirá uma resposta no formato JSON.
	 *  
	 * List<Usuario> listUsu = (List<Usuario>) usuarioRepository.findAll();
	 *  -> List<Usuario> é uma lista que conterá objetos do tipo Usuario.
	 *  -> usuarioRepository é um objeto que realiza operações de acesso a dados relacionados aos usuários.
	 *  -> findAll() é um método do repositório que retorna todos os registros de usuários armazenados.
	 *  
	 * return new ResponseEntity<List<Usuario>>(listUsu, HttpStatus.OK);
	 * -> new ResponseEntity<List<Usuario>>(listUsu, HttpStatus.OK) cria uma nova instância de ResponseEntity com a lista de 
	 *    usuários encontrados e o status HTTP 200 (OK).
	 * -> HttpStatus.OK é o status HTTP 200 (OK), indicando que a solicitação foi bem-sucedida.
	 * 
	 * No geral, esse script define um endpoint que espera uma requisição GET na raiz /usuario/. O script busca todos os 
	 * usuários armazenados no repositório de usuários (usuarioRepository) e retorna uma resposta JSON contendo a lista de 
	 * usuários encontrados, com o status HTTP 200 (OK). Esse endpoint é útil para obter uma lista completa de usuários 
	 * através da API.
	 * */ 
	@GetMapping(value = "/", produces = "application/json")
	public ResponseEntity<List<Usuario>> listaUsuario() {

		List<Usuario> listUsu = (List<Usuario>) usuarioRepository.findAll();

		return new ResponseEntity<List<Usuario>>(listUsu, HttpStatus.OK); 
	}

	
	/** No código abaixo vai ser enviado dados de usuário -http://localhost:8080/usuario/
	 * 
	 *  -> @RequestBody Usuario usuario: a anotação @RequestBody indica que o corpo da requisição HTTP deve ser mapeado 
	 *     para o objeto Usuario passado como parâmetro.
	 *     
	 *  -> No for, o loop percorre a lista de telefones do usuário e define o usuário (usuario) como o usuário associado a 
	 *     cada telefone. Isso estabelece uma relação entre o usuário e seus telefones, vinculando-os no banco de dados.
	 *  
	 *  -> BCryptPasswordEncoder() é uma classe fornecida pelo Spring Security para criptografar senhas usando o algoritmo 
	 *     de criptografia bcrypt. A linha de código acima cria uma instância de BCryptPasswordEncoder e a usa para 
	 *     criptografar a senha do usuário fornecida (usuario.getSenha()). A senha criptografada é armazenada na variável 
	 *     senhaCriptografado. Em seguida, a senha criptografada é definida no objeto usuario, substituindo a senha original 
	 *     pelo valor criptografado.
	 *     
	 *  -> usuarioRepository é um objeto que realiza operações de acesso a dados relacionados aos usuários.
	 *     O método save(usuario) salva o objeto usuario no banco de dados. Isso pode ser interpretado como uma operação de 
	 *     cadastro ou atualização, dependendo se o usuário já existe no banco de dados ou não.
	 *     O objeto usuarioSalvo recebe o usuário salvo no banco de dados, incluindo seu ID gerado pelo sistema.
	 *     
	 *  -> new ResponseEntity<Usuario>(usuarioSalvo, HttpStatus.OK) cria uma nova instância de ResponseEntity com o usuário 
	 *     salvo e o status HTTP 200 (OK).
	 *  -> HttpStatus.OK é o status HTTP 200 (OK), indicando que a solicitação foi bem-sucedida.
	 *  
	 * No geral, esse script recebe um objeto Usuario no corpo da requisição HTTP e realiza as seguintes operações:
	 *    1. Vincula o usuário a seus telefones.
	 *    2. Criptografa a senha do usuário usando o algoritmo bcrypt.
	 *    3. Salva o usuário no banco de dados.
	 *    4. Retorna uma resposta HTTP com o usuário salvo e o status HTTP 200 (OK) indicando que o cadastro foi bem-sucedido.
	 * */ 
	@PostMapping(value = "/", produces = "application/json")
	public ResponseEntity<Usuario> cadastrar(@RequestBody Usuario usuario) {

		// Criando a referência do telefone com o usuário - vai amarrar os telefones a esse usuário
		for (int pos = 0; pos < usuario.getTelefones().size(); pos++) {
			usuario.getTelefones().get(pos).setUsuario(usuario);
		}

		String senhaCriptografado = new BCryptPasswordEncoder().encode(usuario.getSenha());
		usuario.setSenha(senhaCriptografado);
		
		Usuario usuarioSalvo = usuarioRepository.save(usuario);

		return new ResponseEntity<Usuario>(usuarioSalvo, HttpStatus.OK); 
	}
	
	
   /*
		// Para simular como se estivesse chamando um outro método
		@PostMapping(value = "/vendausuario", produces = "application/json")
		public ResponseEntity<Usuario> cadastrarVenda(@RequestBody Usuario usuario) {
	
			Usuario usuarioSalvo = usuarioRepository.save(usuario);
	
			return new ResponseEntity<Usuario>(usuarioSalvo, HttpStatus.OK); // http://localhost:8080/usuario/vendausuario/
		}
		*/
	
		
		/*
		// Para simular como se estivesse chamando um outro método
		@PostMapping(value = "/{iduser}/idvenda/{idvenda}", produces = "application/json")
		public ResponseEntity cadastrarVendaUsu(@PathVariable Long iduser, @PathVariable Long idVenda) {
	
			return new ResponseEntity("id user: " + iduser + " id venda: " + idVenda, HttpStatus.OK); // http://localhost:8080/usuario/5/idvenda/80
		}
	*/

	/** No código abaixo vai ser atualizado dados de usuário - http://localhost:8080/usuario/
	 * @PutMapping(value = "/", produces = "application/json"):
	 *  -> A anotação @PutMapping indica que este método é acionado quando há uma requisição HTTP PUT para a URL especificada.
	 *  -> value = "/" define a URL do endpoint como a raiz, ou seja, o caminho base da API.
	 *  -> produces = "application/json" indica que o endpoint produzirá uma resposta no formato JSON.
	 *  
	 *  -> No for, o loop percorre a lista de telefones do usuário e define o usuário (usuario) como o usuário associado a 
	 *     cada telefone. Isso garante que os telefones sejam atualizados corretamente no banco de dados quando o usuário é 
	 *     atualizado.
	 *     
	 * Usuario userTemporario = usuarioRepository.findUserByLogin(usuario.getLogin());
	 *  -> usuarioRepository é um objeto que realiza operações de acesso a dados relacionadas aos usuários.
	 *  -> findUserByLogin(usuario.getLogin()) é um método que recebe o login do usuário como parâmetro e retorna o usuário correspondente no banco de dados com base nesse login.
	 *  -> O resultado é armazenado na variável userTemporario, que representa o usuário recuperado do banco de dados.
	 * 
	 * Dentro da condição if:
	 *  -> userTemporario.getSenha() retorna a senha armazenada no banco de dados para o usuário recuperado.
	 *  -> usuario.getSenha() retorna a senha fornecida pelo usuário.
	 *  -> A condição !userTemporario.getSenha().equals(usuario.getSenha()) verifica se a senha fornecida pelo usuário é 
	 *     diferente da senha armazenada no banco de dados. Se as senhas forem diferentes, o código dentro do bloco if será 
	 *     executado. 
	 * -> new BCryptPasswordEncoder().encode(usuario.getSenha()) cria um objeto BCryptPasswordEncoder e utiliza seu método 
	 *     encode para criptografar a senha fornecida pelo usuário. A senha criptografada é atribuída à propriedade senha do 
	 *     objeto usuario, substituindo a senha original fornecida. Dessa forma, a senha armazenada no banco de dados é 
	 *     atualizada com a senha criptografada fornecida pelo usuário.
	 *     
	 *     Em resumo, o código verifica se a senha fornecida pelo usuário é diferente da senha armazenada no banco de dados. 
	 *     Se forem diferentes, a senha fornecida é criptografada usando o algoritmo BCrypt e substitui a senha original 
	 *     armazenada no banco de dados. Isso pode ser útil para garantir que a senha seja atualizada corretamente no banco 
	 *     de dados sempre que um usuário alterar sua senha. 
	 * 
	 * -> usuarioRepository é um objeto que realiza operações de acesso a dados relacionados aos usuários.
	 * -> O método save(usuario) salva o objeto usuario no banco de dados. Neste caso, como estamos usando o método PUT 
	 *    (atualização), o usuário fornecido substituirá o usuário existente com o mesmo ID no banco de dados.
	 * -> O objeto usuarioAtualizar recebe o usuário atualizado que foi salvo no banco de dados.
	 * 
	 * -> new ResponseEntity<Usuario>(usuarioAtualizar, HttpStatus.OK) cria uma nova instância de ResponseEntity com o 
	 *    usuário atualizado e o status HTTP 200 (OK).
	 * -> HttpStatus.OK é o status HTTP 200 (OK), indicando que a solicitação de atualização foi bem-sucedida.
	 * 
	 * No geral, esse script recebe um objeto Usuario no corpo da requisição HTTP e realiza as seguintes ações:
	 *   1. Vincula o usuário a seus telefones.
	 *   2. Atualiza o usuário no banco de dados usando o método save.
	 *   3. Retorna uma resposta HTTP com o usuário atualizado e o status HTTP 200 (OK) indicando que a atualização foi 
	 *   bem-sucedida.
	 * */
	
	@PutMapping(value = "/", produces = "application/json")
	public ResponseEntity<Usuario> atualizar(@RequestBody Usuario usuario) {
		
		for (int pos = 0; pos < usuario.getTelefones().size(); pos++) {
			usuario.getTelefones().get(pos).setUsuario(usuario);
		}
		
		Usuario userTemporario = usuarioRepository.findUserByLogin(usuario.getLogin());
		
		if(!userTemporario.getSenha().equals(usuario.getSenha())) {
			String senhaCriptografado = new BCryptPasswordEncoder().encode(usuario.getSenha());
			usuario.setSenha(senhaCriptografado);
		}
		
		Usuario usuarioAtualizar = usuarioRepository.save(usuario);

		return new ResponseEntity<Usuario>(usuarioAtualizar, HttpStatus.OK);
	}

	
	/*
		// Para simular como se estivesse chamando um outro método
		@PutMapping(value = "/{iduser}", produces = "application/json")
		public ResponseEntity atualizarUsuId(@PathVariable Long iduser) {
			return new ResponseEntity("Atualização com sucesso", HttpStatus.OK); // http://localhost:8080/usuario/5/
		}
	*/

	
	/*
		  // Deletar dados do usuário - Tem que passar o id no json do Postman
		  @DeleteMapping(value = "/{id}", produces = "application/json") public String
		  deletarUsu(@PathVariable("id") Long id){
			  usuarioRepository.deleteById(id);
			  return "Deletado com Sucesso!"; 
		  }
	  
	 */

	/** No código abaixo vai ser deletado dados de usuário - http://localhost:8080/usuario/1
	 * 
	 * @DeleteMapping(value = "/{id}", produces = "application/json"):
	 *  -> A anotação @DeleteMapping indica que este método é acionado quando há uma requisição HTTP DELETE para a URL especificada.
	 *  -> value = "/{id}" define a URL do endpoint como /{id}, onde {id} é um espaço reservado para o ID do usuário a ser deletado.
	 *  -> produces = "application/json" indica que o endpoint produzirá uma resposta no formato JSON.
	 *  
	 *  -> @PathVariable(value = "id") Long id: a anotação @PathVariable indica que o valor do espaço reservado {id} na URL 
	 *     deve ser mapeado para a variável id do tipo Long.
	 *     
	 *  -> usuarioRepository é um objeto que realiza operações de acesso a dados relacionados aos usuários.
	 *  -> O método deleteById(id) exclui o registro do usuário com o ID fornecido do banco de dados.
	 *  
	 *  -> new ResponseEntity("Usuário Deletado!", HttpStatus.OK) cria uma nova instância de ResponseEntity com a mensagem 
	 *     "Usuário Deletado!" e o status HTTP 200 (OK).
	 *  -> HttpStatus.OK é o status HTTP 200 (OK), indicando que a solicitação de exclusão foi bem-sucedida.
	 *  
	 * No geral, esse script lida com uma requisição HTTP DELETE para a URL /usuario/{id}, onde {id} é o ID do usuário a 
	 * ser deletado. O usuário correspondente ao ID fornecido é excluído do banco de dados usando o método deleteById. Em 
	 * seguida, é retornada uma resposta HTTP com a mensagem "Usuário Deletado!" e o status HTTP 200 (OK), indicando que a 
	 * exclusão foi bem-sucedida.
	 *  */

	@DeleteMapping(value = "/{id}", produces = "application/json")
	public ResponseEntity<Usuario> deletarUsuPorId(@PathVariable(value = "id") Long id) {

		usuarioRepository.deleteById(id);
		return new ResponseEntity("Usuário Deletado!", HttpStatus.OK); 
	}

}
