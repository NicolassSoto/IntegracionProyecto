package conexion;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class ConectorSalida extends Conector {

    public ConectorSalida(Puerto p, String f) {
        super(p, f);
    }

    public void escribirComanda() {
        try {
            Source source = new DOMSource(getPuerto().getSlot().desencolar().getContenido());
            Result result = new StreamResult(new File(getFichero()));
            
            Transformer transformer = TransformerFactory.newDefaultInstance().newTransformer();
            try {
                transformer.transform(source, result);
            } catch (TransformerException ex) {
                Logger.getLogger(ConectorSalida.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (TransformerConfigurationException ex) {
            Logger.getLogger(ConectorSalida.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
