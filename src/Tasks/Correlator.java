package Tasks;

import java.util.Iterator;
import java.util.List;
import conexion.Slot;
import java.util.ArrayList;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Element;
import resources.Mensaje;
import resources.XmlTransformer;

// Correlaciona los mensajes de sus múltiples entradas (Normalmente usando el id) y los saca al mismo tiempo por sus múltiples salidas.
public class Correlator extends Task {

    private List<Slot> entrada;
    private List<Slot> salida;
    private String tagCorrelacion;
    private XmlTransformer transformer = new XmlTransformer();

    public Correlator(List<Slot> entrada, List<Slot> salida, String tagCorrelacion) {
        super();
        this.entrada = entrada;
        this.salida = salida;
        this.tagCorrelacion = tagCorrelacion;

    }

    public void run() {
        // Asumimos que hay al menos dos entradas y dos salidas
        if (entrada.size() < 2 || salida.size() < 2) {
            throw new IllegalArgumentException("Debe haber al menos 2 entradas y 2 salidas.");
        }

        Slot entradaA = entrada.get(0);
        Slot entradaB = entrada.get(1);
        Slot salidaA = salida.get(0);
        Slot salidaB = salida.get(1);

        List<Mensaje> A = entradaA.getListaMensajes();
        List<Mensaje> B = entradaB.getListaMensajes();

        Iterator<Mensaje> iteratorA = A.iterator();

        while (iteratorA.hasNext()) {
            Mensaje mensajeA = iteratorA.next();
            Iterator<Mensaje> iteratorB = B.iterator();

            String nombre1 = transformer.getElementValueFromDocument(mensajeA.getContenido(), tagCorrelacion);

            while (iteratorB.hasNext()) {
                Mensaje mensajeB = iteratorB.next();

                String nombre2 = transformer.getElementValueFromDocument(mensajeB.getContenido(), tagCorrelacion);

                if (nombre1.equals(nombre2)) {
                    // Enviar mensajes correlacionados a las salidas
                    salidaA.setMensaje(mensajeA);
                    salidaB.setMensaje(mensajeB);

                    iteratorA.remove();
                    iteratorB.remove();
                    break;
                }
            }
        }
    }
}
