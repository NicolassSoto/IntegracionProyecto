package conexion;

import resources.Mensaje;

public class PuertoSalida extends Puerto {

    public PuertoSalida(Slot slot) {
        super(slot);
    }

    @Override
    public void escribirMensaje(Mensaje m) {
        getSlot().setMensaje(m);
    }
}
