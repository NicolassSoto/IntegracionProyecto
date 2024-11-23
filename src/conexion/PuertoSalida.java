package conexion;

import resources.Mensaje;
<<<<<<< HEAD
import resources.XmlTransformer;


public class PuertoSalida {
=======
>>>>>>> branch 'master' of https://Cristiangb02@github.com/NicolassSoto/IntegracionProyecto.git

<<<<<<< HEAD
    private Slot s;
    private XmlTransformer transformer;

    public PuertoSalida() {
        this.transformer = new XmlTransformer();
    }

    public void recibirMensaje(Mensaje mensaje) {
       
        
    }

    public Mensaje enviarMensaje() {
            Mensaje mensaje = s.extraerMensaje();
            s.añadirABuffer(mensaje);
            System.out.println("Mensaje enviado.");
            return mensaje;
    }

=======
public class PuertoSalida extends Puerto {

    public PuertoSalida(Slot slot) {
        super(slot);
    }
    
    @Override
    public Mensaje leerMensaje() {
        return getSlot().desencolar();
    }
    
>>>>>>> branch 'master' of https://Cristiangb02@github.com/NicolassSoto/IntegracionProyecto.git
}
