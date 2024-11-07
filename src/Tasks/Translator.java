package Tasks;

import java.util.List;
import java.util.concurrent.BlockingQueue;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.w3c.dom.Document;

import conexion.Slot;
import resources.Mensaje;
import resources.XmlTransformer;

//Transforma el cuerpo de un mensaje de un formato a otro

public class Translator implements ITask{

	private BlockingQueue<String> entryPort;
	private BlockingQueue<String> exitPort;	
	private XmlTransformer transformer;
	private List<Slot> entrada;
	private List<Slot> salida;

	private Mensaje m;
	private String xsltFilePath;

	public Translator() {}
	
	
	
	public Translator(BlockingQueue<String> entryPort, BlockingQueue<String> exitPort, String xsltFilePath) {
		super();
		this.entryPort = entryPort;
		this.exitPort = exitPort;
		this.xsltFilePath = xsltFilePath;
		transformer = new XmlTransformer();
	}
	

public void run() throws IllegalArgumentException, ParserConfigurationException {
	// Extraer el mensaje del slot de entrada
	String tipo="t";
    for (Slot slotEntrada : entrada) {
        Mensaje mensaje = slotEntrada.extraerMensaje();
        if (mensaje != null) {
            // Realizar la traducción
            Document contenidoTraducido = procesador.convertirFormato(mensaje.getContenido(), tipo);
            mensaje.setContenido(contenidoTraducido);

            // Añadir el mensaje transformado al slot de salida
            for (Slot slotSalida : salida) {
                slotSalida.añadirABuffer(mensaje);
            }
        }
    }
	}
	
}