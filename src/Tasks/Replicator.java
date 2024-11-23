package Tasks;

import conexion.Slot;
import resources.Mensaje;
import java.util.List;

<<<<<<< HEAD
public class Replicator extends ITask {
=======
public class Replicator extends Task {
>>>>>>> branch 'master' of https://Cristiangb02@github.com/NicolassSoto/IntegracionProyecto.git

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
