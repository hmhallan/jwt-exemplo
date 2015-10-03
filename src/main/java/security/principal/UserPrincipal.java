package security.principal;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class UserPrincipal implements Principal {
	
	private Integer id;
	
    private String nome;
    private String email;
    
    private String login;
    private String senha;
    private Date dataCadastro;
    
    private List<UserRole> roles;
    
    @Override
    @JsonIgnore
	public String getName() {
		return nome;
	}
    
    public void addRole(UserRole role){
    	if (this.roles == null){
    		this.roles = new ArrayList<UserRole>();
    	}
    	this.roles.add(role);
    }

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public List<UserRole> getRoles() {
		return roles;
	}

	public void setRoles(List<UserRole> roles) {
		this.roles = roles;
	}
    
}
