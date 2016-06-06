/* www.sberlati.com */
package pinglogger.io;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * La clase IO se encarga de todo lo que es input/output. Con esto
 * me refiero a que se encarga de manipular los archivos de texto con
 * los logs, y al mismo tiempo de la salida de la consola.
 * 
 * @author Sebastián Berlati
 *
 */
public class IO {
	//Directorio donde se van a guardar los logs
	private String logDirectory;
	
	//Formato de la fecha. Se aplica al darle nombre al txt
	private static final String dateDisplay = "dd-MM-yyyy";
	
	//Formato de la hora. Se aplica al escribir una línea de texto en el log
	private static final String timeDisplay = "HH:mm:ss";
	
	//Esto es para escribir en la consola lo que se escriba en el log
	private static final boolean writeOutput = true;
	
	//Clases que son necesarias para el manejo de fechas y archivos
	private Calendar calendar;
	private BufferedWriter bwriter;
	private FileWriter writer;
	private DateFormat dateFormat;
	private DateFormat timeFormat;
	
	/**
	 * Constructor de la clase. Instancia un dateFormat y
	 * un timeFormat para usar más adelante. Revisa que exista
	 * el directorio de la constante logDirectory, y si no existe
	 * lo crea. Después, se fija si existe un archivo .txt cuyo
	 * nombre sea la fecha actual. Si no existe lo crea, caso contrario
	 * simplemente lo abre.
	 * 
	 * Este constructor hace la carpeta de logs en la carpeta donde se 
	 * ejecuta la aplicación.
	 */
	public IO() {
		//Instanciando los date y time format
		this.dateFormat = new SimpleDateFormat(dateDisplay);
		this.timeFormat = new SimpleDateFormat(timeDisplay);
		this.calendar = Calendar.getInstance();
		this.logDirectory = System.getProperty("user.dir") + "/logs/";
		//Fijándome si existe el directorio, y sino lo creo
		File dir = new File(logDirectory);
		if(!dir.exists())
			dir.mkdir();
		//Me fijo si existe el archivo con la fecha actual y sino lo creo
		File archivo = new File(logDirectory + this.dateFormat.format(this.calendar.getTime()) + ".txt");
		if(!archivo.exists())
			try {
				archivo.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
	
	/**
	 * Es el mismo constructor pero recibe una URL donde se van a almacenar los
	 * logs. En caso de no existir, voy a hacer lo mismo que el otro constructor.
	 * 
	 * @param logsURL	La dirección en donde se van a guardar los logs de conexión
	 */
	public IO(String logsURL) {
		//Instanciando los date y time format
		this.dateFormat = new SimpleDateFormat(dateDisplay);
		this.timeFormat = new SimpleDateFormat(timeDisplay);
		this.calendar = Calendar.getInstance();
		this.logDirectory = logsURL;
		//Fijándome si existe el directorio, y sino voy al otro constructor
		File dir = new File(logDirectory);
		if(!dir.exists()) {
			System.out.println("El directorio no existe. Usando el directorio de ejecución.");
			this.logDirectory = System.getProperty("user.dir") + "/logs/";
			dir = new File(logDirectory);
			if(!dir.exists())
				dir.mkdir();
		}
		//Me fijo si existe el archivo con la fecha actual y sino lo creo
		File archivo = new File(logDirectory + this.dateFormat.format(this.calendar.getTime()) + ".txt");
		if(!archivo.exists())
			try {
				archivo.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
	
	/**
	 * Escribe una línea de texto al final del archivo del log
	 * con el mensaje que se le haya pasado por el argumento. Anteponiendo
	 * el mensaje que se vaya a poner, se escribe la hora actual.
	 * 
	 * @param	mensaje	Una cadena de texto con lo que se quiera agregar
	 */
	public void escribirLinea(String mensaje) {
		try {
			this.calendar.setTime(new Date());
			this.writer = new FileWriter(logDirectory + this.dateFormat.format(this.calendar.getTime()) + ".txt", true);
			this.bwriter = new BufferedWriter(this.writer);
			//Escribo la línea al final del archivo
			this.bwriter.write("[" + this.timeFormat.format(this.calendar.getTime()) + "] " + mensaje);
			//Si es TRUE, en la consola voy a escribir lo mismo
			if(writeOutput)
				System.out.println("[" + this.timeFormat.format(this.calendar.getTime()) + "] " + mensaje);
			
			this.bwriter.newLine();
			this.bwriter.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				this.bwriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
