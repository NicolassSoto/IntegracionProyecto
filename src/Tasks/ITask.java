package Tasks;

import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import conexion.Slot;
import resources.XmlTransformer;

public interface ITask {

	public static final XmlTransformer procesador = new XmlTransformer(); 
	public static final List<Slot> listaSlots = null;
	public void run() throws IllegalArgumentException, ParserConfigurationException, Exception;
	
}
