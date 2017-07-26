package test;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

@Entity
public class Counter {

    @Id
    private String helloid;
    private long seq;

    public Counter() {
        this.helloid = "";
        this.seq = 0;
    }

    public Counter(String helloid, long seq) {
        this.helloid = helloid;
        this.seq = seq;
    }

    public String getHelloid() {
        return helloid;
    }

    public void setHelloid(String helloid) {
        this.helloid = helloid;
    }

    public long getSeq() {
        return seq;
    }

    public void setSeq(long seq) {
        this.seq = seq;
    }



}

