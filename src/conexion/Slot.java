package conexion;

import java.util.ArrayList;

import resources.Mensaje;

public class Slot {
<<<<<<< HEAD
	
	private List buffer;
	
	public Mensaje extraerMensaje() {
        if (buffer.isEmpty()) {
            return null; 
        }
        return (Mensaje) buffer.remove(0);  
=======

    private ArrayList<Mensaje> listaMensajes;

    public Slot() {
        listaMensajes = new ArrayList<>();
>>>>>>> branch 'master' of https://Cristiangb02@github.com/NicolassSoto/IntegracionProyecto.git
    }

<<<<<<< HEAD
    public void añadirABuffer(Mensaje m) {
        buffer.add(m);
=======
    public void setMensaje(Mensaje doc) {
        listaMensajes.add(doc);
    }

    public ArrayList<Mensaje> getListaMensajes() {
        return listaMensajes;
    }

    public Mensaje desencolar() {
        return listaMensajes.remove(0);
    }

    public boolean isEmpty() {
        return listaMensajes.isEmpty();
>>>>>>> branch 'master' of https://Cristiangb02@github.com/NicolassSoto/IntegracionProyecto.git
    }
    
    
    public Mensaje getMensaje() {
        return listaMensajes.get(0);
    }
}
