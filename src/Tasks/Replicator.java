package Tasks;

import conexion.Slot;
import resources.Mensaje;
import java.util.ArrayList;
import java.util.List;

public class Replicator implements ITask {

	@Override
	public void run() {

		Mensaje mensajeEntrada = obtenerMensajeEntrada();

		if (mensajeEntrada != null) {
			//Se crean múltiples copias del mensaje
			List<Mensaje> mensajesReplicados = replicarMensaje(mensajeEntrada);

			// Envía cada mensaje replicado a una salida 
			enviarMensajesReplicados(mensajesReplicados);
		}
	}

	private Mensaje obtenerMensajeEntrada() {
		//Se recorren los slots para encontrar uno que tenga mensajes
		for (Slot slot : listaSlots) {
			if (!slot.isEmpty()) { 
				return slot.extraerMensaje(); 
			}
		} 
		return null; //Si no hay mensajes en los slots
	}

	private List<Mensaje> replicarMensaje(Mensaje mensajeOriginal) {
		List<Mensaje> replicas = new ArrayList<>();

		// Se crean copias profundas del mensaje original para replicarlo
		for (int i = 0; i < listaSlots.size(); i++) {
			Mensaje copia = new Mensaje(mensajeOriginal.getCabecera(), mensajeOriginal.getContenido());
			replicas.add(copia);
		}

		return replicas;
	}

	private void enviarMensajesReplicados(List<Mensaje> mensajesReplicados) {
		for (int i = 0; i < mensajesReplicados.size(); i++) {
			Slot slotDestino = listaSlots.get(i); // Se asigna cada réplica a un Slot distinto
			slotDestino.añadirABuffer(mensajesReplicados.get(i)); // Se añade mensaje al Slot
		}
	}
}
