package ch.jmildner.jdbs_jdbc.aufgabe4;

import ch.jmildner.tools.MyDbTools;
import ch.jmildner.tools.MyPoolingDataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import javax.sql.DataSource;
import ch.jmildner.tools.MyTools;

public class Aufgabe4
{

    private final String DATABASE;
    private final DataSource DS;

    public static void main(String[] args)
    {
        new Aufgabe4("H2").run();
        new Aufgabe4("MYSQL").run();
        new Aufgabe4("POSTGRES").run();
        new Aufgabe4("ORACLE").run();
    }

    public Aufgabe4(final String DB)
    {
        this.DATABASE = DB;
        this.DS = new MyPoolingDataSource(DATABASE).getDataSource();
    }

    private void run()
    {
        MyTools.uebOut("Start Aufgabe4 run() mit DB: " + DATABASE, 2);

        try (Connection c = DS.getConnection())
        {
            drop(c, "besuch");
            drop(c, "teilnehmer");
            drop(c, "veranstaltung");

            create(c, "veranstaltung");
            create(c, "teilnehmer");
            create(c, "besuch");

            insert(c);

            select(c);
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }

        MyTools.untOut("Stopp Aufgabe4 run() mit DB: " + DATABASE, 2);
    }

    private void drop(Connection c, String tableName)
    {
        try (Statement s = c.createStatement())
        {
            s.execute("drop table " + tableName);
        }
        catch (Exception e)
        {
            // table muss nicht vorhanden sein
        }
    }

    private void create(Connection c, String tableName)
            throws SQLException
    {
        try (Statement s = c.createStatement())
        {
            switch (tableName)
            {
                case "veranstaltung":
                    s.execute("create table veranstaltung "
                            + "(id int not null primary key, "
                            + " titel varchar(20))");
                    break;

                case "teilnehmer":
                    s.execute("create table teilnehmer "
                            + "(id int not null primary key, "
                            + " name varchar(20))");
                    break;

                case "besuch":
                    s.execute("create table besuch "
                            + "(id_Veranstaltung int not null, "
                            + " id_Teilnehmer int not null, "
                            + " primary key"
                            + "    (id_Veranstaltung, id_Teilnehmer), "
                            + " foreign key (id_Veranstaltung) "
                            + "    references veranstaltung(id), "
                            + " foreign key (id_Teilnehmer) "
                            + "    references Teilnehmer(id))");
                    break;
            }
        }
    }

    private void insert(Connection c) throws SQLException
    {
        try (Statement s = c.createStatement())
        {
            s.execute("insert into teilnehmer " + "values(101,'hugo')");
            s.execute("insert into teilnehmer " + "values(102,'hans')");
            s.execute("insert into teilnehmer " + "values(103,'kurt')");

            s.execute("insert into veranstaltung "
                    + "values(201,'rolling stones')");
            s.execute("insert into veranstaltung "
                    + "values(202,'deep purple')");
            s.execute("insert into veranstaltung "
                    + "values(203,'dire staits')");

            s.execute("insert into besuch " + "values(201,101)");
            s.execute("insert into besuch " + "values(202,101)");
            s.execute("insert into besuch " + "values(203,101)");
            s.execute("insert into besuch " + "values(202,102)");
            s.execute("insert into besuch " + "values(203,103)");
        }
    }

    private void select(Connection c) throws SQLException
    {
        MyTools.uebOut("TEILNEHMER", 2);
        MyDbTools.select(c, "select id, name from teilnehmer t", 3);

        MyTools.uebOut("VERANSTALTUNG", 2);
        MyDbTools.select(c, "select id, titel from veranstaltung v", 3);

        MyTools.uebOut("BESUCH", 2);
        MyDbTools.select(c, "select     "
                + " v.id as veranstaltung_id, "
                + " v.titel as veranstaltung_titel, "
                + " t.id as teilnehmer_id, "
                + " t.name as teilnehmer_name "
                + " from veranstaltung v, teilnehmer t, besuch b "
                + " where v.id = b.id_veranstaltung and t.id = b.id_teilnehmer ", 3);
    }

}
