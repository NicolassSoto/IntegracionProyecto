package conexion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
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

                        ResultSet rs = consultarBaseDeDatos(consulta);
                        ResultSetMetaData metaData = rs.getMetaData();
                        int columnCount = metaData.getColumnCount();

                        // Crear un nuevo DocumentBuilder para construir el documento XML
                        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
                        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
                        Document document = documentBuilder.newDocument();

                        //DAR RESULTSET GENERICO
                        Element rsElemnt = document.createElement("rs");
                        Element rowElement = document.createElement("row");

                        while (rs.next()) {
                            // Iterar sobre las columnas de cada fila
                            for (int i = 1; i <= columnCount; i++) {
                                String columnName = metaData.getColumnName(i); // Nombre de la columna
                                Element column = document.createElement(columnName);
                                String value = (String) rs.getObject(i); // Valor de la columna
                                column.setTextContent(value);
                                rowElement.appendChild(column);
                            }
                            rsElemnt.appendChild(rowElement);
                        }
                        document.appendChild(rsElemnt);

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
