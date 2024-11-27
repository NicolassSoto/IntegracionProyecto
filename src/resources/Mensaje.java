package resources;

import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

public class Mensaje {

    //CABECERA
    private String idMensaje;
    private static int contador = 0;
    private String idMensajeCorrelacion;
    private String idConjunto;
    private int posicionEnConjunto;
    private Document original;
    int nMensajesEnConjunto;
    

    //CUERPO
    private Document contenido;

    public Mensaje() {
    	this.idMensaje = String.valueOf(++contador);
    }

    public Mensaje(String idMensaje, String idConjunto, int nMensajesEnConjunto, Document contenido) {
        super();
        this.idMensaje = String.valueOf(++contador);
        this.idConjunto = idConjunto;
        this.nMensajesEnConjunto = nMensajesEnConjunto;
        this.contenido = contenido;
    }

    public String getIdMensajeCorrelacion() {
        return idMensajeCorrelacion;
    }

    public void setIdMensajeCorrelacion(String idMensajeCorrelacion) {
        this.idMensajeCorrelacion = idMensajeCorrelacion;
    }

    public int getPosicionEnConjunto() {
        return posicionEnConjunto;
    }

    public void setPosicionEnConjunto(int posicionEnConjunto) {
        this.posicionEnConjunto = posicionEnConjunto;
    }

    public Document getOriginal() {
        return original;
    }

    public void setOriginal(Document original) {
        this.original = original;
    }

    public String getIdMensaje() {
        return idMensaje;
    }

    public void setIdMensaje(String idMensaje) {
        this.idMensaje = idMensaje;
    }

    public String getIdConjunto() {
        return idConjunto;
    }

    public void setIdConjunto(String idConjunto) {
        this.idConjunto = idConjunto;
    }

    public int getnMensajesEnConjunto() {
        return nMensajesEnConjunto;
    }

    public void setnMensajesEnConjunto(int nMensajesEnConjunto) {
        this.nMensajesEnConjunto = nMensajesEnConjunto;
    }

    public Document getContenido() {
        return contenido;
    }

    public void setContenido(Document contenido) {
        this.contenido = contenido;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        boolean hayOriginal = false;
        if(original != null) {
        	hayOriginal = true;
        }
        
        // Información adicional
        sb.append("Mensaje: ").append(idMensaje).append("\n")
          .append("Id de Correlación: ").append(idMensajeCorrelacion).append("\n")
          .append("Id del Conjunto: ").append(idConjunto).append("\n")
          .append("Posición en Conjunto: ").append(posicionEnConjunto).append("\n")
          .append("Número de Mensajes en Conjunto: ").append(nMensajesEnConjunto).append("\n")
          .append("Original: ").append(hayOriginal).append("\n");

        // Convertir contenido a String
        if (contenido != null) {
            try {
                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                StringWriter writer = new StringWriter();
                transformer.transform(new DOMSource(contenido), new StreamResult(writer));
                sb.append("Contenido del Documento: \n").append(writer.toString());
            } catch (Exception e) {
                sb.append("Error al transformar el contenido del documento: ").append(e.getMessage());
            }
        } else {
            sb.append("Contenido del Documento: [VACÍO]");
        }

        return sb.toString();
    }
    
    
    public Mensaje(Mensaje otroMensaje) {
    	this.idMensaje = String.valueOf(++contador);
        this.idMensajeCorrelacion = otroMensaje.idMensajeCorrelacion;
        this.idConjunto = otroMensaje.idConjunto;
        this.posicionEnConjunto = otroMensaje.posicionEnConjunto;
        this.nMensajesEnConjunto = otroMensaje.nMensajesEnConjunto;

        // Crear copias profundas de los documentos 'original' y 'contenido'
        this.original = otroMensaje.getOriginal();
        this.contenido = copiarDocumento(otroMensaje.contenido);
    }
    
    public static Document copiarDocumento(Document original) {
    	
    	if(original == null) {
    		return null;
    	}
        try {
            // Crear un nuevo DocumentBuilderFactory y DocumentBuilder
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            
            // Crear un nuevo Document vacío
            Document copy = builder.newDocument();

            // Clonar el nodo raíz del documento original (deep copy)
            Node rootNode = original.getDocumentElement();
            Node clonedNode = rootNode.cloneNode(true);

            // Importar el nodo clonado al nuevo documento
            copy.appendChild(copy.importNode(clonedNode, true));

            return copy;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
}
