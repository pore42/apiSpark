
package test;
import static spark.Spark.*;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import com.mongodb.MongoClient;
import org.json.simple.*;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;

import com.google.gson.Gson;


public class Main {

    private static final String filePath = "/home/jab/workspace/spark/src/main/java/dati/qoodle-list.json";
    private static final String viewPath = "/home/jab/workspace/spark/src/main/java/dati/qoodle-view.json";
    private static final String createPath = "/home/jab/workspace/spark/src/main/java/dati/qoodle-create.json";



    private static void enableCORS(final String origin, final String methods, final String headers) {

        options("/*", (request, response) -> {

            String accessControlRequestHeaders = request.headers("Access-Control-Request-Headers");
            if (accessControlRequestHeaders != null) {
                response.header("Access-Control-Allow-Headers", accessControlRequestHeaders);
            }

            String accessControlRequestMethod = request.headers("Access-Control-Request-Method");
            if (accessControlRequestMethod != null) {
                response.header("Access-Control-Allow-Methods", accessControlRequestMethod);
            }

            return "OK";
        });

        before((request, response) -> {
            response.header("Access-Control-Allow-Origin", origin);
            response.header("Access-Control-Request-Method", methods);
            response.header("Access-Control-Allow-Headers", headers);
            response.type("application/json");
        });
    }

    private static void inserisci(Qoodles q, String name, Datastore ds)
    {
        Counter newCounter = ds.find(Counter.class).field("id").equal(name).get();
        long nuovoId = newCounter.getSeq();


        Query<Counter> query = ds.createQuery(Counter.class).field("id").equal(name);
        UpdateOperations<Counter> ops= ds.createUpdateOperations(Counter.class).set("seq", ++nuovoId);
        ds.update(query, ops);

        ds.save(new Qoodles(q, nuovoId));
    }

    private static void inserisciView(QoodleView qv, String name, Datastore ds)
    {
        Counter newCounter = ds.find(Counter.class).field("id").equal(name).get();
        long nuovoId = newCounter.getSeq();


        Query<Counter> query = ds.createQuery(Counter.class).field("id").equal(name);
        UpdateOperations<Counter> ops= ds.createUpdateOperations(Counter.class).set("seq", ++nuovoId);
        ds.update(query, ops);

        ds.save(new QoodleView(qv, nuovoId));
    }

    private static void inserisciNew(QoodleElement qe, String name, Datastore ds)
    {
        Counter newCounter = ds.find(Counter.class).field("id").equal(name).get();
        long nuovoId = newCounter.getSeq();


        Query<Counter> query = ds.createQuery(Counter.class).field("id").equal(name);
        UpdateOperations<Counter> ops= ds.createUpdateOperations(Counter.class).set("seq", ++nuovoId);
        ds.update(query, ops);

        ds.save(new QoodleElement(qe, nuovoId));
    }


