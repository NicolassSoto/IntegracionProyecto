package Tasks;

import java.util.List;

import conexion.Slot;
import resources.Mensaje;

//Encamina los mensajes de todas sus entradas hacia una única salida
public class Merger extends Task {

    private List<Slot> entradas;
    private Slot salida;

    public Merger(List<Slot> entradas, Slot salida) {
        super();
        this.entradas = entradas;
        this.salida = salida;
    }

    public void run() {

        for (Slot s : entradas) {

            List<Mensaje> mensajes = s.getListaMensajes();
            for (Mensaje m : mensajes) {
                salida.setMensaje(m);
            }

        }

    }
}
