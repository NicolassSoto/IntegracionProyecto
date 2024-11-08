package Tasks;

import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import resources.Mensaje;
import resources.XmlTransformer;

public class Splitter implements ITask {

    private XmlTransformer transformer;
    private String xsltFilePath;
    private Mensaje mensajeEntrada; 

    // Constructor para inicializar los campos
    public Splitter(XmlTransformer transformer, String xsltFilePath, Mensaje mensajeEntrada) {
        this.transformer = transformer;
        this.xsltFilePath = xsltFilePath;
        this.mensajeEntrada = mensajeEntrada;
    }

    public Mensaje[] procesarMensaje(Mensaje mensajeEntrada) throws Exception {
        //Usa el transformer para dividir el contenido en varios Document individuales
        Document[] mensajesDivididos = transformer.splitXmlMessage(mensajeEntrada.getContenido(), xsltFilePath);

        //Crea un array de Mensaje para devolver cada Document dividido
        Mensaje[] mensajesSalida = new Mensaje[mensajesDivididos.length];
        for (int i = 0; i < mensajesDivididos.length; i++) {
            mensajesSalida[i] = new Mensaje(mensajeEntrada.getCabecera(), mensajesDivididos[i]);
        }
        
        return mensajesSalida;
    }

    @Override
    public void run() {
        try {
            if (mensajeEntrada == null) {
                throw new IllegalArgumentException("El mensaje de entrada no puede ser null.");
            }

            Mensaje[] mensajesSalida = procesarMensaje(mensajeEntrada);
            
            for (Mensaje mensaje : mensajesSalida) {
                System.out.println("Cabecera: " + mensaje.getCabecera());
                System.out.println("Contenido: " + mensaje.getContenido());
            }
        } catch (Exception e) {
            System.err.println("Error en la tarea Splitter: " + e.getMessage());
            e.printStackTrace();
        }
    }
}