package ae4;

import java.io.Serializable;
@SuppressWarnings("serial")
public class Contrasenya implements Serializable {
	String contrasenyaPlana;
	String contrasenyaEncriptada;

	public String getContrasenyaPlana() {
		return contrasenyaPlana;
	}

	public void setContrasenyaPlana(String contrasenyaPlana) {
		this.contrasenyaPlana = contrasenyaPlana;
	}

	public String getContrasenyaEncriptada() {
		return contrasenyaEncriptada;
	}

	public void setContrasenyaEncriptada(String contrasenyaEncriptada) {
		this.contrasenyaEncriptada = contrasenyaEncriptada;
	}

	public Contrasenya(String contrasenya1, String contrasenya2) {
		this.contrasenyaPlana = contrasenya1;
		this.contrasenyaEncriptada = contrasenya2;

	}
}
