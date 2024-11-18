package conexion;

import resources.Mensaje;

public class PuertoEntrada extends Puerto{

    public PuertoEntrada(Slot s) {
        super(s);
    }

    @Override
    public Mensaje leerMensaje() {
        return getSlot().desencolar();
    }
}
