package resources;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseOperations {

    public static void insertarDatos(String art, int stock) {
        String sql = "INSERT INTO CANTIDADES (art, stock) VALUES (?, ?)";
        
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, art);
            pstmt.setInt(2, stock);
            pstmt.executeUpdate();
            System.out.println("Datos insertados correctamente.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}