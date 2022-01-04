package ae4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.net.Socket;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Peticion implements Runnable {

	BufferedReader br;
	PrintWriter pw;
	Socket socket;

	/*
	 * Metodo: Peticion Descripcion: Constructor que rep el socket. Parametre
	 * d'entrada: String socket Parametre d'eixida: no
	 */
	public Peticion(Socket socket) {
		this.socket = socket;
	}

	/*
	 * Metodo: ASCII Descripcion: Encripta la contrasenya en codigo ASCII Parametre
	 * d'entrada: String contrasenya Parametre d'eixida: pwdGenerada
	 */
	public String ASCII(String contrasenya) {
		char pwd[] = contrasenya.toCharArray();
		String pwdGenerada = "";
		for (int i = 0; i < pwd.length; i++) {
			if (pwd[i] <= 31 || pwd[i] == 127) {
				pwdGenerada += '*';
			} else {
				pwdGenerada += (char) (pwd[i] + 1);
			}
		}
		return pwdGenerada;
	}

	/*
	 * Metodo: MD5 Descripcion: Encripta la contrasenya en codigo MD5 Parametre
	 * d'entrada: String contrasenya Parametre d'eixida: hashtext
	 */
	public static String MD5(String contrasenya) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] messageDigest = md.digest(contrasenya.getBytes());
			BigInteger number = new BigInteger(1, messageDigest);
			String hashtext = number.toString(16);

			while (hashtext.length() < 32) {
				hashtext = "0" + hashtext;
			}
			return hashtext;
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}

	/*
	 * Metodo: run Descripcion: executa el servidor Parametre d'entrada: no
	 * Parametre d'eixida: no
	 */
	public void run() {
		try {

			ObjectOutput outObjeto = new ObjectOutputStream(socket.getOutputStream());
			Contrasenya contrasenya = new Contrasenya("", "");
			outObjeto.writeObject(contrasenya);

			ObjectInputStream inObjeto = new ObjectInputStream(socket.getInputStream());
			Contrasenya pwdRecibida = (Contrasenya) inObjeto.readObject();
			System.err.println("SERVIDOR >>> Contrasenya recibida del cliente: " + pwdRecibida.getContrasenyaPlana());

			System.err.println("SERVIDOR >> Seleccione el metodo de encriptacion que desea aplicar: ");
			InputStream is = socket.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			br = new BufferedReader(isr);
			OutputStream os = socket.getOutputStream();

			pw = new PrintWriter(os);
			int opcion = Integer.parseInt(br.readLine());
			String tipo = null;
			if (opcion == 1) {
				tipo = "ASCII";
			}
			if (opcion == 2) {
				tipo = "MD5";
			}
			System.err.println("SERVIDOR >>> La opcion recibida ha sido: " + tipo);
			String contrasenya1 = pwdRecibida.getContrasenyaPlana();
			String contrasenyaEncriptada = "";
			System.err.println("SERVIDOR >>> Encriptando...");
			if (opcion == 1) {
				contrasenyaEncriptada = ASCII(contrasenya1);
			}
			if (opcion == 2) {
				contrasenyaEncriptada = MD5(contrasenya1);
			}

			System.err.println("SERVIDOR >>> Su contrasenya encriptada es: " + contrasenyaEncriptada);
			pw.write(contrasenyaEncriptada.toString() + "\n");
			pwdRecibida = new Contrasenya(contrasenya1, contrasenyaEncriptada);
			System.err.println("SERVIDOR >>> Enviado a cliente ");
			outObjeto.writeObject(pwdRecibida);

			pw.flush();
			outObjeto.close();
			inObjeto.close();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("SERVIDOR >>> ERROR :(");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
