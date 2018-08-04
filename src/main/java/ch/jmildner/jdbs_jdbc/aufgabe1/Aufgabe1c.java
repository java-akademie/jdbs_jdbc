package ch.jmildner.jdbs_jdbc.aufgabe1;

import ch.jmildner.tools.KzDbDaten;
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
        MyTools.uebOut("Start Aufgabe1c", 3);

        new Aufgabe1c().run();
    }

    private void run()
    {
        try (Connection c = DriverManager.getConnection(MyDbTools.getUrl()))
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
    }

    private void delete(Connection c) throws SQLException
    {
        try (Statement s = c.createStatement())
        {
            String sql = "delete from person1c where addr like 'addr-1%'";
            MyTools.uebOut(sql, 1);

            long anz = s.executeUpdate(sql);

            System.out.printf("%nAnzahl deleted: %d %n", anz);
        }
    }

    private void update(Connection c) throws SQLException
    {
        try (Statement s = c.createStatement())
        {
            String sql = "update person1c set name='max' where id = 5";
            MyTools.uebOut(sql, 1);

            long anz = s.executeUpdate(sql);

            System.out.printf("%nAnzahl updated: %d %n", anz);
        }
    }

    private void selectCount(Connection c) throws SQLException
    {
        String sql = "select count(*) from person1c";
        MyTools.uebOut(sql, 1);

        MyDbTools.select(c, sql, KzDbDaten.METADATEN_UND_DATEN);
    }

    private void select(Connection c) throws SQLException
    {
        String sql = "select id, name, addr from person1c  order by name";
        MyTools.uebOut(sql, 1);

        MyDbTools.select(c, sql, KzDbDaten.METADATEN_UND_DATEN);
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
            String sql = "create table person1c (id int primary key, name varchar(20), addr varchar(20))";
            MyTools.uebOut(sql, 1);

            s.execute(sql);
        }
    }

    private void insert(Connection c) throws SQLException
    {
        String sql = "insert into person1c values(?,?,?)";
        MyTools.uebOut(sql, 1);

        try (PreparedStatement ps = c.prepareStatement(sql))
        {
            for (int i = 1; i <= 10; i++)
            {
                ps.setInt(1, i);
                ps.setString(2, "name-" + (int) (1000 + Math.random() * 1000));
                ps.setString(3, "addr-" + (int) (1000 + Math.random() * 1000));
                
                System.out.println(ps + "");
                
                ps.execute();
            }
        }
    }


}
