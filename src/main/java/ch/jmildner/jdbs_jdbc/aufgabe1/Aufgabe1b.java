package ch.jmildner.jdbs_jdbc.aufgabe1;

import ch.jmildner.tools.MyDbTools;
import ch.jmildner.tools.MyTools;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Aufgabe1b
{

    public static void main(String[] args) throws Exception
    {
        MyTools.uebOut("Start Aufgabe1b", 3);

        /**
         * 1. Schritt - get Connection
         */
        Connection c = DriverManager.getConnection(MyDbTools.getUrl("POSTGRES"));

        /**
         * 2. Schritt - create Statement
         */
        Statement s = c.createStatement();

        /**
         * 3. Schritt - execute SQL-Statements
         *
         * mehrere moeglich
         */
        try
        {
            s.execute("drop table person1b");
        }
        catch (SQLException e)
        {
            // die table muss nicht vorhanden sein
        }

        s.execute("create table person1b "
                + "(id int primary key, name varchar(20), addr varchar(20))");

        String sql = "insert into person1b values(?,?,?)";

        PreparedStatement ps = c.prepareStatement(sql);

        for (int i = 1; i <= 20; i++)
        {
            ps.setInt(1, i);
            ps.setString(2, "name-" + (int) (1000+Math.random() * 1000));
            ps.setString(3, "addr-" + (int) (1000+Math.random() * 1000));
            ps.execute();
        }
        ps.close();

        s.execute("select id,name,addr from person1b  order by name");

        /**
         * 4. Schritt - show Result
         *
         * nur wenn Schritt 3 ein ResultSet liefert
         */
        ResultSet rs = s.getResultSet();

        while (rs.next())
        {
            System.out.printf("%5d      %-20s %-20s %n",
                    rs.getInt(1), rs.getString(2), rs.getString(3));
        }

        rs.close();

        /**
         *
         * 5. Schritt - close Statement, Connection
         *
         * Jeder dieser DB-Befehle kann eine SQLException werfen.
         *
         * Wenn eine SQLException geworfen wird, koennen die close()-Befehle
         * nicht aufgerufen werden.
         *
         * Loesung: try-with-Resource (siehe naechste Aufgabe1c).
         *
         */
        s.close();
        c.close();

        MyTools.untOut("Stopp Aufgabe1b", 3);
    }
}
