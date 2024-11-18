package Tasks;

//Añade contenido al cuerpo del mensaje de entrada a partir de la información de contexto ofrecida en la entrada “contexto”. 2 entradas y 1 salida

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import conexion.Slot;
import resources.Mensaje;
import resources.XmlTransformer;

public class ContextEnricher extends Task {

    private Slot entrada;    // Mensaje de entrada en XML
    private Slot contexto;   // Información de contexto en XML
    private Slot salida;     // Salida para el mensaje enriquecido en XML
    private XmlTransformer transformer;

    // Constructor que inicializa las entradas y la salida
    public ContextEnricher(Slot mensaje, Slot contexto, Slot salida, XmlTransformer transformer) {
        this.entrada = mensaje;
        this.contexto = contexto;
        this.salida = salida;
        this.transformer = transformer;
    }
    
    public ContextEnricher() {
    	
    }

    @Override
    public void run() {
        try {
            

            
            Mensaje mensaje = entrada.desencolar();
           
            Document contenidoEnriquecido = transformer.enriquecerMensajeConContexto(mensaje.getContenido(), contexto.desencolar().getContenido());

            mensaje.setContenido(contenidoEnriquecido);
            

            salida.setMensaje(mensaje);


        } catch (Exception e) {
            System.out.println("Error al procesar el mensaje XML: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
