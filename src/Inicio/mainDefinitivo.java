package Inicio;

import Tasks.*;
import conexion.Slot;
import resources.Mensaje;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import java.io.ByteArrayInputStream;
import java.util.List;

public class mainDefinitivo {
    public static void main(String[] args) throws Exception { 
        
        String file = "ruta/al/archivo.xml"; 

        //Slots para la comunicación entre tareas
        Slot entrada = new Slot();
        Slot splitterOutput = new Slot();
        Slot siguienteSlot = new Slot(); 
        Slot distributorCold = new Slot();
        Slot distributorHot = new Slot();
        List<Slot> salida = List.of(distributorCold, distributorHot);
        Slot replicatorCold = new Slot();
        Slot replicatorHot = new Slot();
        Slot correlatorOutput = new Slot();
        Slot mergerOutput = new Slot();
        Slot aggregatorOutput = new Slot();

        //Tareas
        Splitter splitter = new Splitter(entrada, splitterOutput, "/comanda/bebidas/bebida");
        CorrelationIdSetter idSetter = new CorrelationIdSetter(splitterOutput, siguienteSlot);
        Distributor distributor = new Distributor(idSetter, distributorCold, distributorHot);
        Replicator replicatorCold = new Replicator(distributorCold, replicatorCold);
        Replicator replicatorHot = new Replicator(distributorHot, replicatorHot);
        Translator translatorCold = new Translator(replicatorCold);
        Translator translatorHot = new Translator(replicatorHot);
        Correlator correlator = new Correlator(translatorCold, translatorHot, correlatorOutput);
        ContextEnricher enricher = new ContextEnricher(correlatorOutput);
        Merger merger = new Merger(enricher, mergerOutput);
        Aggregator aggregator = new Aggregator(mergerOutput, aggregatorOutput);

        Mensaje mensaje = crearMensajeDesdeXML(file);
        entrada.setMensaje(mensaje);

        splitter.run();
        idSetter.run(); 
        distributor.run(); 
        replicatorCold.run();  
        replicatorHot.run();  
        translatorCold.run(); 
        translatorHot.run();  
        correlator.run();  
        enricher.run(); 
        merger.run();  
        aggregator.run();  

        List<Mensaje> resultadoFinal = aggregatorOutput.getListaMensajes();
        for (Mensaje m : resultadoFinal) {
            System.out.println(m.toString());  // Muestra el mensaje enriquecido
        }
    }

    // Método para crear un Mensaje desde una cadena XML
    private static Mensaje crearMensajeDesdeXML(String xml) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new ByteArrayInputStream(xml.getBytes()));
        Mensaje mensaje = new Mensaje();
        mensaje.setContenido(doc);
        return mensaje;
    }
}

