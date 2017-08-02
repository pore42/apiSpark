package test;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import java.util.ArrayList;

@Entity
public class Qoodle extends Insertable{

    @Id
    private long qoodleId;
    private String title;
    private String description;
    private String closingDate;
    private ArrayList<QoodleElement> qeList;
    private ArrayList<Vote> qvotes;

    public Qoodle() {
        this.qoodleId = -1L;
        this.title = "";
        this.description = "";
        this.closingDate = "";
        this.qeList = null;
        this.qvotes = null;

    }

    public Qoodle(String title, String description, String closingDate, ArrayList<QoodleElement> qeList) {
        this.qoodleId = 0;
        this.title = title;
        this.description = description;
        this.closingDate = closingDate;
        this.qeList = qeList;
    }

    public Qoodle(long qoodleId, String title, String description, String closingDate, ArrayList<QoodleElement> qeList, ArrayList<Vote> qvotes) {
        this.qoodleId = qoodleId;
        this.title = title;
        this.description = description;
        this.closingDate = closingDate;
        this.qeList = qeList;
        this.qvotes = qvotes;
    }

    public Qoodle(long qoodleId, String title, String description, String closingDate, ArrayList<QoodleElement> qeList) {
        this.qoodleId = qoodleId;
        this.title = title;
        this.description = description;
        this.closingDate = closingDate;
        this.qeList = qeList;
    }




    @Override
    public void insert(String name, Datastore ds) {
        long nuovoId = this.inserisci(name, ds);
       // this.setQoodleViewId(nuovoId);
        ds.save(this);

    }
}
