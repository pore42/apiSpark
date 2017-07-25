
package test;
import static spark.Spark.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;


import com.mongodb.MongoClient;
import org.json.simple.*;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.query.Query;

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

        final Hello saluto = new Hello("ciao");
        final Hello saluto2 = new Hello("Bonjour");
        final Hello saluto3 = new Hello ("Hi man");


        datastore.save(saluto);
        datastore.save(saluto3);
        datastore.save(saluto2);


       /* final Query<Hello> query = datastore.createQuery(Hello.class);
        final List<Hello> sal = query.asList();



        for( Hello x: sal)
            System.out.println(x.getSaluto());
        */
    }

}
