package conexion;

import java.util.ArrayList;

import resources.Mensaje;

public class Slot {

    private ArrayList<Mensaje> listaMensajes = new ArrayList<>();

    public void setMensaje(Mensaje doc) {
        listaMensajes.add(doc);
    }

    public ArrayList<Mensaje> getListaMensajes() {
        return listaMensajes;
    }
    
    public Mensaje desencolar(){
        return listaMensajes.remove(0);
    }
    
    public boolean isEmpty(){
        return listaMensajes.isEmpty();
    }
}
