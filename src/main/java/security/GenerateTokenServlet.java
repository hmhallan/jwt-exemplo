package security;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;

import com.fasterxml.jackson.databind.ObjectMapper;

import dao.UsuarioDAO;
import entity.Usuario;
import security.dto.TokenDTO;
import security.util.TokenUtil;

/**
 * 
 * @author hallan.medeiros
 *
 */
@WebServlet(name="GenerateTokenServlet", urlPatterns="/auth/login")
public class GenerateTokenServlet extends HttpServlet {
	
	private static final long serialVersionUID = 874319746865743274L;
	
	@Inject
	UsuarioDAO usuarioDao;
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String json = this.getJsonFromRequest(request);
		
		//parse da request
		JsonReader reader = Json.createReader(new StringReader(json));
        JsonObject jsonObject = reader.readObject();
        reader.close();
		
        //valida usuario no banco
		String username = jsonObject.getString("username");
		String password = jsonObject.getString("password");
		
		Usuario u = usuarioDao.doLogin(username, password);
		
		if (u != null){
			//conteudo da resposta para json
			response.setContentType(MediaType.APPLICATION_JSON);
			
			//expiracao do token (7 dias)
			GregorianCalendar expiracao = new GregorianCalendar();
			expiracao.add(GregorianCalendar.DAY_OF_MONTH, 7);
			
			//gera o token e o dto de resposta
			TokenDTO dto = new TokenDTO();
			dto.setSub(u);
			dto.setExp(expiracao.getTime());
			dto.setGen(new Date());
			
			String token = TokenUtil.generateToken(u, expiracao.getTime());
			
			//adiciona o token no header
			response.addHeader("Authorization", token);
			
			//adiciona o dto no body
			ObjectMapper mapper = new ObjectMapper();
			mapper.writeValue(response.getOutputStream(), dto);
		}
		else{
			response.setStatus(Status.UNAUTHORIZED.getStatusCode());
		}
	}
	
	
	public String getJsonFromRequest(HttpServletRequest request){
		StringBuilder jb = new StringBuilder();
		String line = null;
		try {
			BufferedReader reader = request.getReader();
			while ((line = reader.readLine()) != null){
				jb.append(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jb.toString();
	}
	
}
