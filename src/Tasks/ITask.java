package Tasks;

import java.util.List;

import conexion.Slot;
import resources.XmlTransformer;

public interface ITask {

	public static final XmlTransformer procesador = new XmlTransformer(); 
	public static final List<Slot> listaSlots = null;
	public void run();
	
}
