package aev3;

public class App {

	public static void main(String[] args) {
		Mina mina = new Mina();
		int stock = mina.stock;
		//instanciamos un hilo que utilizaremos mas tarde para crear nuevos hilos dentro del bucle "for", asociando a cada uno de ellos el mismo objeto mina,
		// de esta forma al compartir el mismo stock todos los hilos estan conectados entre si
		Thread hilitoMinero;
		for (int i = 0; i < 10; i++) {
			Minero minero = new Minero(mina);
			hilitoMinero = new Thread(minero);
			hilitoMinero.start();
		}
		//hacemos una pausa para mostrar el ultimo mensaje antes de finalizar el programa
		try {
			Thread.sleep(1000);
		}catch(InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println("El total extraido "+Minero.totalExtraccion+ " y en la mina habia "+ stock);
	}
}