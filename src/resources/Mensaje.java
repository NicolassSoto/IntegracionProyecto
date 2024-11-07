package resources;

import org.w3c.dom.Document;

public class Mensaje {

	    private Document contenido;

	    public Mensaje(Document contenido) {
	        this.contenido = contenido;
	    }

	    // Getter para obtener el contenido del mensaje
	    public Document getContenido() {
	        return contenido;
	    }

	    // Setter para actualizar el contenido del mensaje
	    public void setContenido(Document contenido) {
	        this.contenido = contenido;
	    }
	}