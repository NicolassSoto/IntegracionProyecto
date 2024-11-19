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

    public Splitter(Slot entrada, Slot salida) {

        this.entrada = entrada;
        this.salida = salida;

        this.transformer = new XmlTransformer();

    }

    public Splitter() {

    }

    public void run() {

        List<Mensaje> mensajes = entrada.getListaMensajes();

        for (Mensaje mensaje : mensajes) {

            NodeList orderIdList = mensaje.getContenido().getElementsByTagName("order_id");

            String idConjunto = orderIdList.item(0).getTextContent();

            try {
                List<Document> drinks = transformer.splitXmlMessage(mensaje.getContenido(), "drink");

                for (Document d : drinks) {
                    Mensaje m = new Mensaje();
                    m.setContenido(d);
                    m.setIdConjunto(idConjunto);
                    m.setnMensajesEnConjunto(drinks.size());

                    salida.setMensaje(m);
                }

            } catch (ParserConfigurationException e) {
            }

        }

    }
}
