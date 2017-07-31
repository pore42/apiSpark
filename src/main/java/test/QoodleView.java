package test;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import java.util.ArrayList;
import java.util.Date;

@Entity
public class QoodleView extends Insertable{

    @Id
    private long qoodleViewId;
    private String title;
    private String description;
    private String chiusura;
    private ArrayList<QoodleElement> ele;

    public QoodleView(String title, String description, String chiusura, ArrayList<QoodleElement> ele) {
        this.qoodleViewId = 0;
        this.title = title;
        this.description = description;
        this.chiusura = chiusura;
        this.ele = ele;
    }

    public QoodleView(long qoodleViewId, String title, String description, String chiusura, ArrayList<QoodleElement> ele) {
        this.qoodleViewId = qoodleViewId;
        this.title = title;
        this.description = description;
        this.chiusura = chiusura;
        this.ele = ele;
    }

    public QoodleView() {
        this.qoodleViewId = 0;
        this.title = "";
        this.description = "";
        this.chiusura = "";
        this.ele = new ArrayList<QoodleElement>();
    }


    public QoodleView(QoodleView q , long id ) {
        this.qoodleViewId = id;
        this.title = q.title;
        this.description = q.description;
        this.chiusura = q.chiusura;
        this.ele = q.ele;
    }



    public long getQoodleViewId() {
        return qoodleViewId;
    }

    public void setQoodleViewId(long qoodleViewId) {
        this.qoodleViewId = qoodleViewId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getChiusura() {
        return chiusura;
    }

    public void setChiusura(String chiusura) {
        this.chiusura = chiusura;
    }

    public ArrayList<QoodleElement> getEle() {
        return ele;
    }

    public void setEle(ArrayList<QoodleElement> ele) {
        this.ele = ele;
    }

    @Override
    public void insert(String name, Datastore ds) {
        long nuovoId = inserisci(name, ds);
        this.setQoodleViewId(nuovoId);
        ds.save(this);

    }
}
