package Tasks;

import java.util.List;

import conexion.Slot;
import resources.Mensaje;

//Encamina los mensajes de todas sus entradas hacia una única salida
public class Merger implements ITask {

	private List<Slot> entrada;
	private Slot salida;

	public Merger() {
		
	}

	public Merger(List<Slot> entrada, Slot salida) {
		super();
		this.entrada = entrada;
		this.salida = salida;
	}


	public void run() {
		 if (entrada == null || entrada.isEmpty()) {
	            System.out.println("No hay entradas para procesar.");
	            return;
	        }
	        if (salida == null) {
	            System.out.println("No se ha definido una salida.");
	            return;
	        }

	        // Recorremos cada Slot en la lista de entrada.
	        for (Slot slot : entrada) {
	            // Verificamos que el slot no sea nulo y tenga contenido.
	            if (slot != null && slot.isEmpty()) {
	                // Obtenemos el mensaje del Slot de entrada.
	                Mensaje mensaje = slot.extraerMensaje();

	                // Envía el mensaje al Slot de salida.
	                salida.añadirABuffer(mensaje);
	            }
	        }
	        System.out.println("Todos los mensajes de entrada han sido enviados a la salida.");

	}
}
