package Tasks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import conexion.Slot;
import resources.Mensaje;
import resources.XmlTransformer;

//Reconstruye un mensaje divido previamente por una tarea Splitter. 1 Entrada y 1 Salida
public class Aggregator extends Task {

    private Slot entrada;
    private Slot salida;
    private XmlTransformer transformer;

    public Aggregator() {

    }

    public Aggregator(Slot entrada, Slot salida) {
        this.entrada = entrada;
        this.salida = salida;
        transformer = new XmlTransformer();
    }

    public void run() throws Exception {

        List<Mensaje> listaMensajes = entrada.getListaMensajes();

        Map<String, List<Mensaje>> mensajesPorConjunto = new HashMap<>();

        Iterator<Mensaje> iterator = listaMensajes.iterator();

        while (iterator.hasNext()) {
            Mensaje mensaje = iterator.next();

            if (!mensajesPorConjunto.containsKey(mensaje.getIdConjunto())) {

                mensajesPorConjunto.put(mensaje.getIdConjunto(), new ArrayList<>());
            }

            mensajesPorConjunto.get(mensaje.getIdConjunto()).add(mensaje);
        }

        // Llamar a la función con cada conjunto de mensajes
        for (List<Mensaje> conjunto : mensajesPorConjunto.values()) {
            // Llamamos a la función que debes definir
            salida.setMensaje(transformer.combinarConjunto(conjunto));
        }
    }

}

