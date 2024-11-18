package conexion;

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
    public void escribirMensaje(Mensaje m) {
    }
    public Mensaje leerMensaje() {
        return null;
    }
}
