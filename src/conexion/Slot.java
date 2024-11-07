package conexion;

import java.util.List;

import resources.Mensaje;

public class Slot {
	
	private List buffer;
	
	public Mensaje extraerMensaje() {
        if (buffer.isEmpty()) {
            return null;  // Si el buffer está vacío, no hay mensajes para extraer
        }
        return (Mensaje) buffer.remove(0);  // Extraer el primer mensaje (FIFO)
    }

    // Método para añadir un mensaje al buffer
    public synchronized void añadirABuffer(Mensaje m) {
        buffer.add(m);
    }
}