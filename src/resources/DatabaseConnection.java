package resources;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {

    private static final String DB_URL = "jdbc:h2:./data/bdCafe"; 
    private static final String USER = "sa";
    private static final String PASSWORD = "";

    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASSWORD);
    }

    public static void initializeDatabase() {
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