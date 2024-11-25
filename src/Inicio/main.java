package Inicio;

import Tasks.Splitter;
import conexion.Slot;
import resources.Mensaje;
import org.xml.sax.SAXException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.TransformerException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;

public class main {
//creo que ya esta arreglado
	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException, TransformerException {

		String xml = "<cafe_order><order_id>2</order_id><drinks><drink><name>cafe</name><type>hot</type></drink><drink><name>chocolate</name><type>hot</type></drink><drink><name>coca-cola</name><type>cold</type></drink></drinks></cafe_order>";
		
		String xml2= "<cafe_order><order_id>6</order_id><drinks><drink><name>te</name><type>hot</type></drink><drink><name>tila</name><type>hot</type></drink><drink><name>coca-cola</name><type>cold</type></drink><drink><name>guarana</name><type>cold</type></drink><drink><name>cerveza</name><type>cold</type></drink></drinks></cafe_order>";
		
		Slot entrada = new Slot();
		Slot salida = new Slot();
		
		Slot salidahot = new Slot();
		Slot salidacold = new Slot();
		
		List<Slot> salidas = new ArrayList<>();
		
		salidas.add(salidahot);
		salidas.add(salidacold);
		
		Splitter spl = new Splitter(entrada, salida);
		
		Mensaje m = new Mensaje();
		
		 DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
         DocumentBuilder builder = factory.newDocumentBuilder();

         // Convertir el XML en un flujo de entrada y analizarlo en un Document
         Document inputDoc = builder.parse(new ByteArrayInputStream(xml.getBytes()));

         m.setContenido(inputDoc);
         entrada.setMensaje(m);  
         Mensaje m2 = new Mensaje();
         Document inputDoc2 = builder.parse(new ByteArrayInputStream(xml2.getBytes()));
         m2.setContenido(inputDoc2);
         entrada.setMensaje(m2);
         
         spl.run();
         
         List<Mensaje> men = salida.getListaMensajes();
         
         for(Mensaje mens : men) {
        	 System.out.println("NMensajes" + mens.getnMensajesEnConjunto() + "   IDCon" + mens.getIdConjunto() + "\n");
        	 TransformerFactory transformerFactory = TransformerFactory.newInstance();
             Transformer transformer = transformerFactory.newTransformer();
             StringWriter writer = new StringWriter();
             transformer.transform(new DOMSource(mens.getContenido()), new StreamResult(writer));
             String xmlString = writer.toString();
             System.out.println(xmlString);
         }
         
	}
	
	public static void imprimirDocumento(Document doc) throws Exception {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(System.out);

        transformer.transform(source, result); 
    }
}
