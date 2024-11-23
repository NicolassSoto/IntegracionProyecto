<<<<<<< HEAD
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
=======
package resources;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseConnection {

    private static final String db_url = "jdbc:h2:./data/bdCafe"; 
    private static final String user = "sa";
    private static final String passwd = "";

    public static Connection connect(){
        try {
            return DriverManager.getConnection(db_url, user, passwd);
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
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
>>>>>>> branch 'master' of https://Cristiangb02@github.com/NicolassSoto/IntegracionProyecto.git
