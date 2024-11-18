package conexion;

import resources.Mensaje;
import resources.XmlTransformer;


public class PuertoSalida {

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

    private boolean check(Mensaje m) {
     
        //No sé exactamente cómo lo comprobaríamos aquí
        return true;
    }
}
