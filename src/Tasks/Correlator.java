package Tasks;

import java.util.Iterator;
import java.util.List;
import conexion.Slot;
import resources.Mensaje;

// Correlaciona los mensajes de sus múltiples entradas (Normalmente usando el id) y los saca al mismo tiempo por sus múltiples salidas.
public class Correlator extends Task {

    private List<Slot> entrada;
    private List<Slot> salida;

    public Correlator(List<Slot> entrada, List<Slot> salida) {
        super();
        this.entrada = entrada;
        this.salida = salida;
    }

    public void run() {
        // Asumimos que hay al menos dos entradas y dos salidas
        if (entrada.size() < 2 || salida.size() < 2) {
            throw new IllegalArgumentException("Debe haber al menos 2 entradas y 2 salidas.");
        }

        Slot entradaA = entrada.get(0); 
        Slot entradaB = entrada.get(1); 
        Slot salidaA = salida.get(0);  
        Slot salidaB = salida.get(1); 

        List<Mensaje> A = entradaA.getListaMensajes();
        List<Mensaje> B = entradaB.getListaMensajes();

        Iterator<Mensaje> iteratorA = A.iterator();

        while (iteratorA.hasNext()) {
            Mensaje mensajeA = iteratorA.next();
            Iterator<Mensaje> iteratorB = B.iterator();

            while (iteratorB.hasNext()) {
                Mensaje mensajeB = iteratorB.next();

                if (mensajeA.getIdMensaje().equals(mensajeB.getIdMensaje())) {
                    // Enviar mensajes correlacionados a las salidas
                    salidaA.setMensaje(mensajeA);
                    salidaB.setMensaje(mensajeB);

                    iteratorA.remove();
                    iteratorB.remove();
                    break;
                }
            }
        }
    }
}
