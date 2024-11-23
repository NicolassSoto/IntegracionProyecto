package Tasks;

import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import conexion.Slot;
import java.util.List;

import resources.Mensaje;
import resources.XmlTransformer;

<<<<<<< HEAD
public class Translator extends ITask {
=======
public class Translator extends Task {
>>>>>>> branch 'master' of https://Cristiangb02@github.com/NicolassSoto/IntegracionProyecto.git

    private XmlTransformer transformer;
    private String xsltFilePath;

    private Slot entrada, salida;

    public Translator(String xsltFilePath, Slot entrada, Slot salida) {
        super();
        this.xsltFilePath = xsltFilePath;
        this.entrada = entrada;
        this.salida = salida;
<<<<<<< HEAD
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
=======
        transformer = new XmlTransformer();
>>>>>>> branch 'master' of https://Cristiangb02@github.com/NicolassSoto/IntegracionProyecto.git
    }

    @Override
    public void run() throws IllegalArgumentException, ParserConfigurationException, Exception {

        List<Mensaje> mensajes = entrada.getListaMensajes();

        for (Mensaje m : mensajes) {

            Document resultado = transformer.aplicarXslt(m.getContenido(), xsltFilePath);
            m.setContenido(resultado);
            salida.setMensaje(m);
        }
    }
}
