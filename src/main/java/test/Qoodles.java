package test;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import java.util.Date;

@Entity
public class Qoodles extends Insertable{


    @Id
    private long qoodlesId ;
    private String title;

    private String description;

    private int partecipants;
    private Date closingDate;

    public Qoodles() {

        this.qoodlesId = 0L;
        this.title = "defTitle";
        this.description = "defDescription";
        this.partecipants = 0;
        this.closingDate = new Date();
    }

    public Qoodles( String title, String description, int partecipants, Date closingDate) {
        this.qoodlesId = 0;
        this.title = title;
        this.description = description;
        this.partecipants = partecipants;
        this.closingDate = closingDate;
    }




    public Qoodles(long qoodlesId, String title, String description, int partecipants, Date closingDate) {
        qoodlesId = qoodlesId;
        this.title = title;
        this.description = description;
        this.partecipants = partecipants;
        this.closingDate = closingDate;
    }


    public long getQoodlesId() {
        return qoodlesId;
    }

    public void setQoodlesId(long qoodlesId) {
        this.qoodlesId = qoodlesId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getdescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPartecipants() {
        return partecipants;
    }

    public void setPartecipants(int partecipants) {
        this.partecipants = partecipants;
    }

    public Date getClosingDate() {
        return closingDate;
    }

    public void setClosingDate(Date closingDate) {
        this.closingDate = closingDate;
    }


    @Override
    public void insert( String name, Datastore ds) {
        long nuovoId = inserisci(name, ds);
            this.setQoodlesId(nuovoId);
            ds.save(this);

    }
}
