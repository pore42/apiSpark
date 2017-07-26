package test;


import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

@Entity
public class Hello {

    private static long globalId = 0;
    @Id
    private long id ;
    private String saluto = "";

    public Hello(String s )
    {
        this.saluto = s;
        this.id = globalId++;
    }

    public Hello()
    {
        this.saluto = "default value";
        this.id = globalId++;
    }



    public String getSaluto() {
        return saluto;
    }

    public void setSaluto(String saluto) {
        this.saluto = saluto;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


}
