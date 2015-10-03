package security;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import security.dto.TokenDTO;
import security.principal.UserPrincipal;
import security.principal.UserRole;

/**
 * Classe wrapper da request, para fazer override dos metodos getUserPrincipal e isUserInRole
 * 
 * @author hallan.medeiros
 *
 */
public class TokenRequestWrapper extends HttpServletRequestWrapper  {

	private UserPrincipal principal;
	
	public TokenRequestWrapper(HttpServletRequest request, TokenDTO dto) {
	    super(request);
	    this.principal = dto.getSub();
    }
	
	public Principal getUserPrincipal() {
        return this.principal;
    }
	
	public String getRemoteUser() {
        return this.principal != null ? this.principal.getName() : null;
    }
     
    public boolean isUserInRole(final String role) {
        if (role == null || role.trim().length() == 0) {
            return false;
        }
 
        if (this.principal == null) {
            return false;
        }
        if (this.principal.getRoles() != null){
            for (UserRole userRole: this.principal.getRoles()){
                if (rolesEqual(role, userRole)){
                    return true;
                }
            }
        }
        return false;
    }
     
    private boolean rolesEqual(final String given, final UserRole candidate) {
        return given != null ? given.equalsIgnoreCase(candidate.getName()) : false;
    }
	
}
