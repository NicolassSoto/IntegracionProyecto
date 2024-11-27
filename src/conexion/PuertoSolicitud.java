package conexion;

import resources.Mensaje;
import org.w3c.dom.Document;

public class PuertoSolicitud extends Puerto {

    Slot salida;
    Mensaje m;

    public boolean isEmpty() {
    	return getSlot().isEmpty();
    }
    
    public PuertoSolicitud(Slot slot, Slot salida) {
        super(slot);
        this.salida = salida;
        m = new Mensaje();
    }


    public void selectMensaje() {
       Mensaje mens = getSlot().desencolar();
       m = new Mensaje(mens);
    }

    public Document getBody() {
    	return m.getContenido();
    }
    
    @Override
    public void escribirMensaje(Document body) {
        m.setContenido(body);
        salida.setMensaje(m);
    }
    
    @Override
    public void setIdCorrelacion(String idCorrelacion){
        m.setIdMensajeCorrelacion(idCorrelacion);
    }

    public Slot getSlotSalida() {
    	return salida;
    }
}
