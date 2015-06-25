package edu.upb.omaigad.upbmatch.upb_match.bussinesslogic;

import android.util.Log;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andyibanezk on 6/11/15.
 */
public class ActivitiesManager implements IActivitiesManager {

    /**
     * Devuelve todas las actividades.
     * @param callback
     */
    public void getActivities(final CustomSimpleCallback<Actividad> callback) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Actividades");
        query.orderByAscending("Nombre_Actividad");
        query.findInBackground(new FindCallback<ParseObject>() {
            // ORIGINAL: public void done(ParseObject object, ParseException e)
            public void done(List<ParseObject> object, ParseException e) {
                if (e == null) {
                    ArrayList<Actividad> teams = new ArrayList<Actividad>();
                    // TODOS LOS OBJETOS DEL PARSE.
                    for(ParseObject act : object) {
                        Actividad indiAct = new Actividad(act);
                        teams.add(indiAct);
                    }

                    try {
                        ParseObject.unpinAll("ACTIVITIES_LABEL");
                    } catch(Exception ex) {
                        Log.e("ANDYCHE", "Couldn't unpin.");
                    }

                    Log.e("DOKO", "HERE");
                    ParseObject.pinAllInBackground("ACTIVITIES_LABEL", object);
                    Log.e("DOKO", "THEREAFTER");
                    callback.done(teams);
                } else {
                    Log.e("DOKO", "The error is: " + e.getMessage());
                    actividadesCache(callback);
                }
            }
        });
    }

    /**
     * Devuelve el cache de todas las actividades.
     * @param callback
     */
    public void actividadesCache(final CustomSimpleCallback<Actividad> callback) {
        // ALGO SE HA ESTIDO CHE
        Log.e("ANDY CHE", "ALGO SE HA ESTIDO CHE");
        ParseQuery cacheQuery = ParseQuery.getQuery("Actividades");
        //cacheQuery.setCachePolicy(ParseQuery.CachePolicy.CACHE_ELSE_NETWORK);
        cacheQuery.fromLocalDatastore();
        cacheQuery.orderByAscending("Nombre_Actividad");
        cacheQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                if (e == null) {
                    ArrayList<Actividad> teams = new ArrayList<Actividad>();
                    // TODOS LOS OBJETOS DEL PARSE.
                    for (ParseObject act : list) {
                        Actividad indiAct = new Actividad(act);
                        teams.add(indiAct);
                    }

                    if (teams.toArray().length > 0) {
                        Log.e("ANDY", "ARRAY MAYOR A CERO CHE");
                        callback.fail("cache", teams);
                    } else {
                        Log.e("ANDY", "ARRAY 0 CHE");
                        callback.fail("no cache", null);
                    }
                } else {
                    Log.e("ANDY CHE CHE", e.getMessage());
                    callback.fail(e.getMessage(), null);
                }
            }
        });
    }
}
