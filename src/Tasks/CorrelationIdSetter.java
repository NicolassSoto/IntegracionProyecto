package Tasks;

import java.util.List;

import conexion.Slot;
import resources.Mensaje;
import resources.XmlTransformer;

//Almacena en la cabecera un ID de correlacion
public class CorrelationIdSetter implements ITask{

	private Slot entrada;
	private Slot salida;
	
	private int ID = 0;
	
	
	
	//CONSTRUCTORES
	public CorrelationIdSetter() {
	}
	
	public CorrelationIdSetter(List<Slot> entrada, List<Slot> salida) {
		
		this.entrada = entrada.get(0);
		this.salida = salida.get(0);
		
	}
	
	
	//SETTERS
	

	public void setEntrada(List<Slot> entrada) {
		this.entrada = entrada.get(0);
	}

	
	public void setSalida(List<Slot> salida) {
		this.salida = salida.get(0);
	}
	
	
	
	
public void run() {
	
	Mensaje m;
	
	while(!entrada.isEmpty()) {
		m = entrada.extraerMensaje();
		m.setCabecera(generateID());
		salida.añadirABuffer(m);	
	}
	
}

private String generateID() {
	
	ID++;
	
	return String.format("%06d", ID);
}

}
