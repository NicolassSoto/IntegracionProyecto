package Tasks;

import java.util.Iterator;
import java.util.List;

import conexion.Slot;
import resources.Mensaje;

//Correlaciona los mensajes de sus dos entradas (Normalmente usando el id) Y los saca al mismo tiempo por sus dos salidas.
public class Correlator implements ITask{

	private List<Slot> entrada;
	private List<Slot> salida;
	
	private List<Mensaje> A, B;
	
	public void run() {
		
		while(!entrada.get(0).isEmpty()) {
			A.add(entrada.get(0).extraerMensaje());
		}
	
		while(!entrada.get(1).isEmpty()) {
			B.add(entrada.get(1).extraerMensaje());
		}
	
		//Esta forma de buscar los mensajes coincidentes solo funcionaria en secuencial, ya que damos por hecho que
		//cada mensaje tiene su coincidente. En paralelo esto no sería así, ya que puede no haber llegado aun.
		
		 Iterator<Mensaje> iteratorA = A.iterator();
		
		  while (iteratorA.hasNext()) {
		        Mensaje mensajeA = iteratorA.next();
		        Iterator<Mensaje> iteratorB = B.iterator();

		        while (iteratorB.hasNext()) {
		            Mensaje mensajeB = iteratorB.next();

		            if (mensajeA.getCabecera().equals(mensajeB.getCabecera())) {
		                // Si encuentra coincidencia los envia
		                salida.get(0).añadirABuffer(mensajeA);
		                salida.get(1).añadirABuffer(mensajeB);

		                //Los borra de las listas y pasa al siguiente Mensaje
		                iteratorA.remove();
		                iteratorB.remove();
		                break;
		            }
		        }
		    }
	
	
	
	}
}
