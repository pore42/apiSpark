package test;


import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

@Entity
public class Hello {

    @Id
    private long id ;
    private String saluto = "";

    public Hello(String saluto) {
        this.saluto = saluto;
    }

    public Hello(String s, long i )
    {
        this.saluto = s;
        this.id = i;
    }

    public Hello()
    {
        this.saluto = "default value";
        this.id = 0;
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
