package resources;

import org.w3c.dom.Document;

<<<<<<< HEAD

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
=======
public class Mensaje {

    //CABECERA
	private String idMensaje;
	private String idMensajeCorrelacion;
    private String idConjunto;
    private String posicionEnConjunto;
    private Document original;
    int nMensajesEnConjunto;

    //CUERPO
    private Document contenido;

    public Mensaje() {

    }

    public Mensaje(String idMensaje, String idConjunto, int nMensajesEnConjunto, Document contenido) {
        super();
        this.idMensaje = idMensaje;
        this.idConjunto = idConjunto;
        this.nMensajesEnConjunto = nMensajesEnConjunto;
        this.contenido = contenido;
    }

    public String getIdMensaje() {
        return idMensaje;
    }

    public void setIdMensaje(String idMensaje) {
        this.idMensaje = idMensaje;
    }

    public String getIdConjunto() {
        return idConjunto;
    }

    public void setIdConjunto(String idConjunto) {
        this.idConjunto = idConjunto;
    }

    public int getnMensajesEnConjunto() {
        return nMensajesEnConjunto;
    }

    public void setnMensajesEnConjunto(int nMensajesEnConjunto) {
        this.nMensajesEnConjunto = nMensajesEnConjunto;
    }

    public Document getContenido() {
        return contenido;
    }

    public void setContenido(Document contenido) {
        this.contenido = contenido;
>>>>>>> branch 'master' of https://Cristiangb02@github.com/NicolassSoto/IntegracionProyecto.git
    }
}
