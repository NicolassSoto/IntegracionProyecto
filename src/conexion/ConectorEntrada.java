package conexion;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.w3c.dom.Document;

public class ConectorEntrada extends Conector {

    public ConectorEntrada(Puerto p, String f) {
        super(p, f);
    }

    public void leerComandas() {
        try {
            Document document = transformer.stringToDocument(getFichero());
            
            getPuerto().escribirMensaje(document);
        } catch (Exception ex) {
            Logger.getLogger(ConectorEntrada.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
