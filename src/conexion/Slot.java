package conexion;

import java.util.List;

import resources.Mensaje;

public class Slot {
	
	private List buffer;
	
	public Mensaje extraerMensaje() {
        if (buffer.isEmpty()) {
            return null; 
        }
        return (Mensaje) buffer.remove(0);  
    }

    public void añadirABuffer(Mensaje m) {
        buffer.add(m);
    }
    
    public boolean isEmpty() {return buffer.isEmpty();}
}