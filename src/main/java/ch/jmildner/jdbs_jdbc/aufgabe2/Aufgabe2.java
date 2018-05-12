package ch.jmildner.jdbs_jdbc.aufgabe2;

import ch.jmildner.jdbs_jdbc.aufgabe1.Aufgabe1c;
import ch.jmildner.tools.MyTools;
import ch.jmildner.tools.TestDatenTools;
import java.sql.SQLException;
import java.util.List;

public class Aufgabe2
{

    public static void main(String[] args)
    {
        new Aufgabe2().run("H2");
        new Aufgabe2().run("MYSQL");
        new Aufgabe2().run("POSTGRES");
        new Aufgabe2().run("ORACLE");
   }

    public void run(String DB)
    {
        MyTools.uebOut("Start Aufgabe2 run() mit Database " + DB, 2);

        try
        {
            PersonDao dao = new PersonDaoImpl(DB);

            dao.drop();
            dao.create();

            for (int id = 1; id <= 20; id++)
            {
                Person p = new Person(id, TestDatenTools.getNachname(), TestDatenTools.getOrt());
                dao.insert(p);
            }

            showPersonen(dao.getAll());

            Person p5 = dao.getById(5);
            p5.setName("KARL MAY");
            dao.update(p5);

            System.out.printf("deleted by name(schuster): %d%n", dao.deleteByName("schuster"));

            System.out.printf("deleted by name(g*): %d%n", dao.deleteByName("g%"));

            System.out.printf("deleted by id(7): %d%n", dao.deleteById(7));

            showPersonen(dao.getAll());
            showPersonen(dao.getByName(""));
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }

        MyTools.untOut("Stopp Aufgabe2 run() mit Database " + DB, 2);
    }

    private static void showPersonen(List<Person> personen)
    {
        System.out.println();
        System.out.println("PERSONEN");
        System.out.println(Person.header());

        if (personen.isEmpty())
        {
            System.out.println("   --- keine Personen in der Tabelle");
        }
        else
        {
            personen.forEach((p) ->
            {
                System.out.println(p);
            });
        }

        System.out.println();
    }
}
