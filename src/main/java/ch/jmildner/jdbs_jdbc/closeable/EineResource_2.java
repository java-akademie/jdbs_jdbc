package ch.jmildner.jdbs_jdbc.closeable;



public class EineResource_2 implements AutoCloseable
{

    @Override
    public void close()
    {
        System.out.println("\nCLOSE er2 ...........");
    }

    
    public void rechnen() 
    {
        int divisor = (int) (Math.random() * 5);
        
        if(divisor==0)
            throw new RuntimeException("Division durch 0 nicht erlaubt");
        
        System.out.printf("er2.Rechnen: %d / %d = ", 1000, divisor);
        System.out.printf("%d %n", 1000 / divisor);
    }
}