    public static void main(String[] args) {
        final String from= "http://localhost:8080";
        final String how= "get";
        final String head= "*";



        enableCORS(from, how , head);

        try{

            final Morphia morphia = new Morphia();
            morphia.mapPackage("test");
            final Datastore datastore = morphia.createDatastore(new MongoClient(), "morphia_example");

            datastore.ensureIndexes();

            //inizialization
            final Counter counter = new Counter("qoodlesId", 0);
            datastore.save(counter);
            //targetid cioè l'id di Hello
            String targetId = "qoodlesId";


            //inserisco con progressive id
            Qoodles q = new Qoodles( "Gas di Novembre", "idfsofdsijjfsdijfsdijfsijosdfjiofd", 6, new Date("October 13, 2014 11:13:00") );
            Qoodles q1 = new Qoodles( "Christams Dinner", "idfsofdsijjfsdijfsdijfsijosdfjiofd", 4, new Date("October 13, 2018 11:13:00") );
            Qoodles q2 = new Qoodles( "Picnic", "Picnic in un giardino milanese", 15, new Date("October 17, 2017 14:30:00") );
            Qoodles q3 = new Qoodles("TestOk", "Test per connessione con db", 200, new Date("October 11, 2017 14:30:00") );
            Qoodles q4 = new Qoodles("Birthday Party", "Festa di compleanno al birrificio", 300, new Date("September 3, 2017 00:00:00") );



            inserisci(q, targetId, datastore);
            inserisci(q1, targetId, datastore);
            inserisci(q2, targetId, datastore);
            inserisci(q3, targetId, datastore);
            inserisci(q4, targetId, datastore);







            JSONParser jsonParser = new JSONParser();

/*
            FileReader reader = new FileReader(filePath);
            JSONObject  jsonObject = (JSONObject) jsonParser.parse(reader);
            JSONArray jsonArray = (JSONArray) jsonObject.get("qoo");



            FileReader viewReader = new FileReader(viewPath);
            JSONObject  viewJson = (JSONObject) jsonParser.parse(viewReader);



            get("/view", (req, res) -> viewJson);


            FileReader createReader = new FileReader(createPath);
            JSONObject  createObject = (JSONObject) jsonParser.parse(createReader);
            JSONArray createArray = (JSONArray) createObject.get("ele");


            get("/create", (req, res) -> createArray);
*/
            post("/submit-new-qoodle", (req, res) -> req.body());

            post("/submit-qoodle-choices", (req, res) ->  req.body() );










            final Query<Qoodles> primaQuery = datastore.createQuery(Qoodles.class);
            final List<Qoodles> sal = primaQuery.asList();

            Gson gson = new Gson();
            String provaJson = gson.toJson(sal);

            get("/list", (req, res) -> provaJson);




            final Counter counterView = new Counter("qoodleViewId", 0);
            datastore.save(counter);





            QoodleElement qe = new QoodleElement(1l, "banana", 0, 99999, "kg", "€", 1.5f, 0, "_assets/img/bana.png" );
            QoodleElement qe2 = new QoodleElement(2l, "MelaRossa", 0, 99999, "kg", "€", 2.0f, 0, "_assets/img/redApple.png" );
            QoodleElement qe3 = new QoodleElement(3l, "Kiwi", 0, 99999, "", "", 0.0f, 0, "_assets/img/kiwi.png" );
            QoodleElement qe4 = new QoodleElement(4l, "Pesca", 0, 99999, "", "€", 0.0f, 0, "_assets/img/bana.png" );
            QoodleElement qe5 = new QoodleElement(5l, "Uva", 0, 99999, "", "€", 0.0f, 0, "_assets/img/bana.png" );
            QoodleElement qe6 = new QoodleElement(6l, "Number of Vegetarian", 0, 99999, "", "€", 0.0f, 0, "_assets/img/kiwi.png" );
            QoodleElement qe7 = new QoodleElement(7l, "Kiwi", 0, 99999, "bott", "€", 3.0f, 5, "_assets/img/kiwi.png" );
            QoodleElement qe8 = new QoodleElement(8l, "Number of people", 0, 99999, "", "€", 4.0f, 0, "_assets/img/redApple.png" );





            ArrayList<QoodleElement> qeList = new ArrayList<QoodleElement>();
            qeList.add(qe);
            qeList.add(qe2);
            qeList.add(qe3);
            qeList.add(qe4);
            qeList.add(qe5);
            qeList.add(qe6);
            qeList.add(qe7);
            qeList.add(qe8);






            QoodleView qv = new QoodleView(0l, "Acquisto di gruppo di novembre", "È a disposizione sortita varietà di verdure e frutta di stagione","July 31, 2017 19:53:00", qeList);


            String targetViewId = "qoodlesId";
            inserisciView(qv, targetViewId, datastore);



            final Query<QoodleView> viewQuery = datastore.createQuery(QoodleView.class);
            final QoodleView view = viewQuery.asList().get(0);

            String viewJson = gson.toJson(view);

            get("/view", (req, res) -> viewJson);



            //System.out.println(viewJson);


            QoodleElement newQe = new QoodleElement(0l, "banana", 0, 99999, "kg", "€", 4.5f, 5, "_assets/img/bana.png" );
            QoodleElement newQe2 = new QoodleElement(1l, "Intolleranti al lattosio", 0, 99999, "kg", "€", 0.0f, 0, "_assets/img/redApple.png" );
            QoodleElement newQe3 = new QoodleElement(2l, "celiaci", 0, 99999, "", "", 0.0f, 0, "_assets/img/kiwi.png" );


            //inizialization
            final Counter elementCounter = new Counter("elId", 0);
            datastore.save(elementCounter);
            //targetid cioè l'id di Hello
            String elementTargetId = "elId";





            inserisciNew(newQe, elementTargetId, datastore);
            inserisciNew(newQe2, elementTargetId, datastore);
            inserisciNew(newQe3, elementTargetId, datastore);



            final Query<QoodleElement> elementQuery = datastore.createQuery(QoodleElement.class);
            final List<QoodleElement> elements = elementQuery.asList();

            String elementsJson = gson.toJson(elements);

            get("/create", (req, res) -> elementsJson);


            System.out.println(elementsJson);

        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }


    }

}
