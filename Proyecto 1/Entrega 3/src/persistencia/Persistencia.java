package persistencia;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;

public class Persistencia implements Serializable {
	
	public ArrayList<String> leer(String nombreArchivo) {
	    ArrayList<String> lineas = new ArrayList<String>();
	    String rutaArchivo = "docs/" + nombreArchivo + ".txt";
	    File archivo = new File(rutaArchivo);
	    try (BufferedReader lector = new BufferedReader(new FileReader(archivo))) {
	        String lineaActual;
	        while ((lineaActual = lector.readLine()) != null) {
	            lineas.add(lineaActual);
	        }
	    } catch (FileNotFoundException e) {
	        System.out.println("No se pudo encontrar el archivo: " + nombreArchivo);
	        e.printStackTrace();
	    } catch (IOException e) {
	        System.out.println("Error al leer el archivo: " + nombreArchivo);
	        e.printStackTrace();
	    }
	    return lineas;
	}

	
	
	public static void serializar(Object objeto, String rutaArchivo) throws IOException {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(rutaArchivo))) {
            outputStream.writeObject(objeto);
        }
    }
	
	public static Object deserializar(String rutaArchivo) throws IOException, ClassNotFoundException {
        Object objeto = null;
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(rutaArchivo))) {
            objeto = inputStream.readObject();
        }
        return objeto;
    }

}
