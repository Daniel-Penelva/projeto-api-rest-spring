package projeto.api.rest.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name = "role")
@SequenceGenerator(name = "seq_role", sequenceName = "seq_role", allocationSize = 1, initialValue = 1)
public class Role implements GrantedAuthority{
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_role")
	private Long id;
	
	// Define o seu papel, por exemplo, ROLE_SECRETARIA
	private String nomeRole;

	
	// Retorna o nome do papel, ou seja, o acesso ou a autorização (por exemplo, GERENTE_ROLE)
	@Override
	public String getAuthority() {
		
		// Pegar o contexto dessa classe em relação ao nomeRole.
		return this.nomeRole;
	}

	
    // Métodos Setters e Getters
	
	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getNomeRole() {
		return nomeRole;
	}


	public void setNomeRole(String nomeRole) {
		this.nomeRole = nomeRole;
	}
	
}
