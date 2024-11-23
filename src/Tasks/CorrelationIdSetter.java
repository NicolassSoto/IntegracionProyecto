package Tasks;

import java.util.List;

import conexion.Slot;
import resources.Mensaje;

//Almacena en la cabecera un ID de correlacion
<<<<<<< HEAD
public class CorrelationIdSetter extends ITask {
=======
public class CorrelationIdSetter extends Task {
>>>>>>> branch 'master' of https://Cristiangb02@github.com/NicolassSoto/IntegracionProyecto.git

    private Slot entrada;
    private Slot salida;

    private int ID = 0;

    public CorrelationIdSetter() {
    }

    public CorrelationIdSetter(Slot entrada, Slot salida) {

        this.entrada = entrada;
        this.salida = salida;
    }
    
    public void run() {

        List<Mensaje> mensajes = entrada.getListaMensajes();

        for (Mensaje m : mensajes) {
            m.setIdMensaje(generateID());
            salida.setMensaje(m);
        }
    }

    private String generateID() {

        ID++;

        return String.format("%06d", ID);
    }

}
