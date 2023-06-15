package projeto.api.rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import projeto.api.rest.model.Usuario;
import projeto.api.rest.repository.UsuarioRepository;


@Service
public class ImplementacaoUserDetailsService implements UserDetailsService{
	
	@Autowired
	UsuarioRepository usuarioRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		// Consultar no banco de dados o usuário
		Usuario usuario = usuarioRepository.findUserByLogin(username);
		
		// Validação para saber se usuário existe ou não
		if(usuario == null) {
			throw new UsernameNotFoundException("Usuário não encontrado!");
		}
		
		return new User(usuario.getLogin(), usuario.getSenha(), usuario.getAuthorities());
	}

}
