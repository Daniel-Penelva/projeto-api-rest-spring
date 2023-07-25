package projeto.api.rest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import projeto.api.rest.model.Usuario;

@Repository
public interface UsuarioRepository extends CrudRepository<Usuario, Long>{
	
	/* Vai ser passado o parametro representado pelo (?) e a posição do parâmetro (1). E esse parametro (?) é ligado
	 * ao parâmetro login do método findUserByLogin  */
	
	@Query("select u from Usuario u where u.login = ?1")
	Usuario findUserByLogin(String login);
	
	@Transactional
	@Modifying
	@Query(nativeQuery = true, value = "update usuario set token = ?1 where login = ?2")
	void atualizarTokenUser( String token, String login);
	
	// Criando a consulta de usuário por nome
	@Query("select u from Usuario u where u.nome like %?1%")
	List<Usuario> findUserByNome(String login);

}
