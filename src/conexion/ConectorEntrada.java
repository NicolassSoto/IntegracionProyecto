package conexion;

import java.util.logging.Level;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.StringReader;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;

public class ConectorEntrada extends Conector {

    public ConectorEntrada(Puerto p, String f) {
        super(p, f);
    }

    public void readFolder() {
    	
        File carpeta = new File(getFichero());
        
       
        if (carpeta.exists() && carpeta.isDirectory()) {
            // Obtener la lista de archivos en la carpeta
            File[] archivos = carpeta.listFiles((dir, name) -> name.endsWith(".xml"));
            
            if (archivos != null) {
                for (File archivo : archivos) {
                    try {
                        // Procesar cada archivo XML
                        Document document = convertirXMLaDocumento(archivo);
                        // Enviar el documento por el puerto
                        getPuerto().escribirMensaje(document);
                    } catch (Exception e) {
                        // Manejar excepciones, como errores al procesar el archivo XML
                        System.err.println("Error al procesar el archivo: " + archivo.getName());
                        e.printStackTrace();
                    }
                }
            } else {
                System.err.println("No se pudo leer los archivos en la carpeta.");
            }
        } else {
            System.err.println("La carpeta especificada no existe o no es un directorio.");
        }
    }
    
    private Document convertirXMLaDocumento(File archivo) throws Exception {
        // Crear un DocumentBuilderFactory y un DocumentBuilder
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        
        // Leer el archivo XML y convertirlo a un objeto Document
        return builder.parse(archivo);
    }
}
