package resources;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager {

	private Connection connection;

    /**
     * Inicializa la base de datos en memoria y crea la estructura necesaria.
     */
	public void initializeDatabase() {
        String jdbcUrl = "jdbc:h2:mem:testdb"; // Base de datos en memoria
        String user = "sa";
        String password = "";

        try {
            // Establecer conexión
            connection = DriverManager.getConnection(jdbcUrl, user, password);
            System.out.println("Conexión exitosa a la base de datos en memoria.");

            // Crear tabla BEBIDA y agregar datos iniciales
            try (Statement statement = connection.createStatement()) {
                String createTableSQL = """
                    CREATE TABLE BEBIDA (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        nombre VARCHAR(50) NOT NULL,
                        stock INT NOT NULL
                    );
                """;

                statement.execute(createTableSQL);
                System.out.println("Tabla BEBIDA creada exitosamente.");

                String insertDataSQL = """
                    INSERT INTO BEBIDA (nombre, stock) VALUES 
                    ('te', 0),
                    ('agua', 50),
                    ('coca-cola', 30),
                    ('guarana', 15),
                    ('fanta', 25),
                    ('tonica', 10);
                """;

                statement.execute(insertDataSQL);
                System.out.println("Datos iniciales insertados en la tabla BEBIDA.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Obtiene la conexión actual a la base de datos.
     *
     * @return la conexión JDBC activa.
     */
    public Connection getConnection() {
        return connection;
    }

    /**
     * Cierra la conexión a la base de datos.
     */
    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Conexión cerrada correctamente.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
	
}
