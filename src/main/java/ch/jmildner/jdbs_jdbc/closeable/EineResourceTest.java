package ch.jmildner.jdbs_jdbc.closeable;

import ch.jmildner.tools.MyTools;
import java.sql.SQLException;

/**
 * <code>
 *  EineResource1 und EineResource2
 *  implementieren AutoCloseable
 *  und haben eine close() Methode
 *
 *  man kann die Klassen mit try-with-Resource verwenden
 *
 *  try (EineResource1 eineResource1 = new EineResource1(); ....)
 *  {
 *      // tritt hier eine Exception auf, wird die close()-Methode ausgefuehrt
 *  }
 * </code>
 *
 * @author johann
 */
public class EineResourceTest
{

    public static void main(String[] args)
    {
        try
        {
            test1();
        }
        catch (Exception ex)
        {
            System.out.println("exception in test1()");
        }

        try
        {
            test2();
        }
        catch (Exception ex)
        {
            System.out.println("exception in test2()");
        }
    }

    private static void test1()
    {
        MyTools.uebOut("test1", 1);
        EineResource_2 er2;
        try (EineResource_1 er1 = new EineResource_1())
        {
            er2 = new EineResource_2();
            er1.rechnen();
            System.out.println("Stop try-with-Resource 1 !!!");
        }
        er2.rechnen();
        er2.close();

    }

    private static void test2()
    {
        MyTools.uebOut("test2", 1);

        try (EineResource_1 er1 = new EineResource_1();
                EineResource_2 er2 = new EineResource_2())
        {
            System.out.println("Start try-with-Resource !!!");
            er1.rechnen();
            er2.rechnen();

            if ((int) (Math.random() * 2) == 0)
            {
                throw new RuntimeException("xxx");
            }

            System.out.println("Stop try-with-Resource 2 !!!");
        }

    }

}
