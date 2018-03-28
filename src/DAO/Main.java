package DAO;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        DAOFactory MySqlFactory = new DAOFactory();

        // Создать DAO
        SessionDAO custDAO = MySqlFactory.getSessionDAO();

        // создать нового клиента
        Session Session1 = new Session("hui");
        Session Session2 = new Session("s1ss");

        Session2.setDuration(212152121);
        //custDAO.insertSession(Session1);

        //custDAO.deleteSession(Session2);
        custDAO.updateSession(Session2);
        for (Session sss : custDAO.findAll()) {
            System.out.println(sss.toString());
        }
        //custDAO.updateSession(Session2);
        for (Session sss : custDAO.findByType("sasdss")) {
            System.out.println(sss.toString());
        }
        System.out.println(custDAO.findByid(UUID.fromString("95fe6701-af65-440e-bbce-a00732a46b32")).toString());
        System.out.println(custDAO.findByid(UUID.fromString("95fe6701-af65-440e-bbce-a00732a46b32")).toString());
        // найти объект customer. Получить объект Transfer Object.
        //Session cust = SessionDAO.findSession();

    }
}
