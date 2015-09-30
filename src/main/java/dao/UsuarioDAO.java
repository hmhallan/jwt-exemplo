package dao;

import entity.Usuario;

public class UsuarioDAO {
	
	public Usuario doLogin(String username, String password){
		Usuario u = new Usuario();
		u.setLogin(username);
		u.setSenha(password);
		u.setId(1);
		return u;
	}

}
