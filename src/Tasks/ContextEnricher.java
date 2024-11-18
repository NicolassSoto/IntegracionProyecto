package Tasks;

//Añade contenido al cuerpo del mensaje de entrada a partir de la información de contexto ofrecida en la entrada “contexto”. 2 entradas y 1 salida

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import conexion.Slot;
import resources.Mensaje;
import resources.XmlTransformer;

public class ContextEnricher extends Task {

    private Slot mensaje;    // Mensaje de entrada en XML
    private Slot contexto;   // Información de contexto en XML
    private Slot salida;     // Salida para el mensaje enriquecido en XML
    private XmlTransformer transformer;

    // Constructor que inicializa las entradas y la salida
    public ContextEnricher(Slot mensaje, Slot contexto, Slot salida, XmlTransformer transformer) {
        this.mensaje = mensaje;
        this.contexto = contexto;
        this.salida = salida;
        this.transformer = transformer;
    }
    
    public ContextEnricher() {
    	
    }

    @Override
    public void run() {
        try {
            // Verificar que mensaje y contexto están definidos y tienen contenido
            if (mensaje == null || contexto == null || salida == null) {
                System.out.println("Entradas o salida no definidas.");
                return;
            }
            if (!mensaje.isEmpty()) {
                System.out.println("No hay mensaje base para enriquecer.");
                return;
            }
            if (!contexto.isEmpty()) {
                System.out.println("No hay información de contexto disponible.");
                return;
            }

            // Enriquecer el documento del mensaje con el contexto
            Document mensajeEnriquecido = transformer.enriquecerMensajeConContexto(mensaje.extraerMensaje().getContenido(), contexto.extraerMensaje().getContenido());

            // Convertir el documento enriquecido a String y enviarlo a la salida
            Mensaje mensajeEnriquecidoStr = new Mensaje(mensaje.desencolar().getCabecera(), mensajeEnriquecido);
            salida.setMensaje(mensajeEnriquecidoStr);


        } catch (Exception e) {
            System.out.println("Error al procesar el mensaje XML: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
