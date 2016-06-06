/* www.sberlati.com */
package pinglogger.ping;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;

import pinglogger.io.IO;

/**
 * La clase Pinger se encarga de manejar los threads que hacen
 * pings constantes a google.com y comunicar el resultado (enviando
 * mensajes a la clase IO).
 * 
 * @author Sebastián Berlati
 *
 */
public class Pinger {
	//La instancia a la clase IO que necesito para enviar mensajes y escribir el log
	private IO io;
	
	/**
	 * Constructor de la clase sin URL donde almacenar los logs
	 */
	public Pinger() {
		this.io = new IO();
		this.start();
	}
	
	/**
	 * Constructor de la clase CON URL de logs
	 */
	public Pinger(String logsUrl) {
		this.io = new IO(logsUrl);
		this.start();
	}
	
	/**
	 * El método start se llama desde el constructor, y es el que básicamente
	 * hace todo. Crea un thread que se encargue de hacer ping a www.google.com
	 * y, si hay algún cambio, lo envía como mensaje a IO para que lo grabe en
	 * el log.
	 */
	private void start() {
		Thread t = new Thread(new Runnable() {
			public void run() {
				while(true) {
					if(!someInternet()) {
						io.escribirLinea("Conexion con Internet perdida...");
						try {
							while(!someInternet()) {
								Thread.sleep(1000);
							}
							io.escribirLinea("Conexion restablecida con éxito!");
						}catch(InterruptedException e) {
							e.printStackTrace();
						}
					}else{
						try {
							Thread.sleep(5000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}	
					}
				}
			}
		});
		t.start();
	}
	
	/**
	 * Hace el ping a http://www.google.com. Si no aparece ninguna excepción
	 * (Host desconocido, por ejemplo) devuelve TRUE. Caso contrario va a 
	 * devolver FALSE y la excepción va a ser salvada. El método start es
	 * el que va a hacer lo que corresponda dependiendo del retorno de éste método. 
	 * 
	 * @return	boolean	TRUE si hay una respuesta de la URL.
	 */
	private boolean someInternet() {
		try {
			URL url = new URL("http://www.google.com");
			HttpURLConnection urlConnect = (HttpURLConnection)url.openConnection();
			
			@SuppressWarnings("unused")
			Object objData = urlConnect.getContent();
		} catch(UnknownHostException e) {
			return false;
		}catch (MalformedURLException e) {
			return false;
		} catch (IOException e) {
			return false;
		}
		return true;
	}
}
