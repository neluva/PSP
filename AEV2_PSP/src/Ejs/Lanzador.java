
package Ejs;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Lanzador {

	public void lanzarProbabilidad(String nombre, Double posicion, Double velocidad) {
		String clase = "Ejs.Probabilidad";
		try {

			String javaHome = System.getProperty("java.home");
			String javaBin = javaHome + File.separator + "bin" + File.separator + "java";
			String classpath = System.getProperty("java.class.path");

			String className = clase;

			List<String> command = new ArrayList<>();
			command.add(javaBin);
			command.add("-cp");
			command.add(classpath);
			command.add(className);
			command.add(posicion.toString());
			command.add(velocidad.toString());

			ProcessBuilder builder = new ProcessBuilder(command);
			Process process = builder.inheritIO().start();

			process.waitFor();
			if (process.exitValue() != 0) {
				System.out.println(process.exitValue());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws IOException {

		Lanzador l = new Lanzador();
		FileReader fr = new FileReader("NEOs.txt");
		BufferedReader bf = new BufferedReader(fr);
		String[] datos;
		String nom;
		Double pos;
		Double vel;
		String linea = "";
		while (linea != null) {
			linea = bf.readLine();
			if (linea != null) {
				datos = linea.split(",");
				nom = datos[0];
				pos = Double.parseDouble(datos[1]);
				vel = Double.parseDouble(datos[2]);
				l.lanzarProbabilidad(nom, pos, vel);

				File cosasQueEstallan = new File(nom + ".txt");
				try {
					cosasQueEstallan.createNewFile();
					FileWriter escribir = new FileWriter(cosasQueEstallan);
					PrintWriter pw = null;

					try {
						pw = new PrintWriter(escribir);
						pw.println(linea);
					} catch (Exception e) {
						e.printStackTrace();
					} finally {
						escribir.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}