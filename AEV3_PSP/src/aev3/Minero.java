package aev3;

public class Minero implements Runnable {
	int bolsa;
	int tiempoExtraccion = 100;
	Mina mina;
	static int totalExtraccion;
	public Minero(Mina min) {

		this.bolsa = 0;
		this.mina = min;
	}
	//Metodo para extraer los recursos de la mina en el cual comprobaremos siempre que el stock de la mina sea mayor que 0 y ademas un condicional que nos permita
	// controlar cuantos recursos son substraidos en la mina, ya que de otra forma al tratarse de numeros aleatorios podria darnos resultados que no queremos
	private void extraerRecurso() throws InterruptedException {
		

		while (mina.stock > 0) {
			double extraer = Math.round(Math.random() * 9 + 1);
			if (mina.stock <= extraer) {
				System.out.println("El minero ha agotado la mina y ha conseguido "+mina.stock+" de oro");
				synchronized(mina){
				extraer = mina.stock;
				mina.stock = 0;
				}
				bolsa += extraer;
				totalExtraccion +=extraer;
				
			} else {
				synchronized(mina){
				mina.stock -= extraer;
				System.out.println("El minero ha extraido " + extraer +" piezas de oro");
				}
				bolsa += extraer;
				totalExtraccion +=extraer;
			}
			Thread.sleep(tiempoExtraccion);
		}
		System.out.println("El minero ha conseguido " + bolsa + " oro");

	}

	@Override
	public void run() {
		//En este apartado ponemos que queremos que se ejecute en nuestra clase App 
		try {
			extraerRecurso();
			Thread.sleep(10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
