package edu.upb.omaigad.upbmatch.upb_match.bussinesslogic;
import android.util.Log;

import com.parse.*;
import java.util.*;

/**
 * Created by andyibanezk on 6/8/15.
 */

//VERSION ANTIGUA DE LA CLASE AHORA IMPLEMENTA LA INERFACE SCORES MANAGER (y)
//public class TeamsManager extends Object {



public class TeamsManager implements ITeamsManager {
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

    /**
     * getTeams: get all teams from the parse class Equipo and save them in a callback
     * @param callback class where all the data from the parse is stored to be used
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
                    for (ParseObject team : object) {
                        try {
                            Log.e("ANDY TEAM", "THE TEAM IS " + team.toString());
                            Equipo indiTeam = new Equipo(team);
                            teams.add(indiTeam);
                        } catch (Exception exi) {
                            Log.e("ANDY WHY", "WHY ARE YOU HERE " + exi.getMessage());
                            callback.fail(exi.getMessage(), null);
                            break;
                        }
                    }

                    try {
                        ParseObject.unpinAll("TEAMS_LABEL");
                    } catch (Exception ex) {
                        Log.e("ANDY TEAMS", "WHOOPS UNPINNING");
                    }

                    ParseObject.pinAllInBackground("TEAMS_LABEL", object);

                    callback.done(teams);
                } else {
                    // ALGO SE HA ESTIDO CHE
                    teamsCache(callback);
                }
            }
        });
    }

    /**
     * Retorna el cache de equipos.
     * @param callback
     */
    public void teamsCache(final CustomSimpleCallback<Equipo> callback) {
        ParseQuery<ParseObject> cacheQuery = ParseQuery.getQuery("Equipos");
        cacheQuery.orderByDescending("Puntaje");
        cacheQuery.fromLocalDatastore();
        cacheQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                Log.e("ANDY TEAMS", "TEAMS CACHE");
                if (e == null) {

                    ArrayList<Equipo> teams = new ArrayList<Equipo>();
                    // TODOS LOS OBJETOS DEL PARSE.
                    for (ParseObject team : list) {
                        try {
                            Equipo indiTeam = new Equipo(team);
                            teams.add(indiTeam);
                        } catch (Exception exi) {
                            callback.fail(exi.getMessage(), null);
                        }
                    }

                    if(teams.toArray().length > 0) {
                        Log.e("ANDY TEAMS", "MORE THAN 0 TEAMS");
                        callback.fail("cache", teams);
                    } else {
                        Log.e("ANDY TEAMS", "LESS OR = THAN 0 TEAMS");
                        callback.fail("no cache", teams);
                    }
                } else {
                    Log.e("ANDY TEAMS", "LOS TEAMS EN FAIL " + e.getMessage());
                    callback.fail(e.getMessage(), null);
                }
            }
        });
    }
}
