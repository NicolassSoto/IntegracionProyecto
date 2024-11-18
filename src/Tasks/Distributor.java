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
public class Distributor extends ITask{

	private List<Slot> entrada;
	private List<Slot> salida;
	
	public Distributor() {}
	
	public Distributor(List<Slot> entrada, List<Slot> salida) {
		this.entrada = entrada;
		this.salida = salida;	
	}
	
	public void setEntrada(List<Slot> entrada) {
		this.entrada = entrada;
	}
	
	public void setSalida(List<Slot> salida) {
		this.salida = salida;
	}
	
	
public void run() {
		
	//Se crea una instancia XPath y se define la expresion
	//Por ejemplo vamos a obtener en un String el atributo tipo
	
	
	XPath xpath = XPathFactory.newInstance().newXPath();
	
	try {
		XPathExpression expr = xpath.compile("//tipo/text()");
	
	
	
	//Entramos en el bucle para comprobar cada mensaje y enviarlo a una salida concreta segun el tipo
	while(!entrada.get(0).isEmpty()) {
		
		Mensaje m = entrada.get(0).extraerMensaje();
		
		String tipo = (String) expr.evaluate(m.getContenido(), XPathConstants.STRING);
		
		
		
		switch(tipo) {
		
		case "tipo1": 
			salida.get(0).añadirABuffer(m);
			break;
			
			
		case "tipo2":
			salida.get(1).añadirABuffer(m);
			break;
		default:
		
		}
		
		
	}
	
	
	} catch (XPathExpressionException e) {
		e.printStackTrace();
	}
	
	
	
	}
}
