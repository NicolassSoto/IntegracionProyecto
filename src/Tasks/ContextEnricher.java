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
    private String xsltFilePath;

    // Constructor que inicializa las entradas y la salida
    public ContextEnricher(Slot entrada, Slot contexto, Slot salida, String xsltFilePath) {
        this.entrada = entrada;
        this.contexto = contexto;
        this.salida = salida;
        this.transformer = new XmlTransformer();
        this.xsltFilePath = xsltFilePath;
    }

    public ContextEnricher() {

    }

    @Override
    public void run() {
        try {
        	List<Mensaje> mensajes = entrada.getListaMensajes();
        	List<Mensaje> contextos = contexto.getListaMensajes();
        
            for(int i = 0; i < mensajes.size(); i++) {
            	Mensaje m = mensajes.get(i);
            	Mensaje context = contextos.get(i);
            	Document contenidoEnriquecido = transformer.combinarDocumentos(m.getContenido(), transformer.aplicarXslt(context.getContenido(), xsltFilePath));
            	m.setContenido(contenidoEnriquecido);
            	salida.setMensaje(m);
            }
        	
        
        } catch (Exception e) {
            System.out.println("Error al procesar el mensaje XML: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
