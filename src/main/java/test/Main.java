
package test;
import static spark.Spark.*;
import java.util.ArrayList;
import java.util.List;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;

import com.google.gson.Gson;
import spark.Request;


public class Main {

    private static void saveQoodle(String targetId, Request req, Gson gson, Datastore datastore) {

        final Qoodle primoQoodle = gson.fromJson(req.body().toString(), Qoodle.class);

        primoQoodle.insert(targetId, datastore);
    }

    private static String getList(Datastore datastore, Gson gson) {
        final Query<Qoodle> primaQuery = datastore.createQuery(Qoodle.class).retrievedFields(true, "qoodleId","title", "description","closingDate", "voList");
        final List<Qoodle> sal = primaQuery.asList();


        ArrayList<Qoodles> qList = new ArrayList<>();

        for ( Qoodle x : sal)
        {
            qList.add(
                    new Qoodles
                            (x.getqoodleId(),
                                    x.getTitle(),
                                    x.getDescription() ,
                                    x.getVoList().size(),
                                    x.getClosingDate())
            );

        }

        return gson.toJson(qList);
    }

    private static String getQoodleView(Gson gson,Datastore datastore, Request req) {
        long id = Long.parseLong( req.params(":id"));

        final Query<Qoodle> primaQuery = datastore.createQuery(Qoodle.class).filter("qoodleId ==", id).retrievedFields(true, "qoodleId","title", "description","closingDate", "qeList");
        final Qoodle targetQoodle = primaQuery.limit(1).get();


        QoodleView qView =
                new QoodleView(

                        targetQoodle.getQoodleId(),
                        targetQoodle.getTitle(),
                        targetQoodle.getDescription() ,
                        targetQoodle.getClosingDate(),
                        targetQoodle.getQeList()
                );

        return gson.toJson(qView);
    }




    public static void main(String[] args) {
        final String from= "http://localhost:8080";
        final String how= "get";
        final String head= "*";


        Inizialization init = new Inizialization(from, how, head);
        init.enableCORS();

        final Datastore datastore = init.createDatastore("test", "morphia_example");

        datastore.ensureIndexes();



        try{
            Gson gson = new Gson();

            post("/qoodles", (req, res) ->
            {
                saveQoodle("qoodleId", req, gson, datastore);
                return  req.body();
            });

            post("/submit-qoodle-choices", (req, res) ->  req.body() );




            get("/qoodles", (req, res) ->getList(datastore, gson));




            get("/view/:id", (req, res) ->
            {
                return getQoodleView(gson, datastore, req);

            });




            final Query<Qoodle> primaQuery = datastore.createQuery(Qoodle.class).retrievedFields(true, "qeList");
            final ArrayList<QoodleElement> templateExample = primaQuery.asList().get(0).getQeList();



            get("/create", (req, res) -> gson.toJson(templateExample));


        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }


    }

}
