package conexion;

import org.w3c.dom.Document;
import resources.Mensaje;

public abstract class Puerto {

    private Slot slot;

    public Puerto(Slot slot) {
        this.slot = slot;
    }

    public void setSlot(Slot slot) {
        this.slot = slot;
    }

    public Slot getSlot() {
        return slot;
    }

    public void escribirMensaje(Document m) {
    }

    public Mensaje leerMensaje() {
        return null;
    }
    
    public Document leerDoc() {
    	return slot.desencolar().getContenido();
    }

    public void setIdCorrelacion(String idCorrelacion) {
    }
}
