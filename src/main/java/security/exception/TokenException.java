package security.exception;

public class TokenException extends Exception {

	private static final long serialVersionUID = -3464638125314076562L;
	
	public TokenException(){
		this("Token inválido");
	}
	
	public TokenException(String message){
		super(message);
	}

}
