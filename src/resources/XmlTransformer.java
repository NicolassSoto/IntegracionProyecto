package resources;

import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import org.xml.sax.InputSource;

public class XmlTransformer {

	public XmlTransformer() {

	}

	public Document aplicarXslt(Document inputDoc, String xsltFilePath) throws Exception {

		TransformerFactory factory = TransformerFactory.newInstance();

		StreamSource xsltStream = new StreamSource(new File(xsltFilePath));
		Transformer transformer = factory.newTransformer(xsltStream);

		DOMSource source = new DOMSource(inputDoc);

		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

		StreamResult result = new StreamResult(byteArrayOutputStream);

		transformer.transform(source, result);

		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
		Document outputDoc = docBuilder.parse(byteArrayInputStream);

		return outputDoc;
	}

	// Método para dividir un mensaje XML en múltiples Documents según la etiqueta
	// de elemento
	public List<Mensaje> splitXmlMessage(Mensaje originalMes, String tagName) throws ParserConfigurationException {

	
		Document originalDoc = originalMes.getContenido();
		
		NodeList nodes = originalDoc.getElementsByTagName(tagName);

		for (int i = nodes.getLength() - 1; i >= 0; i--) {
			Node node = nodes.item(i);

			// Eliminar el nodo del documento
			Node parent = node.getParentNode();
			if (parent != null) {
				parent.removeChild(node);
			}
		}

		int nNodos = nodes.getLength();

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();

		List<Mensaje> messageList = new ArrayList<>();

		for (int i = 0; i < nNodos; i++) {

			Node node = nodes.item(i);

			Document newDoc = builder.newDocument();

			Node importedNode = newDoc.importNode(node, true);
			newDoc.appendChild(importedNode);

			Mensaje m = new Mensaje();

			 m.setIdConjunto(originalMes.getIdMensaje());
			m.setnMensajesEnConjunto(nNodos);
			m.setPosicionEnConjunto(i);
			m.setContenido(newDoc);

			if (i == nNodos - 1) {
				m.setOriginal(originalDoc);
			}

			messageList.add(m);

		}

		return messageList;

	}

	// Convertir un Document a String
	public String documentToString(Document doc) throws TransformerException {
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer transformer = tf.newTransformer();
		StringWriter writer = new StringWriter();
		transformer.transform(new javax.xml.transform.dom.DOMSource(doc),
				new javax.xml.transform.stream.StreamResult(writer));
		return writer.getBuffer().toString();
	}

	public static Document combinarConjunto(List<Mensaje> conjunto, String tag) throws Exception {

		
        conjunto.sort(Comparator.comparingInt(Mensaje::getPosicionEnConjunto));

        Document originalDoc = conjunto.get(conjunto.size() - 1).getOriginal();

        NodeList destinoList = originalDoc.getElementsByTagName(tag);
        if (destinoList.getLength() == 0) {
            throw new Exception("El nodo destino '" + tag + "' no existe en el documento.");
        }
        
        Node nodoDestinoElement = destinoList.item(0); 

       
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

       
        for (Mensaje mensaje : conjunto) {
            Document contenido = mensaje.getContenido();

            // Importar y añadir todos los nodos del contenido actual al nodo destino
            NodeList childNodes = contenido.getDocumentElement().getChildNodes();
            for (int i = 0; i < childNodes.getLength(); i++) {
                Node importedNode = originalDoc.importNode(childNodes.item(i), true);
                nodoDestinoElement.appendChild(importedNode);
            }
        }

        // Retornar el nuevo documento modificado
        return originalDoc;
    }
	

	// Convertir un String a Document
	public Document stringToDocument(String xmlStr) throws Exception {
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

	public Document convertirFormato(Document contenido, String tipo)
			throws IllegalArgumentException, ParserConfigurationException {
		if ("SQL".equalsIgnoreCase(tipo)) {
			return convertirAConsultaSQL(contenido);
		} else {
			throw new IllegalArgumentException("Tipo de formato no soportado: " + tipo);
		}
	}

	// Convertir Document a una consulta SQL
	private Document convertirAConsultaSQL(Document document) throws ParserConfigurationException {
		// Suponemos que el XML tiene un elemento <registro> con subelementos <id>,
		// <nombre>, <email>
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
		String sqlQuery = String.format("INSERT INTO usuarios (id, tipo, stock) VALUES (%s, '%s', '%s');", id, tipo,
				stock);
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

	// Método para enriquecer el mensaje base con el contexto
	public Document enriquecerMensajeConContexto(Document mensajeDoc, Document contextoDoc)
			throws ParserConfigurationException {

		//
		NodeList contextoList = contextoDoc.getElementsByTagName("stock");

		if (contextoList.getLength() > 0) {
			Node stockNode = contextoList.item(0); // Obtener el primer nodo <stock>
			String stockValue = stockNode.getTextContent(); // Obtener el contenido de texto (valor de stock)

			// Crear un nuevo elemento <stock> con el mismo valor de texto del contexto
			Element stockElement = mensajeDoc.createElement("stock");
			stockElement.appendChild(mensajeDoc.createTextNode(stockValue)); // Añadir el valor de stock como texto

			// Buscar el elemento <drink> en mensajeDoc y añadirle el nuevo elemento <stock>
			NodeList drinkList = mensajeDoc.getElementsByTagName("drink");
			if (drinkList.getLength() > 0) {
				Element drinkElement = (Element) drinkList.item(0); // Obtener el primer (y único) <drink>
				drinkElement.appendChild(stockElement); // Añadir el <stock> como hijo del <drink>
			}
		}

		// Retornar el documento enriquecido
		return mensajeDoc;
	}
}
