package ch.jmildner.jdbs_jdbc.aufgabe1;

import ch.jmildner.tools.MyDbTools;
import ch.jmildner.tools.MyTools;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class Aufgabe1c
{

    public static void main(String[] args)
    {
        new Aufgabe1c().run("H2");
        new Aufgabe1c().run("MYSQL");
        new Aufgabe1c().run("POSTGRES");
        new Aufgabe1c().run("ORACLE");
    }

    private void run(final String DB)
    {
        MyTools.uebOut("Start Aufgabe1c run() mit DB: " + DB, 2);

        try (Connection c = DriverManager.getConnection(MyDbTools.getUrl(DB)))
        {
            drop(c);
            create(c);
            insert(c);
            select(c);
            selectCount(c);
            update(c);
            delete(c);
            select(c);
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }

        MyTools.untOut("Stopp Aufgabe1c run() mit DB: " + DB, 2);
    }

    private void delete(Connection c) throws SQLException
    {
        try (Statement s = c.createStatement())
        {
            System.out.printf("%nAnzahl deleted: %d %n",
                    s.executeUpdate("delete from person1c where addr like 'addr-1%'"));
        }
    }

    private void update(Connection c) throws SQLException
    {
        try (Statement s = c.createStatement())
        {
            System.out.printf("%nAnzahl updated: %d %n",
                    s.executeUpdate("update person1c set name='max' where id = 5"));
        }
    }

    private void selectCount(Connection c) throws SQLException
    {
        MyDbTools.select(c, "select count(*) from person1c");
    }

    private void select(Connection c) throws SQLException
    {
        MyDbTools.select(c,
                "select id, name, addr from person1c  order by name");
    }

    private void drop(Connection c)
    {
        try (Statement s = c.createStatement())
        {
            s.execute("drop table person1c");
        }
        catch (SQLException e)
        {
            // table muss nicht vorhanden sein
        }
    }

    private void create(Connection c) throws SQLException
    {
        try (Statement s = c.createStatement())
        {
            s.execute("create table person1c "
                    + "(id int primary key, name varchar(20), addr varchar(20))");
        }
    }

    private void insert(Connection c) throws SQLException
    {
        try (PreparedStatement ps = c.prepareStatement(
                "insert into person1c values(?,?,?)");)
        {
            for (int i = 1; i <= 10; i++)
            {
                ps.setInt(1, i);
                ps.setString(2, "name-" + (int) (1000 + Math.random() * 1000));
                ps.setString(3, "addr-" + (int) (1000 + Math.random() * 1000));
                ps.execute();
            }
        }
    }
}
