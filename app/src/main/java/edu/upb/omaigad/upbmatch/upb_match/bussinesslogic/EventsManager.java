package edu.upb.omaigad.upbmatch.upb_match.bussinesslogic;

import android.util.Log;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Miguel on 17/06/2015.
 */

public abstract class EventsManager implements EventsInterface {


    @Override
    public void getEvents(final CustomSimpleCallback<Evento> callback) {
        // Pendiente
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Eventos");
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> object, ParseException e) {
                if (e == null) {
                    ArrayList<Evento> eventos = new ArrayList<Evento>();
                    // TODOS LOS OBJETOS DEL PARSE.
                    for(ParseObject evnt : object) {
                        String eID = evnt.getObjectId();
                        String eNombre = evnt.getString("Nombre_Evento");
                        Date eFecha = evnt.getDate("Fecha_Hora");
                        String eDesc = evnt.getString("Descripcion");

                        Evento indiEvent = new Evento(eID, eNombre, eFecha, eDesc);
                        eventos.add(indiEvent);
                    }
                    callback.done(eventos);
                } else {
                    // No Esta Jalando los datos :(
                    // TODO Pasarle el cache actual (cuando relevante)
                    callback.fail(e.getMessage(), new ArrayList<Evento>());
                }
            }
        });
    }

    @Override
    public void getInvolucrados(final CustomSimpleCallback<Involucrado> callback) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Involucrados");
        Log.e("Micky","accede a la tabla");
        ParseObject equipo = new ParseObject("Equipos");
        ParseObject evento = new ParseObject("Eventos");


    }
}

