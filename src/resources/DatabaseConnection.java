package resources;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {

    private static final String db_url = "jdbc:h2:./data/bdCafe"; 
    private static final String user = "sa";
    private static final String passwd = "";

    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(db_url, user, passwd);
    }

    public static void iniciarBD() {
        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            // Crear tabla de ejemplo si no existe
            String sql = "CREATE TABLE IF NOT EXISTS CANTIDADES ("
                       + "id INT PRIMARY KEY,"
                       + "art VARCHAR(255),"
                       + "stock INT)";
            stmt.executeUpdate(sql);
            System.out.println("Base de datos y tabla creadas correctamente.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}