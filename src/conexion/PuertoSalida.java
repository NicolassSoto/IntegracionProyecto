package conexion;

import resources.Mensaje;

public class PuertoSalida extends Puerto {

    public PuertoSalida(Slot slot) {
        super(slot);
    }
    
    @Override
    public Mensaje leerMensaje() {
        return getSlot().desencolar();
    }
    
}
