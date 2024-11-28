package conexion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import resources.DatabaseManager;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import org.w3c.dom.*;

public class ConectorDB {

    private Connection connection;
    private PuertoSolicitud puerto;
    
   
    public ConectorDB(PuertoSolicitud puerto, Connection con) {
        this.puerto = puerto;
        this.connection = con;
    }

    public void run() {
        while (!puerto.isEmpty()) {
            
        	puerto.selectMensaje();
        	
            String[] consultas = extraerConsulta(puerto.getBody(), "//query");

           
            
            if (consultas != null && consultas.length > 0) {
            	for (String consulta : consultas) {
            	    try {
            	        
            	        ResultSet resultado = consultarBaseDeDatos(consulta);

            	        // Crear un nuevo DocumentBuilder para construir el documento XML
            	        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            	        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            	        Document document = documentBuilder.newDocument();

            	        //DAR RESULTSET GENERICO
            	     
            	        Element rsElemnt = document.createElement("rs");
            	        Element rowElement = document.createElement("row");

            	       
            	        
            	        for(int i = 1; i< resultado.get)
            	        resultado.getArray(1);
            	      
            	        document.appendChild(stockElement);

            	     
            	        puerto.escribirMensaje(document);

            	    } catch (Exception ex) {
            	        // Manejo de errores
            	        Logger.getLogger(ConectorDB.class.getName()).log(Level.SEVERE, null, ex);
            	    }
            	}
            } else {
                System.out.println("No se encontraron consultas en el mensaje");
            }
        }
    }

    private String[] extraerConsulta(Document document, String xpathConsulta) {
        try {
            XPathFactory xpathFactory = XPathFactory.newInstance();
            XPath xpath = xpathFactory.newXPath();
            XPathExpression expr = xpath.compile(xpathConsulta);
            NodeList nodeList = (NodeList) expr.evaluate(document, XPathConstants.NODESET);
            
            String[] names = new String[nodeList.getLength()];

            for (int i = 0; i < nodeList.getLength(); i++) {
                Element element = (Element) nodeList.item(i);
                names[i] = element.getTextContent();

            }

            return names;
        } catch (XPathExpressionException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    private ResultSet consultarBaseDeDatos(String consulta) {
       
        if (consulta == null || consulta.trim().isEmpty()) {
            System.out.println("Consulta SQL vacía o nula");
            return null;
        }


        try (PreparedStatement stmt = connection.prepareStatement(consulta)) {
            ResultSet rs = stmt.executeQuery();

             return rs;
          
         
        } catch (SQLException e) {
           
            System.err.println("Error al ejecutar la consulta: " + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }
}

