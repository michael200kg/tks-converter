package saver;

import model.Person;
import model.Tks;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

public class OracleSaver {
    public void save(List<Tks> tksList) {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con = DriverManager.getConnection(
                    "jdbc:oracle:thin:@localhost:1521:orcl", "DUTYMANAGER_MICHAEL", "DUTYMANAGER_MICHAEL");

            Statement statement = con.createStatement();
            statement.executeUpdate("delete from convert_tks");
            statement.close();

            for (Tks tks : tksList) {

                for (Person person : tks.getPersons()) {

                    PreparedStatement stmt = con.prepareStatement("insert into convert_tks (tks,email,fio) values (?,?,?)");
                    stmt.setString(1, tks.getName());
                    stmt.setString(2, person.getEmail());
                    stmt.setString(3, person.getFio());

                    stmt.executeUpdate();
                    ;
                    stmt.close();
                }
            }
            con.close();

        } catch (Exception e) {
            System.out.println("ORACLE ERROR: " + e);
        }

    }
}

