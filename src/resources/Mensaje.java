package resources;

import org.w3c.dom.Document;

public class Mensaje {

	    private Document contenido;
	    private String cabecera;

	    
	    //CONSTRUCTORES
	    public Mensaje() {
	    }
	    
	    public Mensaje(Document contenido) {
	        this.contenido = contenido;
	    }

	    public Mensaje(String cabecera, Document contenido) {
	    	this.cabecera = cabecera;
	    	this.contenido = contenido;
	    }
	    
	    //GETTERS Y SETTERS
	    public Document getContenido() {
	        return contenido;
	    }

	    public void setContenido(Document contenido) {
	        this.contenido = contenido;
	    }

		public String getCabecera() {
			return cabecera;
		}

		public void setCabecera(String cabecera) {
			this.cabecera = cabecera;
		}
	    
	    
	}