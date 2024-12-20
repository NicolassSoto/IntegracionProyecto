package Inicio;

import Tasks.*;
import conexion.ConectorDB;
import conexion.ConectorEntrada;
import conexion.ConectorSalida;
import conexion.PuertoEntrada;
import conexion.PuertoSalida;
import conexion.PuertoSolicitud;
import conexion.SalidaAPI;
import conexion.Slot;
import resources.DatabaseManager;
import resources.Mensaje;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;
import resources.XmlTransformer;

public class mainDefinitivo {

    public static void main(String[] args) throws Exception {

        // Necesarios
        String folderInput = "src/InputFolder";
        String folderOutput = "src/OutputFolder";
        XmlTransformer transformerCold = null;
        XmlTransformer transformerHot = null;
        String tagSplitter = "drink";
        String tagAggregator = "drinks";
        String xsltTransformer = "./Transformer.xml";
        String path = "./ContextEnricher.xml";
        String tagCorrelacion = "name";

        List<String> expresiones = new ArrayList<>();
        expresiones.add("//drink[type='cold']"); // Expresión para drinks frías
        expresiones.add("//drink[type='hot']"); // Expresión para drinks calientes

        // Base de datos
        DatabaseManager DB = new DatabaseManager();
        DB.initializeDatabase();

        // Slots para la comunicación entre tareas
        // Splitter
        Slot splitterInput = new Slot();
        Slot splitterOutput = new Slot();

        // Id_Setter
        Slot salidaIdSetter = new Slot();

        // Distributor
        Slot distributorColdOutput = new Slot();
        Slot distributorHotOutput = new Slot();
        List<Slot> salida = List.of(distributorColdOutput, distributorHotOutput);

        // Replicator
        Slot replicatorColdOutputToTranslator = new Slot();
        Slot replicatorHotOutputToTransalator = new Slot();
        Slot replicatorColdOutputToCorrelator = new Slot();
        Slot replicatorHotOutputToCorrelator = new Slot();
        List<Slot> replicatorColdOutput = List.of(replicatorColdOutputToTranslator, replicatorColdOutputToCorrelator);
        List<Slot> replicatorHotOutput = List.of(replicatorHotOutputToCorrelator, replicatorHotOutputToTransalator);

        // Translator (bebidas frías)
        Slot translatorColdOutput = new Slot();

        // Translator (bebidas calientes)
        Slot translatorHotOutput = new Slot();

        // Correlator (bebidas frías)
        Slot correlatorColdInput1 = new Slot();
        Slot correlatorColdInput2 = new Slot();
        List<Slot> entradaCorrelatorCold = List.of(correlatorColdInput1, correlatorColdInput2);
        Slot correlatorColdOutput1 = new Slot();
        Slot correlatorColdOutput2 = new Slot();
        List<Slot> correlatorColdOutput = List.of(correlatorColdOutput1, correlatorColdOutput2);

        // Correlator (bebidas calientes)
        Slot correlatorHotInput1 = new Slot();
        Slot correlatorHotInput2 = new Slot();
        List<Slot> entradaCorrelatorHot = List.of(correlatorHotInput1, correlatorHotInput2);
        Slot correlatorHotOutput1 = new Slot();
        Slot correlatorHotOutput2 = new Slot();
        List<Slot> correlatorHotOutput = List.of(correlatorHotOutput1, correlatorHotOutput2);

        // Context enricher (bebidas frías)
        Slot enricherColdContext = new Slot();
        Slot enricherColdMessaje = new Slot();
        Slot enricherColdOutput = new Slot();

        // Context enricher (bebidas calientes)
        Slot enricherHotContext = new Slot();
        Slot enricherHotMessaje = new Slot();
        Slot enricherHotOutput = new Slot();

        // Merger
        List<Slot> mergerInput = List.of(enricherColdOutput, enricherHotOutput);
        Slot mergerOutput = new Slot();

        // Aggregator
        Slot aggregatorOutput = new Slot();

        // PUERTOS
        PuertoEntrada pEntrada = new PuertoEntrada(splitterInput);
        PuertoSolicitud pSolHot = new PuertoSolicitud(translatorHotOutput, correlatorHotInput2);
        PuertoSolicitud pSolCold = new PuertoSolicitud(translatorColdOutput, correlatorColdInput2);
        PuertoSalida pSalida = new PuertoSalida(aggregatorOutput);

        // CONECTORES
        ConectorEntrada conectorEntrada = new ConectorEntrada(pEntrada, folderInput);
        ConectorDB conectorHot = new ConectorDB(pSolHot, DB.getConnection());
        ConectorDB conectorCold = new ConectorDB(pSolCold, DB.getConnection());
        ConectorSalida conectorSalida = new ConectorSalida(pSalida, folderOutput);
        SalidaAPI salidaAPI = new SalidaAPI(pSalida);
        // CONEXIÓN ENTRE TAREAS
        Splitter splitter = new Splitter(splitterInput, splitterOutput, tagSplitter);

        CorrelationIdSetter idSetter = new CorrelationIdSetter(splitterOutput, salidaIdSetter);

        Distributor distributor = new Distributor(salidaIdSetter, salida, expresiones);

        Replicator replicatorCold = new Replicator(distributorColdOutput, replicatorColdOutput);
        Replicator replicatorHot = new Replicator(distributorHotOutput, replicatorHotOutput);

        Translator translatorCold = new Translator(xsltTransformer, replicatorColdOutputToTranslator,
                translatorColdOutput);
        Translator translatorHot = new Translator(xsltTransformer, replicatorHotOutputToTransalator,
                translatorHotOutput);

        // HASTA AQUI FUNCIONA
        List<Slot> inputCorrelatorCold = List.of(replicatorColdOutputToCorrelator, correlatorColdInput2);
        Correlator correlatorCold = new Correlator(inputCorrelatorCold, correlatorColdOutput, tagCorrelacion);

        List<Slot> inputCorrelatorHot = List.of(replicatorHotOutputToCorrelator, correlatorHotInput2);
        Correlator correlatorHot = new Correlator(inputCorrelatorHot, correlatorHotOutput, tagCorrelacion);

        ContextEnricher enricherCold = new ContextEnricher(correlatorColdOutput1, correlatorColdOutput2,
                enricherColdOutput, path);
        ContextEnricher enricherHot = new ContextEnricher(correlatorHotOutput1, correlatorHotOutput2,
                enricherHotOutput, path);

        Merger merger = new Merger(mergerInput, mergerOutput);

        Aggregator aggregator = new Aggregator(mergerOutput, aggregatorOutput, tagAggregator);

        conectorEntrada.readFolder();

        splitter.run();
        idSetter.run();
        distributor.run();
        replicatorCold.run();
        replicatorHot.run();
        translatorCold.run();
        translatorHot.run();

        conectorCold.run();
        conectorHot.run();
        
        correlatorCold.run();
        correlatorHot.run();

        enricherCold.run();
        enricherHot.run();

        merger.run();

        aggregator.run();

        //conectorSalida.write();
        salidaAPI.sendToTelegram();
        
        /*List<Mensaje> resultadoFinal = correlatorColdOutput.get(0).getListaMensajes();
        for (Mensaje m : resultadoFinal) {

            System.out.println(m.toString());  // Muestra el mensaje enriquecido
        }*/

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
