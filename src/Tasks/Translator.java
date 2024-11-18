package Tasks;

import java.util.List;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;

import conexion.Slot;
import resources.Mensaje;
import resources.XmlTransformer;

public class Translator extends ITask {

    private List<Slot> entrada;
    private List<Slot> salida;
    private XmlTransformer transformer;
    private String xsltFilePath;

    public Translator(List<Slot> entrada, List<Slot> salida, String xsltFilePath) {
        this.entrada = entrada;
        this.salida = salida;
        this.xsltFilePath = xsltFilePath;
        this.transformer = new XmlTransformer();
    }
    
    public Translator() {
    	
    }

    public void procesarMensaje(Mensaje mensajeEntrada, String tipo) throws Exception {
        //Convierte el contenido del mensaje (Document) a String
        String xmlInput = documentToString(mensajeEntrada.getContenido());

        //Hace la transformación XSLT
        String contenidoTransformado = transformer.ApplyXSLT(xmlInput, xsltFilePath);

        //Convierte el resultado transformado de String a Document
        Document contenidoTraducido = stringToDocument(contenidoTransformado);

        //Establece el contenido traducido en el mensaje
        mensajeEntrada.setContenido(contenidoTraducido);

        //Añade el mensaje transformado a la salida
        for (Slot slotSalida : salida) {
            slotSalida.añadirABuffer(mensajeEntrada);
        }
    }

    private Document stringToDocument(String xmlStr) throws Exception {
        return transformer.stringToDocument(xmlStr); 
    }

    //Método para convertir Document a String
    private String documentToString(Document doc) throws Exception {
        return transformer.documentToString(doc); // Usamos el método de XmlTransformer
    }

    @Override
    public void run() throws IllegalArgumentException, ParserConfigurationException, Exception {
        String tipo = "t";  //Se debe elegir un valor en base a qué se desea pasar

        for (Slot slotEntrada : entrada) {
            Mensaje mensaje = slotEntrada.extraerMensaje();
            if (mensaje != null) {
                procesarMensaje(mensaje, tipo);
            }
        }
    }
}
