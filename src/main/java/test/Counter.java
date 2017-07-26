package test;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

@Entity
public class Counter {

    @Id
    private String qoodlesId;
    private long seq;

    public Counter() {
        this.qoodlesId = "";
        this.seq = 0;
    }

    public Counter(String qoodlesId, long seq) {
        this.qoodlesId = qoodlesId;
        this.seq = seq;
    }

    public String getqoodlesId() {
        return qoodlesId;
    }

    public void setqoodlesId(String qoodlesId) {
        this.qoodlesId = qoodlesId;
    }

    public long getSeq() {
        return seq;
    }

    public void setSeq(long seq) {
        this.seq = seq;
    }



}

