package security.principal;

import java.security.Principal;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class UserRole implements Principal {
	
	private String nome;
	
	public UserRole() {
		super();
	}
	public UserRole(String nome) {
		super();
		this.nome = nome;
	}
	
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}

	@Override
	@JsonIgnore
	public String getName() {
		return nome;
	}

}
