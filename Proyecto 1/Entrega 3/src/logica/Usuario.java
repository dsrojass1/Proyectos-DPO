package logica;

import java.io.Serializable;

public class Usuario implements Serializable {
	private String login;
	private String constrasena;
	private String rol;
	
	public Usuario(String login, String constrasena, String rol) {
		this.login = login;
		this.constrasena = constrasena;
		this.rol = rol;
	}

	public String getLogin() {
		return login;
	}

	public String getConstrasena() {
		return constrasena;
	}

	public String getRol() {
		return rol;
	}
		
	
}
