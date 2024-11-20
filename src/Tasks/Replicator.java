package Tasks;

import conexion.Slot;
import resources.Mensaje;
import java.util.List;

public class Replicator extends Task {

    private Slot entrada;
    private List<Slot> salidas;

    public Replicator(Slot entrada, List<Slot> salidas) {
        super();
        this.entrada = entrada;
        this.salidas = salidas;
    }

    @Override
    public void run() {

        List<Mensaje> mensajes = entrada.getListaMensajes();

        for (Mensaje m : mensajes) {

            for (Slot s : salidas) {
                s.setMensaje(m);
            }
        }
    }
}
