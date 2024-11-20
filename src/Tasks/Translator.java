package Tasks;

import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import conexion.Slot;
import java.util.List;

import resources.Mensaje;
import resources.XmlTransformer;

public class Translator extends Task {

    private XmlTransformer transformer;
    private String xsltFilePath;

    private Slot entrada, salida;

    public Translator(String xsltFilePath, Slot entrada, Slot salida) {
        super();
        this.xsltFilePath = xsltFilePath;
        this.entrada = entrada;
        this.salida = salida;
        transformer = new XmlTransformer();
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
