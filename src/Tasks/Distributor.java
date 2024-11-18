package Tasks;

import java.util.List;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import conexion.Slot;
import resources.Mensaje;

//Distribuye los mensajes de una entrada hacia varias salidas siguiendo un criterio.
public class Distributor extends Task{

	private Slot entrada;
	private List<Slot> salida;
	
	public Distributor() {}
	
	public Distributor(Slot entrada, List<Slot> salida) {
		this.entrada = entrada;
		this.salida = salida;	
	}
	
	
	
public void run() {
		
	//Se crea una instancia XPath y se define la expresion
	//Por ejemplo vamos a obtener en un String el atributo tipo
	
	
	XPath xpath = XPathFactory.newInstance().newXPath();
	
	try {
		XPathExpression expr = xpath.compile("//tipo/text()");
	
	
	
	List<Mensaje> mensajes = entrada.getListaMensajes();
	
	for(Mensaje m : mensajes) {
			
		String tipo = (String) expr.evaluate(m.getContenido(), XPathConstants.STRING);

		
		switch(tipo) {
		
		case "cold": 
			salida.get(0).setMensaje(m);
			break;
			
			
		case "hot":
			salida.get(1).setMensaje(m);
			break;
		default:
		
		}
		
		
	}
	
	
	} catch (XPathExpressionException e) {
		e.printStackTrace();
	}
	
	
	
	}
}
