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
    public List<Document> splitXmlMessage(Document contenido, String tagName) throws ParserConfigurationException {

        List<Document> splitDocuments = new ArrayList<>();

        NodeList drinks = contenido.getElementsByTagName(tagName);

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        for (int i = 0; i < drinks.getLength(); i++) {
            Node drinkNode = drinks.item(i);

            Document newDoc = builder.newDocument();

            Node importedDrink = newDoc.importNode(drinkNode, true);
            newDoc.appendChild(importedDrink);

            splitDocuments.add(newDoc);
        }

        return splitDocuments;

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

    public static Mensaje combinarConjunto(List<Mensaje> conjunto) throws Exception {

        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        Document nuevoDocumento = docBuilder.newDocument();

        Element rootElement = nuevoDocumento.createElement("root");
        nuevoDocumento.appendChild(rootElement);

        Element orderIdElement = nuevoDocumento.createElement("order_id");
        orderIdElement.appendChild(nuevoDocumento.createTextNode(conjunto.get(0).getIdConjunto()));
        rootElement.appendChild(orderIdElement);

        for (Mensaje mensaje : conjunto) {
            Document contenido = mensaje.getContenido();
            NodeList drinkList = contenido.getElementsByTagName("drink");

            Node drinkNode = drinkList.item(0);

            Node importedNode = nuevoDocumento.importNode(drinkNode, true);
            rootElement.appendChild(importedNode); // Añadir el nodo <drink> al <root>

        }
        Mensaje m = new Mensaje();
        m.setContenido(nuevoDocumento);
        return m;
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
    public Document enriquecerMensajeConContexto(Document mensajeDoc, Document contextoDoc) throws ParserConfigurationException {

        // 
        NodeList contextoList = contextoDoc.getElementsByTagName("stock");

        if (contextoList.getLength() > 0) {
            Node stockNode = contextoList.item(0);  // Obtener el primer nodo <stock>
            String stockValue = stockNode.getTextContent();  // Obtener el contenido de texto (valor de stock)

            // Crear un nuevo elemento <stock> con el mismo valor de texto del contexto
            Element stockElement = mensajeDoc.createElement("stock");
            stockElement.appendChild(mensajeDoc.createTextNode(stockValue));  // Añadir el valor de stock como texto

            // Buscar el elemento <drink> en mensajeDoc y añadirle el nuevo elemento <stock>
            NodeList drinkList = mensajeDoc.getElementsByTagName("drink");
            if (drinkList.getLength() > 0) {
                Element drinkElement = (Element) drinkList.item(0);  // Obtener el primer (y único) <drink>
                drinkElement.appendChild(stockElement);  // Añadir el <stock> como hijo del <drink>
            }
        }

        // Retornar el documento enriquecido
        return mensajeDoc;
    }
}
