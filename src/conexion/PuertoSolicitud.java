package conexion;

import resources.Mensaje;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.JSQLParserException;

public class PuertoSolicitud implements IPuerto{

	public Mensaje recibirMensaje() {
		
			Mensaje m = slot.get(0).extraerMensaje();
			
			boolean isOk = checkSQL(m);
			
			if(isOk) {
				return m;			
			}else {
				return null;
			}

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
	
	
}
