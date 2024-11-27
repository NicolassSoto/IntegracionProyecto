package conexion;

import org.w3c.dom.Document;

import resources.Mensaje;

public class PuertoSalida extends Puerto {

    public PuertoSalida(Slot slot) {
        super(slot);
    }
    
    
    public Document leer() {
        return getSlot().desencolar().getContenido();
    }
    
}
