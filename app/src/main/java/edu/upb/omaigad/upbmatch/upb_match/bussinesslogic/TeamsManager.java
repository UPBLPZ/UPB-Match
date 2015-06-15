package edu.upb.omaigad.upbmatch.upb_match.bussinesslogic;
import com.parse.*;
import java.util.*;

import edu.upb.omaigad.upbmatch.upb_match.bussinesslogic.CustomSimpleCallback;
import edu.upb.omaigad.upbmatch.upb_match.bussinesslogic.Equipo;

/**
 * Created by andyibanezk on 6/8/15.
 */

public class TeamsManager extends Object {

    /*
        Para usar esto:

        En un Activity:

        UPBMatchApplication app = (UPBMatchApplication) getApplication();

        app.teamsManager.getTeams(new CustomSimpleCallback<Equipo> {
            public void done(List<Equipo>) {
                // Hacer algo con la lista de equipos.
            }

            public void fail(String message) {
                // Esto se llama si la operacion ha fallado.
            }
        });
    */

    public void getTeams(final CustomSimpleCallback<Equipo> callback) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Equipos");
        query.orderByDescending("Puntaje");
        query.findInBackground(new FindCallback<ParseObject>() {
            // ORIGINAL: public void done(ParseObject object, ParseException e)
            public void done(List<ParseObject> object, ParseException e) {
                if (e == null) {
                    ArrayList<Equipo> teams = new ArrayList<Equipo>();
                    // TODOS LOS OBJETOS DEL PARSE.
                    for(ParseObject team : object) {
                        String tName = team.getString("Nombre_Equipo");
                        String tColor = team.getString("Color");
                        int tScore = team.getInt("Puntaje");
                        int tLost = team.getInt("Puntos_Perdidos");
                        String id = team.getObjectId();
                        Equipo indiTeam = new Equipo(tName, tColor, tScore, tLost, id);
                        teams.add(indiTeam);
                    }
                    callback.done(teams);
                } else {
                    // ALGO SE HA ESTIDO CHE
                    // TODO Pasarle el cache actual (cuando relevante)
                    callback.fail(e.getMessage(), new ArrayList<Equipo>());
                }
            }
        });
    }


}