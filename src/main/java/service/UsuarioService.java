package service;

import java.security.Principal;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

@Path("/usuario")
public class UsuarioService {
	
	@Context
	SecurityContext context;
	
	@GET
	@Path("/dados")
	@Produces(MediaType.APPLICATION_JSON)
	@RolesAllowed("convidado")
	public Principal getDadosUsuario(){
		return context.getUserPrincipal();
	}
	
	@GET
	@Path("/protegido")
	@Produces(MediaType.TEXT_PLAIN)
	@RolesAllowed("admin")
	public String getMetodoProtegido(){
		return "Metodo protegido com role 'admin'";
	}

}
