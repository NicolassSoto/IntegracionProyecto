package conexion;

import resources.Mensaje;
import org.w3c.dom.Document;

public class PuertoSolicitud extends Puerto {

    Slot salida;
    Mensaje m;

<<<<<<< HEAD
	}
	
	public void enviarMensaje(Mensaje m) {
		if(checkSalida(m)) {
		slot.get(1).añadirABuffer(m);
		}else {
			System.out.println("CONECTOR: EL FORMATO DE SALIDA NO COINCIDE");
		}
		
	}
	private boolean checkSQL(Mensaje m) {
		 String content = m.getContenido().getTextContent().trim();
=======
    public PuertoSolicitud(Slot slot, Slot salida) {
        super(slot);
        this.salida = salida;
        m = new Mensaje();
    }
>>>>>>> branch 'master' of https://Cristiangb02@github.com/NicolassSoto/IntegracionProyecto.git

<<<<<<< HEAD
	        try {
	          
	            CCJSqlParserUtil.parse(content);
	            
	            return true; 
	        } catch (JSQLParserException e) {
	        	System.out.println("CONECTOR: EL FORMATO DE ENTRADA NO COINCIDE");
	            return false; 
	        }
		
	}
	
	private boolean checkSalida(Mensaje m) {
		
		String content = m.getContenido().getTextContent().trim();
		
		//Para stock
		try {
	        Double.parseDouble(content); 
	        return true;
	    } catch (NumberFormatException e) {
	        return false;
	    }	
	}
=======
    @Override
    public Mensaje leerMensaje() {
        return getSlot().desencolar();
    }

    @Override
    public void escribirMensaje(Document body) {
        m.setContenido(body);
        salida.setMensaje(m);
    }
    
    @Override
    public void setIdCorrelacion(String idCorrelacion){
        m.setIdConjunto(idCorrelacion);
    }

>>>>>>> branch 'master' of https://Cristiangb02@github.com/NicolassSoto/IntegracionProyecto.git
}
