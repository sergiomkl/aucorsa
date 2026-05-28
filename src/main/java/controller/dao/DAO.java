package controller.dao;

import controller.connection.ConexionBBDD;

import java.sql.Connection;
import java.sql.SQLException;

public class DAO {
    protected static Connection con;

    static {
        try {
            con = ConexionBBDD.getConexion();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
