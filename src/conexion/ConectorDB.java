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
import resources.DatabaseConnection;

public class ConectorDB extends Conector {

    private Connection connection;

    public ConectorDB(Puerto puerto) {
        super(puerto);

        this.connection = new DatabaseConnection().connect();
    }

    public void serveDrinks() {

        while (!getPuerto().getSlot().getListaMensajes().isEmpty()) {
            String idCorrelacion = getPuerto().getSlot().getMensaje().getIdMensaje();
            String[] consultas = extraerConsulta(getPuerto().getSlot().desencolar().getContenido(), "//consulta_sql");

            if (consultas != null && consultas.length > 0) {
                for (String consulta : consultas) {
                    try {
                        boolean disponible = consultarDisponibilidadEnBaseDeDatos(consulta);

                        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
                        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
                        Document document = documentBuilder.newDocument();

                        Element drinkElement = document.createElement("stock");
                        if (disponible) {
                            drinkElement.setTextContent("Si");
                            getPuerto().setIdCorrelacion(idCorrelacion);
                            getPuerto().escribirMensaje(document);
                        } else {
                            drinkElement.setTextContent("No");
                            getPuerto().setIdCorrelacion(idCorrelacion);
                            getPuerto().escribirMensaje(document);
                        }

                    } catch (ParserConfigurationException ex) {
                        Logger.getLogger(ConectorDB.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            } else {
                System.out.println("No se encontraron consultas en el mensaje");
                // Manejar la situación cuando no se encuentran nombres en el mensaje
            }
        }
    }

    private String[] extraerConsulta(Document document, String xpathConsulta) {
        try {
            NodeList nodeList = document.getElementsByTagName(xpathConsulta);
            String[] names = new String[nodeList.getLength()];

            for (int i = 0; i < nodeList.getLength(); i++) {
                Element element = (Element) nodeList.item(i);
                names[i] = element.getTextContent();
            }

            return names;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    private boolean consultarDisponibilidadEnBaseDeDatos(String consulta) {

        try (PreparedStatement stmt = connection.prepareStatement(consulta)) {
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int stock = rs.getInt("Stock");

                return stock > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
