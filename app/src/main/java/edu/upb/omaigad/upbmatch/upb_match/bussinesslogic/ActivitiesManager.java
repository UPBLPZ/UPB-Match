package edu.upb.omaigad.upbmatch.upb_match.bussinesslogic;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andyibanezk on 6/11/15.
 */
public class ActivitiesManager {
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
                        String tEstado = act.getString("Estado_Actividad");
                        String tFechaHora = act.getString("Fecha_o_Hora");
                        String tID = act.getObjectId();
                        String tNombre = act.getString("Nombre_Actividad");
                        String tNumPartici = act.getString("Numero_Participantes");
                        String tReglas = act.getString("Reglas");
                        Actividad indiAct = new Actividad(tEstado, tFechaHora, tID, tNombre, tNumPartici, tReglas);
                        teams.add(indiAct);
                    }
                    callback.done(teams);
                } else {
                    // ALGO SE HA ESTIDO CHE
                    // TODO Pasarle el cache actual (cuando relevante)
                    callback.fail(e.getMessage(), new ArrayList<Actividad>());
                }
            }
        });
    }
}
