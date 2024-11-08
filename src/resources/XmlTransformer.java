package resources;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.io.File;
import org.xml.sax.InputSource;
import org.xml.sax.InputSource;
import org.xml.sax.InputSource;
import org.xml.sax.InputSource;
import org.xml.sax.InputSource;
import org.xml.sax.InputSource;
import org.xml.sax.InputSource;
import org.xml.sax.InputSource;
import org.xml.sax.InputSource;

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
	    
	 // Método para dividir un mensaje XML en múltiples Documents según la etiqueta de elemento
	    public Document[] splitXmlMessage(Document contenido, String tagName) throws Exception {
	        // Convertir el Document a String para poder usar segmentXmlByTag
	        String xmlInput = documentToString(contenido);

	        // Obtener cada segmento como string usando la etiqueta especificada
	        List<String> segments = segmentXmlByTag(xmlInput, tagName);

	        // Convertir cada segmento a Document y añadirlo a la lista
	        List<Document> documentList = new ArrayList<>();
	        for (String segment : segments) {
	            Document doc = stringToDocument(segment);
	            documentList.add(doc);
	        }

	        // Convertir la lista a un array y retornarlo
	        return documentList.toArray(new Document[0]);
	    }
	    
	    // Convertir un Document a String
	    private String documentToString(Document doc) throws TransformerException {
	        TransformerFactory tf = TransformerFactory.newInstance();
	        Transformer transformer = tf.newTransformer();
	        StringWriter writer = new StringWriter();
	        transformer.transform(new javax.xml.transform.dom.DOMSource(doc), new javax.xml.transform.stream.StreamResult(writer));
	        return writer.getBuffer().toString();
	    }

	    // Convertir un String a Document
	    private Document stringToDocument(String xmlStr) throws Exception {
	        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	        DocumentBuilder builder = factory.newDocumentBuilder();
	        return builder.parse(new InputSource(new StringReader(xmlStr)));
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
	    
	    public Document convertirFormato(Document contenido, String tipo) throws IllegalArgumentException, ParserConfigurationException {
	        if ("SQL".equalsIgnoreCase(tipo)) {
	            return convertirAConsultaSQL(contenido);
	        } else {
	            throw new IllegalArgumentException("Tipo de formato no soportado: " + tipo);
	        }
	    }

	    //Convertir Document a una consulta SQL
	    private Document convertirAConsultaSQL(Document document) throws ParserConfigurationException {
	        // Suponemos que el XML tiene un elemento <registro> con subelementos <id>, <nombre>, <email>
	        Element root = document.getDocumentElement();

	        String id = getElementValue(root, "id");
	        String tipo = getElementValue(root, "tipo");
	        String stock = getElementValue(root, "stock");

	        // Creamos un nuevo documento para la consulta SQL
	        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	        DocumentBuilder builder = factory.newDocumentBuilder();
	        Document sqlDoc = builder.newDocument();
	        
	        // Creamos el elemento raíz <consulta>
	        Element consultaElement = sqlDoc.createElement("consulta");
	        
	        // Creamos el subelemento <sql> con la consulta formateada
	        Element sqlElement = sqlDoc.createElement("sql");
	        String sqlQuery = String.format("INSERT INTO usuarios (id, tipo, stock) VALUES (%s, '%s', '%s');", id, tipo,stock);
	        sqlElement.appendChild(sqlDoc.createTextNode(sqlQuery));

	        // Añadimos el subelemento <sql> al elemento raíz
	        consultaElement.appendChild(sqlElement);

	        // Añadimos el elemento raíz <consulta> al documento
	        sqlDoc.appendChild(consultaElement);

	        return sqlDoc;
	    }

	    // Método auxiliar para obtener el valor de un elemento XML
	    private String getElementValue(Element parent, String tagName) {
	        Element element = (Element) parent.getElementsByTagName(tagName).item(0);
	        return element != null ? element.getTextContent() : "";
	    }
}