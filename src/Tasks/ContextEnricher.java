package Tasks;

//Añade contenido al cuerpo del mensaje de entrada a partir de la información de contexto ofrecida en la entrada “contexto”. 2 entradas y 1 salida
import org.w3c.dom.Document;
import conexion.Slot;
import java.util.List;
import resources.Mensaje;
import resources.XmlTransformer;

public class ContextEnricher extends Task {

    private Slot entrada;    // Mensaje de entrada en XML
    private Slot contexto;   // Información de contexto en XML
    private Slot salida;     // Salida para el mensaje enriquecido en XML
    private XmlTransformer transformer;

    // Constructor que inicializa las entradas y la salida
    public ContextEnricher(Slot entrada, Slot contexto, Slot salida) {
        this.entrada = entrada;
        this.contexto = contexto;
        this.salida = salida;
        this.transformer = new XmlTransformer();
    }

    public ContextEnricher() {

    }

    @Override
    public void run() {
        try {

        	List<Mensaje> mensajes = entrada.getListaMensajes();
        	List<Mensaje> contextos = contexto.getListaMensajes();
        	
            for(int i = 0; i < mensajes.size(); i++) {
            	
            	Document contenidoEnriquecido = transformer.enriquecerMensajeConContexto(mensajes.get(i).getContenido(), contextos.get(i).getContenido());
            	mensajes.get(i).setContenido(contenidoEnriquecido);
            	salida.setMensaje(mensajes.get(i));
            }
        	
        
        } catch (Exception e) {
            System.out.println("Error al procesar el mensaje XML: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
