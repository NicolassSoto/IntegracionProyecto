package resources;

import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.io.File;

public class XmlTransformer {

		public XmlTransformer() {
			
		}

	    public static String ApplyXSLT(String xmlInput, String xsltFilePath) throws TransformerException {
	        // Crear StreamSource a partir del XML de entrada
	        StreamSource xmlSource = new StreamSource(new StringReader(xmlInput));

	        // Crear StreamSource a partir del fichero XSLT
	        StreamSource xsltSource = new StreamSource(new File(xsltFilePath));

	        // Crear un TransformerFactory para construir el Transformer
	        TransformerFactory factory = TransformerFactory.newInstance();
	        Transformer transformer = factory.newTransformer(xsltSource);

	        // Usamos StringWriter para capturar la salida de la transformación
	        StringWriter writer = new StringWriter();
	        StreamResult result = new StreamResult(writer);

	        // Aplicar la transformación
	        transformer.transform(xmlSource, result);

	        // Retornar el resultado como String
	        return writer.toString();
	    }
	    
	    
	    public static List<String> segmentXmlByTag(String xmlInput, String tagName) {
	        List<String> segments = new ArrayList<>();

	        // Crear una etiqueta de cierre correspondiente
	        String closingTag = "</" + tagName.substring(1); // Obtener el nombre de la etiqueta sin "<" y agregar "</"

	        // Dividir el string de entrada basado en la etiqueta de cierre
	        String[] splitSegments = xmlInput.split(closingTag);

	        for (String segment : splitSegments) {
	            // Asegurarse de que el segmento no esté vacío y agregar la etiqueta de cierre
	            if (!segment.trim().isEmpty()) {
	                segments.add(segment.trim() + closingTag);
	            }
	        }

	        return segments;
	    }
	
	
	
	    
	
	
}
