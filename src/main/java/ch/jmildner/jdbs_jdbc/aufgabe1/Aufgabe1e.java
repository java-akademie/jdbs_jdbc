package ch.jmildner.jdbs_jdbc.aufgabe1;

import ch.jmildner.tools.MyDbTools;
import ch.jmildner.tools.MyPoolingDataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.sql.DataSource;

public class Aufgabe1e
{
    private final double MAX_SECONDS = 3;

    public static void main(String[] args) throws SQLException
    {
        new Aufgabe1e().run();
    }

    private void run() throws SQLException
    {
        timeTest("H2");
        timeTest("MYSQL");
       timeTest("POSTGRES");
       timeTest("ORACLE");
    }

    private void timeTest(String DATABASE) throws SQLException 
    {
        System.out.println("\nZEITMESSUNG FUER : " + DATABASE);

        timeTestWithoutPooling(DATABASE);
        timeTestWithPooling(DATABASE);
    }

    private void timeTestWithoutPooling(String DATABASE) throws SQLException 
    {
        long anz = 0;
        long t = (long) (System.currentTimeMillis() + MAX_SECONDS * 1000);

        while (System.currentTimeMillis() < t)
        {
            try (Connection c = DriverManager.getConnection(MyDbTools.getUrl(DATABASE)))
            {
                c.getMetaData().getDatabaseProductName();
                anz++;
            }
        }
        System.out.printf("%nAnzahl getConnections ohne Pool in %4.2f Sekunden: %,10d %n", MAX_SECONDS, anz);
    }

    private void timeTestWithPooling(String DATABASE) throws SQLException 
    {
        final DataSource DS = new MyPoolingDataSource(DATABASE).getDataSource();

        long anz = 0;
        long t = (long) (System.currentTimeMillis() + MAX_SECONDS * 1000);

        while (System.currentTimeMillis() < t)
        {
            try (Connection c = DS.getConnection();)
            {
                c.getMetaData().getDatabaseProductName();
                anz++;
            }
        }
        System.out.printf("%nAnzahl getConnections  mit Pool in %4.2f Sekunden: %,10d %n", MAX_SECONDS, anz);
    }
}
