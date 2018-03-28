package DAO;

import java.sql.*;

public class DAOFactory {

    public static final String DRIVER = "COM.MySql.core.RmiJdbcDriver";
    public static final String DBURL = "jdbc:MySql:rmi://localhost:1099/CoreJ2EEDB";
    public static final String URL = "jdbc:mysql://localhost:3306/testdb?useSSL=false";
    public static final String Session = "Session";
    public static final String Time = "Time";
    private final String user = "root";//Логин пользователя
    private final String password = "";//Пароль пользователя
    private final String url = "jdbc:mysql://localhost:3306/daotalk";//URL адрес
    private final String driver = "com.mysql.jdbc.Driver";//Имя драйвера

    public DAOFactory() {
        createConnection();
    }

    public static Connection createConnection() {
        try {
            return DriverManager.getConnection(URL, "root", "root");
        } catch (SQLException ex) {
            throw new RuntimeException("Error connecting to the database", ex);
        }
        // Использовать DRIVER и DBURL для создания соединения
        // Рекомендовать реализацию/использование пула соединений
    }

    public SessionDAO getSessionDAO() {
        return new SessionDAO();
    }
}
