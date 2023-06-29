package projeto.api.rest.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ConstraintMode;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.UniqueConstraint;

import org.hibernate.validator.constraints.br.CPF;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Usuario implements UserDetails {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(unique = true)
	private String login;
	private String senha;
	private String nome;
	
	@CPF(message = "Cpf inválido")
	private String cpf;
	
	private String cep;
	private String logradouro;
	private String complemento;
	private String bairro;
	private String localidade;
	private String uf;
	
	// Declarando a Lista de Telefones
	// Na regra um usuario pode ter nenhum ou vários telefones - tem que mapear para o usuário
	// 'orphanRemoval = true' e 'cascade' -> para remover o usuário e pode ser em cascata
	@OneToMany(mappedBy = "usuario", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Telefone> telefones = new ArrayList<Telefone>();
	
	
	// Autorizações ou papéis do usuário
	@OneToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "usuarios_role", 
	uniqueConstraints = @UniqueConstraint(
			columnNames = {"usuario_id", "role_id"}, 
			name = "unique_role_user"),
	joinColumns = @JoinColumn(
			name = "usuario_id",
			referencedColumnName = "id",
			table = "usuario",
			unique = false,
			foreignKey = @ForeignKey(
					name = "usuario_fk", 
					value = ConstraintMode.CONSTRAINT
					)
			), 
	inverseJoinColumns = @JoinColumn(
			name="role_id", 
			referencedColumnName = "id", 
			table = "role",
			unique = false,
			updatable = false,
			foreignKey = @ForeignKey(
					name = "role_fk",
					value = ConstraintMode.CONSTRAINT
					)
			)
	)
	private List<Role> roles;
	
	private String token = "";

	
	// Getters e setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getCpf() {
		return cpf;
	}
	
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getLocalidade() {
		return localidade;
	}

	public void setLocalidade(String localidade) {
		this.localidade = localidade;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	public List<Telefone> getTelefones() {
		return telefones;
	}

	public void setTelefones(List<Telefone> telefones) {
		this.telefones = telefones;
	}
	
	public void setToken(String token) {
		this.token = token;
	}
	
	public String getToken() {
		return token;
	}

	
	// HashCode e Equals
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		return Objects.equals(id, other.id);
	}

	// Retorna uma coleção de objetos `GrantedAuthority`, que representam as autorizações ou papéis do usuário.
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		return roles;
	}

	// Retorna a senha do usuário.
	@JsonIgnore
	@Override
	public String getPassword() {
		
		return this.senha;
	}

	// Retorna o nome de usuário do usuário
	@JsonIgnore
	@Override
	public String getUsername() {
		
		return this.login;
	}

	// Verifica se a conta do usuário não está expirada.
	@JsonIgnore
	@Override
	public boolean isAccountNonExpired() {
		
		return true;
	}

	// Verifica se a conta do usuário não está bloqueada.
	@JsonIgnore
	@Override
	public boolean isAccountNonLocked() {
		
		return true;
	}

	// Verifica se as credenciais do usuário não estão expiradas.
	@JsonIgnore
	@Override
	public boolean isCredentialsNonExpired() {
		
		return true;
	}

	// Verifica se a conta do usuário está habilitada
	@JsonIgnore
	@Override
	public boolean isEnabled() {
		
		return true;
	}
	
}
