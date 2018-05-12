package ch.jmildner.jdbs_jdbc.closeable;

public class EineResource_1 implements AutoCloseable
{

    @Override
    public void close()
    {
        System.out.println("\nCLOSE er1 ...........");
    }

    public void rechnen()
    {
        int divisor = (int) (Math.random() * 5);
        System.out.printf("er1.Rechnen: %d / %d = ", 1000, divisor);
        System.out.printf("%d %n", 1000 / divisor);
    }
}
