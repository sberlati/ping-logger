import pinglogger.ping.Pinger;

public class Main {
	/**
	 * Punto de entrada de la aplicación. Recibe como argumentos
	 * la URL de la carpeta donde voy a almacenar los logs. En caso
	 * de no recibirlo lo voy a guardar en la carpeta donde se ejecuta
	 * la aplicación.
	 * 
	 */
	public static void main(String[] args) {
		try {
			System.out.println("       _                   _                             ");
			System.out.println(" _ __ (_)_ __   __ _      | | ___   __ _  __ _  ___ _ __ ");
			System.out.println("| '_ \\| | '_ \\ / _` |_____| |/ _ \\ / _` |/ _` |/ _ \\ '__|");
			System.out.println("| |_) | | | | | (_| |_____| | (_) | (_| | (_| |  __/ |");
			System.out.println("| .__/|_|_| |_|\\__, |     |_|\\___/ \\__, |\\__, |\\___|_|");
			System.out.println("|_|            |___/               |___/ |___/           \n");
			System.out.println("\t\t\tgithub.com/sberlati/ping-logger\n\n\n");
			switch(args.length) {
				case 0:
					System.out.println("Se almacenaran los logs en la carpeta donde se ejecuta ping-logger.");
					System.out.println("Para ejecutar ping-logger con una URL donde almacenar logs, \nescribe java Main [url]");
					new Pinger();
				break;
				
				case 1:
					System.out.format("Los logs se almacenan en %s\n", args[0]);
					new Pinger(args[0]);
					break;
			}
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("Ejecución interrumpida por excepción.");
			System.exit(1);
		}
	}

}
