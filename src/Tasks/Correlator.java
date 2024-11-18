package Tasks;

import java.util.Iterator;
import java.util.List;

import conexion.Slot;
import resources.Mensaje;

//Correlaciona los mensajes de sus dos entradas (Normalmente usando el id) Y los saca al mismo tiempo por sus dos salidas.
public class Correlator extends Task{

	private Slot entradaA, entradaB;
	private Slot salidaA, salidaB;
	

	
	
	
	public Correlator(Slot entradaA, Slot entradaB, Slot salidaA, Slot salidaB) {
		super();
		this.entradaA = entradaA;
		this.entradaB = entradaB;
		this.salidaA = salidaA;
		this.salidaB = salidaB;
	}





	public void run() {
		
		List<Mensaje> A, B;
		A = entradaA.getListaMensajes();
		
		B = entradaB.getListaMensajes();
		
	
		//Esta forma de buscar los mensajes coincidentes solo funcionaria en secuencial, ya que damos por hecho que
		//cada mensaje tiene su coincidente. En paralelo esto no sería así, ya que puede no haber llegado aun.
		
		 Iterator<Mensaje> iteratorA = A.iterator();
		
		  while (iteratorA.hasNext()) {
		        Mensaje mensajeA = iteratorA.next();
		        Iterator<Mensaje> iteratorB = B.iterator();

		        while (iteratorB.hasNext()) {
		            Mensaje mensajeB = iteratorB.next();

		            if (mensajeA.getIdMensaje().equals(mensajeB.getIdMensaje())) {
		              
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
