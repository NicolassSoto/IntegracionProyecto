package conexion;

import resources.Mensaje;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.JSQLParserException;

public class PuertoSolicitud extends Puerto {

    Slot salida;

    public PuertoSolicitud(Slot slot, Slot salida) {
        super(slot);
        this.salida = salida;
    }

    @Override
    public Mensaje leerMensaje() {
        return getSlot().desencolar();
    }

    @Override
    public void escribirMensaje(Mensaje m) {
        salida.setMensaje(m);
    }

}
