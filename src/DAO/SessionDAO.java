package DAO;

import javax.sql.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

public class SessionDAO {

    private static final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final String DELETE = "DELETE FROM sessions WHERE id=?";
    private static final String FIND_ALL = "SELECT * FROM sessions ORDER BY duration";
    private static final String FIND_BY_ID = "SELECT * FROM sessions WHERE id=?";
    private static final String FIND_BY_TYPE = "SELECT * FROM sessions WHERE type=?";

    public SessionDAO() {
        // инициализация
    }

    public List<Session> findByType(String type) {

        List<Session> sessions = new ArrayList<>();
        try {
            Connection conn = DAOFactory.createConnection();
            PreparedStatement ps = conn.prepareStatement(FIND_BY_TYPE);
            ps.setString(1, type);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Session sss = new Session(UUID.fromString(rs.getString("id")), rs.getString("type"), rs.getLong("duration"));
                sessions.add(sss);
            }
            ps.close();
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return sessions;
    }

    public List<Session> findAll() {
        boolean i = false;
        List<Session> sessions = new ArrayList<>();
        try {
            Connection conn = DAOFactory.createConnection();
            PreparedStatement ps = conn.prepareStatement(FIND_ALL);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Session sss = new Session(UUID.fromString(rs.getString("id")), rs.getString("type"), rs.getLong("duration"));
                sessions.add(sss);
            }
            ps.close();
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return sessions;
    }

    public boolean insertSession(Session sss) {
        boolean i = false;
        try {
            Connection conn = DAOFactory.createConnection();
            PreparedStatement ps = conn.prepareStatement("INSERT INTO sessions VALUES (?, ?, ?)");
            ps.setString(1, sss.getId().toString());
            ps.setString(2, sss.getType());
            ps.setString(3, sss.getDuration().toString());
            
            i = (ps.executeUpdate() != 0);
            conn.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        System.out.print(i);
        return i;
    }

    public boolean deleteSession(Session sss) {
        boolean i = false;
        try {
            Connection conn = DAOFactory.createConnection();
            PreparedStatement ps = conn.prepareStatement(DELETE);
            ps.setString(1, sss.getId().toString());

            i = (ps.executeUpdate() != 0);
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.print(i);
        return i;
    }

    public Session findByid(UUID id) {
        Session sss = null;
        try {
            Connection conn = DAOFactory.createConnection();
            PreparedStatement ps = conn.prepareStatement(FIND_BY_ID);
            ps.setString(1, id.toString());
            ResultSet rs = ps.executeQuery();
            System.out.println(rs);
            rs.next();
            sss = new Session(UUID.fromString(rs.getString("id")), rs.getString("type"), rs.getLong("duration"));

            ps.close();
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return sss;
    }

    public Session findSession() {
        return null;
        // Реализовать здесь операцию поиска клиента, используя
        // предоставленные значения аргументов в качестве критерия поиска.
        // Возвратить объект Transfer Object при успешном поиске,
        // null или ошибку, если клиент не найден.
    }

    public boolean updateSession(Session sss) {

        boolean i = false;
        Connection conn = DAOFactory.createConnection();
        try {
            PreparedStatement ps = conn.prepareStatement("UPDATE sessions SET type=?, duration=? WHERE id=?");

            ps.setString(3, sss.getId().toString());
            ps.setString(1, sss.getType());
            ps.setString(2, sss.getDuration().toString());

            i = (ps.executeUpdate() != 0);
            conn.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        System.out.print(i);
        return i;
    }

    public RowSet selectSessionsRS() {
        return null;
        // Реализовать здесь операцию выбора клиентов,
        // используя предоставленный критерий.
        // Возвратить RowSet.
    }

    public Collection selectSessionsTO() {
        return null;
        // Реализовать здесь операцию выбора клиентов,
        // используя предоставленный критерий.
        // В качестве альтернативы, реализовать возврат
        // коллекции объектов Transfer Object.
    }
}
