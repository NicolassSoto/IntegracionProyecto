package Tasks;

import java.util.List;

import conexion.Slot;
import resources.Mensaje;
import resources.XmlTransformer;

//Almacena en la cabecera un ID de correlacion
public class CorrelationIdSetter extends Task {

    private Slot entrada;
    private Slot salida;

    private int ID = 0;

    // CONSTRUCTORES
    public CorrelationIdSetter() {
    }

    public CorrelationIdSetter(Slot entrada, Slot salida) {

        this.entrada = entrada;
        this.salida = salida;

    }

    // SETTERS
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
