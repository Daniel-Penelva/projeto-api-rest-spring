package projeto.api.rest.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import projeto.api.rest.model.Usuario;

@Repository
public interface UsuarioRepository extends CrudRepository<Usuario, Long>{
	
	/* Vai ser passado o parametro representado pelo (?) e a posição do parâmetro (1). E esse parametro (?) é ligado
	 * ao parâmetro login do método findUserByLogin  */
	
	@Query("select u from Usuario u where u.login = ?1")
	Usuario findUserByLogin(String login);

}
