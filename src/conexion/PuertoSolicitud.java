package conexion;

import resources.Mensaje;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.JSQLParserException;
import org.w3c.dom.Document;

public class PuertoSolicitud extends Puerto {

    Slot salida;
    Mensaje m;

    public PuertoSolicitud(Slot slot, Slot salida) {
        super(slot);
        this.salida = salida;
        m = new Mensaje();
    }

    @Override
    public Mensaje leerMensaje() {
        return getSlot().desencolar();
    }

    @Override
    public void escribirMensaje(Document body) {
        m.setContenido(body);
        salida.setMensaje(m);
    }

}
