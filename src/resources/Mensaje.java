package resources;

import org.w3c.dom.Document;


public class Mensaje {


    private Document contenido;
    private String cabecera;
    private int idTrozo;

    public Mensaje(Document contenido, String cabecera, int idTrozo) {
        super();
        this.contenido = contenido;
        this.cabecera = cabecera;
        this.idTrozo = idTrozo;
    }

    public int getnMensaje() {
        return idTrozo;
    }

    public void setnMensaje(int nMensaje) {
        this.idTrozo = nMensaje;
    }

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
