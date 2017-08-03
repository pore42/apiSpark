package test;


import com.google.gson.annotations.SerializedName;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import java.util.ArrayList;


@Entity
public class Qoodle {

    @Id
    private long id ;
    @SerializedName("title")
    private String saluto = "";

    @SerializedName("description")
    private String description = "";

    @SerializedName("closingDate")
    private String date;

    @SerializedName("qeList")
    private ArrayList<QoodleElement> qeList;

    @SerializedName("voList")
    private ArrayList<Vote> voList;

    public Qoodle(String saluto, String description, String d, ArrayList<QoodleElement> qeList) {
        this.id = 0L;
        this.saluto = saluto;
        this.description = description;
        this.date = d;
        this.qeList = qeList;
        this.voList = new ArrayList<Vote>();
    }

    public Qoodle(String saluto, String description, String d ) {
        this.id = 0L;
        this.saluto = saluto;
        this.description = description;
        this.date = d;
        this.qeList = new ArrayList<>();
    }



    public Qoodle(String s, long i )
    {
        this.saluto = s;
        this.id = i;
    }

    public Qoodle()
    {
        this.saluto = "default value";
        this.id = 0;
    }

    public ArrayList<QoodleElement> getQeList() {
        return qeList;
    }


    public String getDate() {
        return date;
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


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public ArrayList<Vote> getVoList() {
        return voList;
    }
}
