package security.dto;

import java.util.Date;

import security.principal.UserPrincipal;

public class TokenDTO {
	
	//dados do usuario
	private UserPrincipal sub;
	
	//data de expiracao
	private Date exp;
	
	//data de geracao
	private Date gen;

	@Override
	public String toString() {
		return "TokenValueDTO [sub=" + sub + ", exp=" + exp + ", gen=" + gen
				+ "]";
	}

	public UserPrincipal getSub() {
		return sub;
	}

	public void setSub(UserPrincipal sub) {
		this.sub = sub;
	}

	public Date getExp() {
		return exp;
	}

	public void setExp(Date exp) {
		this.exp = exp;
	}

	public Date getGen() {
		return gen;
	}

	public void setGen(Date gen) {
		this.gen = gen;
	}

}
