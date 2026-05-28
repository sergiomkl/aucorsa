package controller.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBBDD {

    public static final String URL = "jdbc:mysql://localhost:3306/aucorsa";
    public static final String USERNAME = "root";
    public static final String PASSWORD = "root";

    public static Connection getConexion() throws SQLException {
        return DriverManager.getConnection(URL,USERNAME,PASSWORD);

    }

}
