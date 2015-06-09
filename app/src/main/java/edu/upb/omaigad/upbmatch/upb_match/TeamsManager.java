package edu.upb.omaigad.upbmatch.upb_match;
import com.parse.*;

/**
 * Created by andyibanezk on 6/8/15.
 */

public class TeamsManager extends Object {

    /*
        Para usar esto:

     app.teamsManager.getTeams(new GetCallback<ParseObject>() {
            public void done(ParseObject object, ParseException e) {
                if (e == null) {
                    // TODOS LOS OBJETOS DEL PARSE.
                } else {
                    // ALGO SE HA ESTIDO CHE
                }
            }
        });
    */

    public void getTeams(GetCallback<ParseObject> callback) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Equipos");
        query.orderByAscending("Puntaje");
        query.getInBackground("xWMyZ4YEGZ", callback);
    }
}
