package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

class ConnectionFactory {
    public static final String URL = "jdbc:mysql://localhost:3306/testdb";
    public static final String Session = "Session";
    public static final String Time = "Time";

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, Session, Time);
        } catch (SQLException ex) {
            throw new RuntimeException("Error connecting to the database", ex);
        }
    }
}

public class Session_DAO {
    
    private Session extractSessionFromResultSet(ResultSet rs) throws SQLException {
        Session Session = new Session();
        Session.setId( rs.getInt("id") );
        Session.setType( rs.getString("Type") );
        Session.setTime( rs.getDate("Time") );
        Session.setDuration( rs.getInt("Duration") );
        return Session;
    }

    public Session getSession(int id) {
        Connection connection = ConnectionFactory.getConnection();
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Session WHERE id=" + id);
            if(rs.next())
            {
                return extractSessionFromResultSet(rs);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public Session getSessionByTime(Date time) {
        Connection connection = ConnectionFactory.getConnection();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM Session WHERE Time=?");
            ps.setDate(1, time);
            ResultSet rs = ps.executeQuery();
            if(rs.next())
            {
                return extractSessionFromResultSet(rs);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public Set getAllSessions() {
        Connection connection = ConnectionFactory.getConnection();
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Session");
            Set Sessions = new HashSet();
            while(rs.next())
            {
                Session Session = extractSessionFromResultSet(rs);
                Sessions.add(Session);
            }
            return Sessions;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public boolean insertSession(Session Session) {
        Connection connection = ConnectionFactory.getConnection();
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO Session VALUES (NULL, ?, ?, ?)");
            ps.setString(1, Session.getType());
            ps.setDate(2, Date.valueOf(Session.getTime().toString()));
            ps.setInt(3, Session.getDuration());
            int i = ps.executeUpdate();
            if(i == 1) {
                return true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public boolean updateSession(Session Session) {
        Connection connection = ConnectionFactory.getConnection();
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE Session SET Type=?, Time=?, Duration=? WHERE id=?");
            ps.setString(1, Session.getType());
            ps.setDate(2, Date.valueOf(Session.getTime().toString()));
            ps.setInt(3, Session.getDuration());
            ps.setInt(4, Session.getId());
            int i = ps.executeUpdate();
            if(i == 1) {
                return true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public boolean deleteSession(int id) {
        Connection connection = ConnectionFactory.getConnection();
        try {
            Statement stmt = connection.createStatement();
            int i = stmt.executeUpdate("DELETE FROM Session WHERE id=" + id);
            if(i == 1) {
                return true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }
}
