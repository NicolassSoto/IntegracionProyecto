package Inicio;

import resources.DatabaseConnection;
import resources.DatabaseOperations;

public class GeneradorComandas {

	public static void main(String[] args) {
		DatabaseConnection.initializeDatabase();
		DatabaseOperations.insertExampleData("cafe",2);
		DatabaseOperations.insertExampleData("cocacola",7);
	}
}
