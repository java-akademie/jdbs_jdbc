package ch.jmildner.jdbs_jdbc.aufgabe2;

public class Person
{
    private Integer id;
    private String name;
    private String addr;

    public Person(Integer id, String name, String addr)
    {
    	this.id = id;
	this.name = name;
        this.addr = addr;
    }

    public 	Integer 	getId	 	()	{return id;	}
    public 	String		getName 	()	{return name;	}
    public 	String 		getAddr	 	()	{return addr;	}

    public 	void setId	(Integer id)	{this.id    = id;	}
    public 	void setName 	(String name)	{this.name  = name;	}
    public 	void setAddr   	(String addr)	{this.addr  = addr;	}

    @Override
    public String toString()
    {
        return String.format("%5d  %-20s  %-20s ", id, name, addr);
    } 

    public static String header()
    {
        return String.format("%5s  %-20s  %-20s", "ID", "NAME", "ADRESSE");
    }
}
