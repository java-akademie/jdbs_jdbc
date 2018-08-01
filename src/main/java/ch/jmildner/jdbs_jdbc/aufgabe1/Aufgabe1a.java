package ch.jmildner.jdbs_jdbc.aufgabe1;

import ch.jmildner.tools.MyDbTools;
import ch.jmildner.tools.MyTools;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Aufgabe1a
{
    private static final String DB = "POSTGRES";

    public static void main(String[] args)
    {
        try
        {
            new Aufgabe1a().test1(DB);
        }
        catch (SQLException ex)
        {
            Logger.getLogger(Aufgabe1a.class.getName()).log(Level.SEVERE, null, ex);
        }

        new Aufgabe1a().test2(DB);
    }

    private void test1(final String DB) throws SQLException
    {
        MyTools.uebOut("Start Aufgabe1a test1() mit DB " + DB, 2);

        /**
         * 1. Schritt - get Connection
         */
        Connection c = DriverManager.getConnection(MyDbTools.getUrl(DB));

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
            s.execute("drop table person1a");
        }
        catch (SQLException e)
        {
            System.out.println("Fehler: " + e.getMessage());
            // die Tabelle muss nicht vorhanden sein
            // das heisst, das Programm soll weiterlaufen
        }

        s.execute("create table person1a (id int primary key, name varchar(20), addr varchar(20))");

        for (int i = 1; i <= 20; i++)
        {
            s.execute("insert into person1a values(" + i + ",'hugo-" + i + "','addr-" + i + "')");
        }

        s.execute("select id,name,addr from person1a order by id");

        /**
         *
         * 4. Schritt - show Result
         *
         * nur wenn Schritt 3 ein ResultSet liefert
         *
         */
        ResultSet rs = s.getResultSet();

        while (rs.next())
        {
            System.out.printf("%5d      %-20s %-20s %n", rs.getInt(1), rs.getString(2), rs.getString(3));
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
         * Loesung: try-with-Resource (siehe test2()).
         *
         */
        s.close();
        c.close();

        MyTools.untOut("Stopp Aufgabe1a test1() mit DB " + DB, 2);
    }

    private void test2(final String DB)
    {

        try (Connection c = DriverManager.getConnection(MyDbTools.getUrl(DB)))
        {
            MyTools.uebOut("Start Aufgabe1a test2() mit DB " + DB, 2);

            try (Statement s = c.createStatement())
            {
                s.execute("drop table person1a");
            }
            catch (SQLException e)
            {
                System.out.println("Fehler: " + e.getMessage());
                // die Tabelle muss nicht vorhanden sein
                // das heisst, das Programm soll weiterlaufen
            }

            try (Statement s = c.createStatement())
            {
                s.execute("create table person1a (id int primary key, name varchar(20), addr varchar(20))");

                for (int i = 1; i <= 20; i++)
                {
                    s.execute("insert into person1a values(" + i + ",'hugo-" + i + "','addr-" + i + "')");
                }
            }

            try (Statement s = c.createStatement())
            {
                try (ResultSet rs = s.executeQuery("select id,name,addr from person1a order by id"))
                {
                    while (rs.next())
                    {
                        System.out.printf("%5d      %-20s %-20s %n", rs.getInt(1), rs.getString(2), rs.getString(3));
                    }
                }
            }

            MyTools.untOut("Stopp Aufgabe1a test2() mit DB " + DB, 2);
        }
        catch (SQLException ex)
        {
            Logger.getLogger(Aufgabe1a.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
