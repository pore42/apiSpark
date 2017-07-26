
package test;
import static spark.Spark.*;
import java.io.FileReader;
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

    public static void inserisci(String saluto, String name, Datastore ds)
    {
        Counter nuovoCounter = ds.find(Counter.class).field("helloid").equal(name).get();
        long nuovoId = nuovoCounter.getSeq();


        Query<Counter> query = ds.createQuery(Counter.class).field("helloid").equal(name);
        UpdateOperations<Counter> ops= ds.createUpdateOperations(Counter.class).set("seq", ++nuovoId);
        ds.update(query, ops);

        ds.save(new Hello(saluto, nuovoId));
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
        inserisci("ciao", targetId, datastore);
        inserisci("hi man", targetId, datastore);
        inserisci("Bonjour", targetId, datastore);


        final Query<Hello> primaQuery = datastore.createQuery(Hello.class);
        final List<Hello> sal = primaQuery.asList();

        //mostro quello che ho inserito
        for( Hello x: sal)
            System.out.println(x.getSaluto());



        /*for( Hello x: sal2)
            System.out.println(x.getSaluto());*/
    }

}
