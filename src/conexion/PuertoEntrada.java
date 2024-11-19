package conexion;

import org.w3c.dom.Document;
import resources.Mensaje;

public class PuertoEntrada extends Puerto{
    
    Mensaje m;

    public PuertoEntrada(Slot s) {
        super(s);
        m = new Mensaje();
    }

    @Override
    public void escribirMensaje(Document body) {
        m.setContenido(body);
        getSlot().setMensaje(m);
    }
}
