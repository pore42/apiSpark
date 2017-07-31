package test;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import java.util.Date;

@Entity
public class Qoodles extends Insertable{


    @Id
    private long qoodlesId ;
    private String titolo;

    private String descrizione;

    private int partecipanti;
    private Date dataChiusura;

    public Qoodles() {

        this.qoodlesId = 0L;
        this.titolo = "defTitle";
        this.descrizione = "defDescription";
        this.partecipanti = 0;
        this.dataChiusura = new Date();
    }

    public Qoodles( String titolo, String descrizione, int partecipanti, Date dataChiusura) {
        this.qoodlesId = 0;
        this.titolo = titolo;
        this.descrizione = descrizione;
        this.partecipanti = partecipanti;
        this.dataChiusura = dataChiusura;
    }

    public Qoodles(Qoodles q , long id)
    {
        this.qoodlesId = id;
        this.titolo = q.getTitolo();
        this.descrizione = q.getDescrizione();
        this.partecipanti = q.getPartecipanti();
        this.dataChiusura = q.getDataChiusura();
    }



    public Qoodles(long qoodlesId, String titolo, String descrizione, int partecipanti, Date dataChiusura) {
        qoodlesId = qoodlesId;
        this.titolo = titolo;
        this.descrizione = descrizione;
        this.partecipanti = partecipanti;
        this.dataChiusura = dataChiusura;
    }


    public long getQoodlesId() {
        return qoodlesId;
    }

    public void setQoodlesId(long qoodlesId) {
        qoodlesId = qoodlesId;
    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public int getPartecipanti() {
        return partecipanti;
    }

    public void setPartecipanti(int partecipanti) {
        this.partecipanti = partecipanti;
    }

    public Date getDataChiusura() {
        return dataChiusura;
    }

    public void setDataChiusura(Date dataChiusura) {
        this.dataChiusura = dataChiusura;
    }


    @Override
    public void insert( String name, Datastore ds) {
        long nuovoId = inserisci(name, ds);
            this.setQoodlesId(nuovoId);

    }
}
