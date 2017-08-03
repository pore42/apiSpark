
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

        ArrayList<Integer> voti = new ArrayList<>(primoQoodle.getQeList().size());
        ArrayList<Vote> votiUtenti = new ArrayList<>();

        votiUtenti.add(new Vote());
        primoQoodle.setVoList(votiUtenti);

        primoQoodle.insert(targetId, datastore);
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
            }
            );

            post("/submit-qoodle-choices", (req, res) ->  req.body() );



            String targetId = "qoodlesId";

            Insertable.progressiveId(targetId, datastore);


            Qoodles q = new Qoodles( "Gas di Novembre", "idfsofdsijjfsdijfsdijfsijosdfjiofd", 6, "October 13, 2014 11:13:00") ;
            Qoodles q1 = new Qoodles( "Christams Dinner", "idfsofdsijjfsdijfsdijfsijosdfjiofd", 4, "October 13, 2018 11:13:00") ;
            Qoodles q2 = new Qoodles( "Picnic", "Picnic in un giardino milanese", 15, "October 17, 2017 14:30:00") ;
            Qoodles q3 = new Qoodles("TestOk", "Test per connessione con db", 200, "October 11, 2017 14:30:00") ;
            Qoodles q4 = new Qoodles("Birthday Party", "Festa di compleanno al birrificio", 300, "September 3, 2017 00:00:00") ;



            q.insert( targetId, datastore);
            q1.insert(targetId, datastore);
            q2.insert(targetId, datastore);
            q3.insert(targetId, datastore);
            q4.insert(targetId, datastore);









            get("/list", (req, res) ->
            {
                final Query<Qoodle> primaQuery = datastore.createQuery(Qoodle.class).retrievedFields(true, "qoodleId","title", "description","closingDate", "voList");
                final List<Qoodle> sal = primaQuery.asList();


                ArrayList<Qoodles> qList = new ArrayList<>();

                for ( Qoodle x : sal)
                {
                    System.out.println(x.getVoList() + "    "  + x.getClosingDate());
                    qList.add(
                            new Qoodles
                                    (x.getqoodleId(),
                                            x.getTitle(),
                                            x.getDescription() ,
                                            x.getVoList().size(),
                                            x.getClosingDate())
                    );

                }

                String provaJson = gson.toJson(qList);

                return  provaJson;
            }
            );





            //get("/list", (req, res) -> provaJson);




            String targetViewId = "qoodleViewId";

            Insertable.progressiveId(targetViewId, datastore);





            QoodleElement qe = new QoodleElement(1L, "banana", "kg", "€", 1.5f, 0, "_assets/img/bana.png" );
            QoodleElement qe2 = new QoodleElement(2L, "MelaRossa",  "kg", "€", 2.0f, 0, "_assets/img/redApple.png" );
            QoodleElement qe3 = new QoodleElement(3L, "Kiwi", "", "", 0, "_assets/img/kiwi.png" );
            QoodleElement qe4 = new QoodleElement(4L, "Pesca", "", "€",0, "_assets/img/bana.png" );
            QoodleElement qe5 = new QoodleElement(5L, "Uva", "", "€", 0, "_assets/img/bana.png" );
            QoodleElement qe6 = new QoodleElement(6L, "Number of Vegetarian","", "€", 0, "_assets/img/kiwi.png" );
            QoodleElement qe7 = new QoodleElement(7L, "Kiwi","bott", "€", 3.0f, 5, "_assets/img/kiwi.png" );
            QoodleElement qe8 = new QoodleElement(8L, "Number of people","", "€", 0.0f, 0, "_assets/img/redApple.png" );





            ArrayList<QoodleElement> qeList = new ArrayList<>();
            qeList.add(qe);
            qeList.add(qe2);
            qeList.add(qe3);
            qeList.add(qe4);
            qeList.add(qe5);
            qeList.add(qe6);
            qeList.add(qe7);
            qeList.add(qe8);






            QoodleView qv = new QoodleView( "Acquisto di gruppo di novembre", "È a disposizione sortita varietà di verdure e frutta di stagione","July 31, 2027 19:53:00", qeList);


            qv.insert(targetViewId, datastore);



            final Query<QoodleView> viewQuery = datastore.createQuery(QoodleView.class);
            final QoodleView view = viewQuery.asList().get(0);

            String viewJson = gson.toJson(view);

            get("/view", (req, res) -> viewJson);



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
