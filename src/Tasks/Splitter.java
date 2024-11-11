package Tasks;

import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import resources.Mensaje;
import resources.XmlTransformer;

public class Splitter implements ITask {

    private XmlTransformer transformer;
    private String xsltFilePath;
    private Mensaje mensajeEntrada; 

    public Splitter(XmlTransformer transformer, String xsltFilePath, Mensaje mensajeEntrada) {
        this.transformer = transformer;
        this.xsltFilePath = xsltFilePath;
        this.mensajeEntrada = mensajeEntrada;
    }
    
    public Splitter() {
    	
    }

    public Mensaje[] procesarMensaje(Mensaje mensajeEntrada) throws Exception {
        //Usa el transformer para dividir el contenido en varios Document individuales
        Document[] mensajesDivididos = transformer.splitXmlMessage(mensajeEntrada.getContenido(), xsltFilePath);

        Mensaje[] mensajesSalida = new Mensaje[mensajesDivididos.length];
        for (int i = 0; i < mensajesDivididos.length; i++) {
       
            mensajesSalida[i] = new Mensaje(mensajesDivididos[i], mensajeEntrada.getCabecera(), i);
        }
        
        return mensajesSalida;
    }

    @Override
    public void run() {
        try {
            if (mensajeEntrada == null) {
                throw new IllegalArgumentException();
            }

            Mensaje[] mensajesSalida = procesarMensaje(mensajeEntrada);
            
            for (Mensaje mensaje : mensajesSalida) {
                System.out.println("Cabecera: " + mensaje.getCabecera());
                System.out.println("Trozo numero: " + mensaje.getnMensaje()); 
                System.out.println("Contenido: " + mensaje.getContenido());
            }
        } catch (Exception e) {
            System.err.println("Error en la tarea Splitter: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
