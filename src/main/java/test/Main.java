
package test;
import static spark.Spark.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;



import org.json.simple.*;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Main {

  private static final String filePath = "/home/jab/Scrivania/spark/src/main/java/dati/qoodle-list.json";
  private static final String viewPath = "/home/jab/Scrivania/spark/src/main/java/dati/qoodle-view.json";



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
          // Note: this may or may not be necessary in your particular application
          response.type("application/json");
      });
  }


    public static void main(String[] args) {
      final String from= "http://localhost:8080";
      final String how= "get";
      final String head= "*";



      enableCORS(from, how , head);
      //togliere
        get("/hello", (req, res) -> "Hello World");

        try{
        FileReader reader = new FileReader(filePath);
        JSONParser jsonParser = new JSONParser();
        JSONObject  jsonObject = (JSONObject) jsonParser.parse(reader);
        JSONArray jsonArray = (JSONArray) jsonObject.get("qoo");


        get("/qoodles", (req, res) -> jsonArray);


        FileReader viewReader = new FileReader(viewPath);
        JSONParser viewParser = new JSONParser();
        JSONObject  viewJson = (JSONObject) jsonParser.parse(viewReader);



        get("/view", (req, res) -> viewJson);






      }
      catch(FileNotFoundException ex)
      {
      ex.printStackTrace();
      }
      catch (IOException ex) {
      ex.printStackTrace();
      }
      catch (ParseException ex) {
        ex.printStackTrace();
    } catch (NullPointerException ex) {
        ex.printStackTrace();
    }





  /*    public JSONArray dati = new JSONArray ('');





        get("/qoodles", (req, res) -> dati);*/

    }

}
