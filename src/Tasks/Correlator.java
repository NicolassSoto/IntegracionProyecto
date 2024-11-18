package Tasks;

import java.util.Iterator;
import java.util.List;

import conexion.Slot;
import resources.Mensaje;

//Correlaciona los mensajes de sus dos entradas (Normalmente usando el id) Y los saca al mismo tiempo por sus dos salidas.
public class Correlator extends Task{

	private List<Slot> entrada;
	private List<Slot> salida;
	
	private List<Mensaje> A, B;
	
	public void run() {
		
		A = entrada.get(0).getListaMensajes();
		
		B = entrada.get(1).getListaMensajes();
		
	
		//Esta forma de buscar los mensajes coincidentes solo funcionaria en secuencial, ya que damos por hecho que
		//cada mensaje tiene su coincidente. En paralelo esto no sería así, ya que puede no haber llegado aun.
		
		 Iterator<Mensaje> iteratorA = A.iterator();
		
		  while (iteratorA.hasNext()) {
		        Mensaje mensajeA = iteratorA.next();
		        Iterator<Mensaje> iteratorB = B.iterator();

		        while (iteratorB.hasNext()) {
		            Mensaje mensajeB = iteratorB.next();

		            if (mensajeA.getIdMensaje().equals(mensajeB.getIdMensaje())) {
		                // Si encuentra coincidencia los envia
		                salida.get(0).setMensaje(mensajeA);
		                salida.get(1).setMensaje(mensajeB);

		                //Los borra de las listas y pasa al siguiente Mensaje
		                iteratorA.remove();
		                iteratorB.remove();
		                break;
		            }
		        }
		    }
	
	
	
	}
}
