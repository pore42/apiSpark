package test;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;


public abstract class Insertable {


    public abstract void insert(Insertable i, String name, Datastore ds);

    public static long inserisci(String name, Datastore ds) {
        Counter newCounter = ds.find(Counter.class).field("id").equal(name).get();
        long nuovoId = newCounter.getSeq();


        Query<Counter> query = ds.createQuery(Counter.class).field("id").equal(name);
        UpdateOperations<Counter> ops= ds.createUpdateOperations(Counter.class).set("seq", ++nuovoId);
        ds.update(query, ops);
        return nuovoId;
    }

}
