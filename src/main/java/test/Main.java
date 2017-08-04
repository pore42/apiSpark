
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

        System.out.println("titolo" + targetQoodle.getTitle());


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

            //System.out.println(viewJson);


            QoodleElement newQe = new QoodleElement( "banana", 0, 99999, "kg", "€", 4.5f, 5, "_assets/img/bana.png" );
            QoodleElement newQe2 = new QoodleElement("Intolleranti al lattosio", 0, 99999, "€", 0.0f, 0, "_assets/img/redApple.png" );
            QoodleElement newQe3 = new QoodleElement("celiaci", 0, 99999, "", "", 0.0f, 0, "_assets/img/kiwi.png" );


            String elementTargetId = "elId";



            Insertable.progressiveId(elementTargetId, datastore);





            newQe.insert(elementTargetId, datastore);
            newQe2.insert(elementTargetId, datastore);
            newQe3.insert(elementTargetId, datastore);



            final Query<QoodleElement> elementQuery = datastore.createQuery(QoodleElement.class);
            final List<QoodleElement> elements = elementQuery.asList();

            String elementsJson = gson.toJson(elements);

            get("/create", (req, res) -> elementsJson);


            //System.out.println(elementsJson);

        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }


    }

}
