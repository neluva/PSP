package Ejs;

public class Probabilidad {
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		double posicionNEO = Double.parseDouble(args[0]);
		double velocidadNEO = Double.parseDouble(args[1]);

		double posicionTierra = 1;
		double velocidadTierra = 100;
		for (int i = 0; i < (50 * 365 * 24 * 60 * 60); i++) {
			posicionNEO = posicionNEO + velocidadNEO * i;
			posicionTierra = posicionTierra + velocidadTierra * i;
		}
		double resultado = 100 * Math.random()
				* Math.pow(((posicionNEO - posicionTierra) / (posicionNEO + posicionTierra)), 2);
		System.out.println(resultado);
		if(resultado>=10) {
			 System.err.println("Vais a morir todos");
		}
		
	}

}
