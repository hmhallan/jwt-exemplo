package security;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response.Status;

import security.dto.TokenDTO;
import security.exception.TokenException;
import security.util.TokenUtil;

@WebFilter(filterName="ValidateTokenFilter", urlPatterns="/rest/*")
public class ValidateTokenFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		
		String token = request.getHeader("Authorization");
		
		try {
			//valida o token da request
			TokenDTO dto = TokenUtil.getFromToken(token);
			
			//valido, encaminha a request
			TokenRequestWrapper wrapper = new TokenRequestWrapper(request, dto);
			chain.doFilter(wrapper, response);
			
		} catch (TokenException e) {
			response.addHeader("x-message", e.getMessage());
			response.setStatus(Status.UNAUTHORIZED.getStatusCode());
		}	catch (IllegalArgumentException e) {
			response.addHeader("x-message", "Token de autorização não encontrado");
			response.setStatus(Status.UNAUTHORIZED.getStatusCode());
		}
		
	}

	@Override
	public void destroy() {}

}
