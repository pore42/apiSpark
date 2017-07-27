
package test;
import static spark.Spark.*;
import java.io.FileReader;
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
        Counter newCounter = ds.find(Counter.class).field("qoodlesId").equal(name).get();
        long nuovoId = newCounter.getSeq();


        Query<Counter> query = ds.createQuery(Counter.class).field("qoodlesId").equal(name);
        UpdateOperations<Counter> ops= ds.createUpdateOperations(Counter.class).set("seq", ++nuovoId);
        ds.update(query, ops);

        ds.save(new Qoodles(q, nuovoId));
    }




    public static void main(String[] args) {
        final String from= "http://localhost:8080";
        final String how= "get";
        final String head= "*";



        enableCORS(from, how , head);

        try{
            JSONParser jsonParser = new JSONParser();

            FileReader reader = new FileReader(filePath);
            JSONObject  jsonObject = (JSONObject) jsonParser.parse(reader);
            JSONArray jsonArray = (JSONArray) jsonObject.get("qoo");


            get("/list", (req, res) -> jsonArray);


            FileReader viewReader = new FileReader(viewPath);
            JSONObject  viewJson = (JSONObject) jsonParser.parse(viewReader);



            get("/view", (req, res) -> viewJson);

            FileReader createReader = new FileReader(createPath);
            JSONObject  createObject = (JSONObject) jsonParser.parse(createReader);
            JSONArray createArray = (JSONArray) createObject.get("ele");


            get("/create", (req, res) -> createArray);

            post("/submit-new-qoodle", (req, res) -> req.body());

            post("/submit-qoodle-choices", (req, res) ->  req.body() );





        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }


        final Morphia morphia = new Morphia();
        morphia.mapPackage("test");

        final Datastore datastore = morphia.createDatastore(new MongoClient(), "morphia_example");

        datastore.ensureIndexes();

        //inizialization
        final Counter counter = new Counter("id", 0);
        datastore.save(counter);
        //targetid cioè l'id di Hello
        String targetId = "id";


        //inserisco con progressive id
        Qoodles prova = new Qoodles((long) 1 , "Gas di Novembre", "idfsofdsijjfsdijfsdijfsijosdfjiofd", 6, new Date("October 13, 2014 11:13:00") );
        Qoodles prova1 = new Qoodles((long) 2 , "Christams Dinner", "idfsofdsijjfsdijfsdijfsijosdfjiofd", 4, new Date("October 13, 2018 11:13:00") );


        inserisci(prova, targetId, datastore);
        inserisci(prova1, targetId, datastore);


        final Query<Qoodles> primaQuery = datastore.createQuery(Qoodles.class);
        final List<Qoodles> sal = primaQuery.asList();

        //List<Qoodles> tail = datastore.createQuery(Qoodles.class).filter("qoodlesId <=", 1).asList();


        //mostro quello che ho inserito
        for( Qoodles x: sal)
            System.out.println(x.getTitolo());


    }

}
