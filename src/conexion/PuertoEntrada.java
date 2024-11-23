package conexion;

import org.w3c.dom.Document;
import resources.Mensaje;
<<<<<<< HEAD
import resources.XmlTransformer;

import java.util.ArrayList;

public class PuertoEntrada {
=======
>>>>>>> branch 'master' of https://Cristiangb02@github.com/NicolassSoto/IntegracionProyecto.git

<<<<<<< HEAD
    private ArrayList<Mensaje> listaMensajes;
    private XmlTransformer transformer;

    public PuertoEntrada() {
        this.listaMensajes = new ArrayList<>();
        this.transformer = new XmlTransformer();
    }

    public void recibirMensaje(Document mensajeXml) {

        Mensaje mensaje = new Mensaje(mensajeXml, "Cabecera", 0);
        listaMensajes.add(mensaje);
        System.out.println("Mensaje añadido a la lista.");
    }

    public Mensaje enviarMensaje() {
        if (!listaMensajes.isEmpty()) {
            Mensaje mensaje = listaMensajes.get(0);
            listaMensajes.remove(0);
            System.out.println("Mensaje enviado correctamente.");
            return mensaje;
        } else {
            System.out.println("No hay mensajes en la lista para enviar.");
            return null;
        }
    }

=======
public class PuertoEntrada extends Puerto{
    
    Mensaje m;

    public PuertoEntrada(Slot s) {
        super(s);
        m = new Mensaje();
    }

    @Override
    public void escribirMensaje(Document body) {
        m.setContenido(body);
        getSlot().setMensaje(m);
    }
>>>>>>> branch 'master' of https://Cristiangb02@github.com/NicolassSoto/IntegracionProyecto.git
}
