package Tasks;

import java.util.ArrayList;
import java.util.List;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import conexion.Slot;
import resources.Mensaje;

public class Distributor extends Task {

    private Slot entrada;
    private List<Slot> salida;
    private List<XPathExpression> expresiones;

    public Distributor() {
    }

    public Distributor(Slot entrada, List<Slot> salida, List<String> expresiones) {
        this.entrada = entrada;
        this.salida = salida;
        this.expresiones = new ArrayList<>();

        XPath xpath = XPathFactory.newInstance().newXPath();

        try {
            for (String s : expresiones) {
                this.expresiones.add(xpath.compile(s));
            }
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        List<Mensaje> mensajes = entrada.getListaMensajes();

        for (Mensaje m : mensajes) {
            for (int i = 0; i < salida.size(); i++) {
                try {
                    Boolean cumpleExpresion = (Boolean) expresiones.get(i).evaluate(m.getContenido(), XPathConstants.BOOLEAN);

                    if (cumpleExpresion) {
                        salida.get(i).setMensaje(m);
                    }
                } catch (XPathExpressionException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

