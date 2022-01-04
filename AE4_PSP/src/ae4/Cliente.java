package ae4;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Cliente {

	/*
	 * Metodo: main Descripcion: inicia el client Parametre d'entrada: String[] args
	 * Parametre d'eixida: no
	 */
	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
		// TODO Auto-generated method stub
		Scanner teclat = new Scanner(System.in);

		Thread.sleep(5000);
		System.out.println("CLIENTE >> Arranca cliente");
		System.out.println("CLIENTE >> Conexion al servidor");
		String host = "localhost";
		int puerto = 1234;

		Socket cliente = new Socket(host, puerto);

		System.out.print("CLIENTE >>> Recibo contrasenya. ");
		ObjectInputStream inObjeto = new ObjectInputStream(cliente.getInputStream());
		Contrasenya pwd = (Contrasenya) inObjeto.readObject();

		System.out.print("SERVIDOR >>> Introduce una contrasenya: ");
		pwd.setContrasenyaPlana(teclat.nextLine());
		ObjectOutputStream outObjeto = new ObjectOutputStream(cliente.getOutputStream());
		outObjeto.writeObject(pwd);

		System.out.println("CLIENTE >>> Enviando contrasenya al servidor: " + pwd.getContrasenyaPlana());
		PrintWriter pw = new PrintWriter(cliente.getOutputStream());
		System.out.println(
				"\nSERVIDOR >>> METODOS DE ENCRIPTACION:\n" + "\t1: Encriptacion ASCII\n" + "\t2: Encriptacion MD5 \n");
		System.out.print("Cual es el metodo de encriptacion que quieras usar?: ");
		String tipoEncriptacion = teclat.nextLine();
		switch (tipoEncriptacion) {
		case "1":
			System.out.println("CLIENTE >>> Metodo de encriptacion seleccionado: ASCII");
			break;
		case "2":
			System.out.println("CLIENTE >>> Metodo de encriptacion seleccionado: MD5");
			break;
		default:
			System.err
					.println("SERVIDOR >>> ERROR! Parece ser que no has indicado un metodo de encriptacion valido :(");
		} // end-switch
		pw.print(tipoEncriptacion + "\n");
		pw.flush();

		pwd = (Contrasenya) inObjeto.readObject();
		System.out
				.println("CLIENTE >>> Contrasenya encriptada recibida del servidor: " + pwd.getContrasenyaEncriptada());

		teclat.close();
		inObjeto.close();
		outObjeto.close();
		cliente.close();
	}

}
