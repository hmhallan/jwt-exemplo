package security.util;

import java.io.IOException;
import java.io.StringWriter;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

import javax.crypto.spec.SecretKeySpec;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import entity.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import security.dto.TokenDTO;
import security.exception.TokenException;

public class TokenUtil {
	
	private static final String SECRET = "chave.privada";
	
	public static TokenDTO getFromToken(String token) throws TokenException{
		try {
			byte[] encodedKey = Base64.getEncoder().encode(SECRET.getBytes());
			Key key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "DES");
			
			//faz o parse do token e monta o body
			Claims claim = Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
			
			//monta o dto com data de geracao e expiracao
			TokenDTO dto = new TokenDTO();
			dto.setExp(claim.getExpiration());
			dto.setGen( new java.util.Date((Long)claim.get("gen")));
			
			//pega o usuario do subject 
			String subject = claim.getSubject();
			ObjectMapper mapper = new ObjectMapper();
			dto.setSub(mapper.readValue(subject, Usuario.class));
			
			return dto;
			
		} 
		catch(MalformedJwtException ex){
			throw new TokenException();
		}
		catch(ExpiredJwtException ex){
			throw new TokenException("Token expirado (" + ex.getMessage() + ")" );
		}
		catch(SignatureException ex){
			throw new TokenException();
		}
		catch(IOException ex){
			ex.printStackTrace();
			throw new TokenException("Token inválido: " + ex.getMessage());
		}
		
	}
	
	public static String generateToken(Usuario u, Date dataExpiracao){
		String s = null;
		try {
			byte[] encodedKey = Base64.getEncoder().encode(SECRET.getBytes());
			Key key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "DES");
			
			
			//to json string
			StringWriter writer = new StringWriter();
			ObjectMapper mapper = new ObjectMapper();
			mapper.writeValue(writer, u);
			
			//monta o token passando o subject, data de expiracao e geracao
			s = Jwts.builder()
							.signWith(SignatureAlgorithm.HS512, key)
							.claim("sub", writer.toString())
							.claim("gen", new Date())
							.setExpiration(dataExpiracao)
							.compact();
			
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return s;
	}
	

}
