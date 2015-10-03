package dao;

import java.util.Date;

import security.principal.UserPrincipal;
import security.principal.UserRole;

public class UsuarioDAO {
	
	public UserPrincipal doLogin(String username, String password){
		UserPrincipal u = new UserPrincipal();
		u.setLogin(username);
		u.setSenha(password);
		u.setDataCadastro(new Date());
		u.setId(1);
		u.addRole(new UserRole("convidado"));
		return u;
	}

}
