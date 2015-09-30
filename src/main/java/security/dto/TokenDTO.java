package security.dto;

import java.util.Date;

import entity.Usuario;

public class TokenDTO {
	
	//dados do usuario
	private Usuario sub;
	
	//data de expiracao
	private Date exp;
	
	//data de geracao
	private Date gen;

	@Override
	public String toString() {
		return "TokenValueDTO [sub=" + sub + ", exp=" + exp + ", gen=" + gen
				+ "]";
	}

	public Usuario getSub() {
		return sub;
	}

	public void setSub(Usuario sub) {
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
