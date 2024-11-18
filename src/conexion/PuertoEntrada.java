package conexion;

import org.w3c.dom.Document;
import resources.Mensaje;
import resources.XmlTransformer;

import java.util.ArrayList;

public class PuertoEntrada {

    private ArrayList<Mensaje> listaMensajes;
    private XmlTransformer transformer;

    public PuertoEntrada() {
        this.listaMensajes = new ArrayList<>();
        this.transformer = new XmlTransformer();
    }

    public void recibirMensaje(Document mensajeXml) {
        if (checkMensaje(mensajeXml)) {
            Mensaje mensaje = new Mensaje(mensajeXml, "Cabecera", 0);
            listaMensajes.add(mensaje);
            System.out.println("Mensaje añadido a la lista.");
        } else {
            System.err.println("Error al recibir mensaje.");
        }
    }

    public Mensaje enviarMensaje() {
        if (!listaMensajes.isEmpty()) {
            Mensaje mensaje = listaMensajes.get(0);
            listaMensajes.remove(0); 
            System.out.println("Mensaje enviado correctamente.");
            return mensaje;
        } else {
            System.out.println("No hay mensajes en la lista para enviar.");
            return null;
        }
    }

    private boolean checkMensaje(Document mensajeXml) {
        try {
            String contenido = transformer.documentToString(mensajeXml);
            return contenido.contains("<comanda>") && contenido.contains("</comanda>");
        } catch (Exception e) {
            System.err.println("Error al validar el mensaje.");
            return false;
        }
    }
}
