package DAO;

import java.sql.*;

public class DAOFactory {

    public static final String URL = "jdbc:mysql://localhost:3306/Data?useSSL=false";
    
    public DAOFactory() {
        createConnection();
    }

    public static Connection createConnection() {
        try {
            return DriverManager.getConnection(URL, "root", "root");
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public SessionDAO getSessionDAO() {
        return new SessionDAO();
    }
}
