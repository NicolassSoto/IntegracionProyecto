package Tasks;

import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import conexion.Slot;
import resources.Mensaje;
import resources.XmlTransformer;

public class Splitter extends Task {

    private XmlTransformer transformer;
    Slot entrada;
    Slot salida;
    private String splitTag; // Expresión XPath para dividir las bebidas


    public Splitter(Slot entrada, Slot salida) {

        this.entrada = entrada;
        this.salida = salida;
        this.transformer = new XmlTransformer();

    }

    public Splitter() {

    }

    public Splitter(Slot entrada, Slot splitterOutput, String splitTag) {
        this.entrada = entrada;
        this.salida = splitterOutput;
        this.splitTag = splitTag;
        this.transformer = new XmlTransformer();
    }
    
    public void run() {

        List<Mensaje> mensajes = entrada.getListaMensajes();
        
        for (Mensaje mensaje : mensajes) {

            try {
            	System.out.println("COMANDA: " + mensaje.toString());
                List<Mensaje> messageList = transformer.splitXmlMessage(mensaje, splitTag);
                
                for(Mensaje m : messageList) {
                	salida.setMensaje(m);
                	
                }
                
                
            } catch (ParserConfigurationException e) {
            }
        }
    }
}
