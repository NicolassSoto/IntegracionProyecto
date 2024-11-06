package resources;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.io.File;

public class XmlTransformer {

		public XmlTransformer() {
			
		}

	    public static Document ApplyXSLT(Document  xmlInput, String xsltFilePath) throws TransformerException {
	        StreamSource xsltSource = new StreamSource(new File(xsltFilePath));

	      
	        TransformerFactory factory = TransformerFactory.newInstance();
	        Transformer transformer = factory.newTransformer(xsltSource);

	        // Crear un DOMSource a partir del Document de entrada
	        DOMSource source = new DOMSource(xmlInput);

	        // Crear un DOMResult para almacenar el resultado de la transformación
	        DOMResult result = new DOMResult();

	        // Aplicar la transformación
	        transformer.transform(source, result);

	        // Retornar el Document de salida desde DOMResult
	        return (Document) result.getNode();
	    }
	    
	    
	    public static List<Document> segmentXmlByTag(Document xmlInput, String tagName) throws Exception {
	        List<Document> segments = new ArrayList<>();

	       
	        NodeList nodes = xmlInput.getElementsByTagName(tagName);

	      
	        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	        DocumentBuilder builder = factory.newDocumentBuilder();

	        for (int i = 0; i < nodes.getLength(); i++) {
	            Node node = nodes.item(i);

	            // Crear un nuevo documento para cada nodo
	            Document segmentDoc = builder.newDocument();
	            Node importedNode = segmentDoc.importNode(node, true);
	            segmentDoc.appendChild(importedNode);

	            segments.add(segmentDoc);
	        }

	        return segments;
	    }
}
