package conexion;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ConectorSalida extends Conector {

    public ConectorSalida(Puerto p, String f) {
        super(p, f);
    }

    public void write() {
    	 try {
             // Generar un nombre único para el archivo (puedes usar un timestamp, un UUID, etc.)
             String fileName = "document_" + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + ".xml";
             File folder = new File(getFichero());

             // Verificar si el folder existe, si no, crear el folder
             if (!folder.exists()) {
                 folder.mkdirs();  // Crea el directorio si no existe
             }

             // Crear el archivo XML en el folder especificado
             File file = new File(folder, fileName);

             // Crear un Transformer para convertir el Document a un archivo XML
             TransformerFactory transformerFactory = TransformerFactory.newInstance();
             Transformer transformer = transformerFactory.newTransformer();

             // Configurar la salida del archivo para ser legible
             transformer.setOutputProperty(OutputKeys.INDENT, "yes");
             transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

             // Escribir el Document en el archivo
             StreamResult result = new StreamResult(file);
             transformer.transform(new DOMSource(getPuerto().leerDoc()), result);

             System.out.println("Archivo XML guardado en: " + file.getAbsolutePath());
            

         } catch (TransformerException e) {
             e.printStackTrace();
         }
    	 
    }
}

