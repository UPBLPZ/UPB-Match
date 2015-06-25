package edu.upb.omaigad.upbmatch.upb_match.bussinesslogic;

import android.util.Log;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by Miguel on 17/06/2015.
 */

public class EventsManager implements IEventsManager {


    @Override
    public void getEvents(String months , final CustomSimpleCallback<Evento> callback)  {
        //
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Eventos");

        SimpleDateFormat formatter = new SimpleDateFormat("MMM dd, yyyy, HH:mm ");
        Date low = null;
        try {
            low = formatter.parse( "" + months + " 1, 2015, 01:00");
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        Date up = null;
        try {
            up = formatter.parse( "" + months + " 31, 2015, 23:59");
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }

        query.whereGreaterThanOrEqualTo("Fecha_Evento", low);
        query.whereLessThanOrEqualTo("Fecha_Evento", up );

        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> object, ParseException e) {
                if (e == null) {
                    ArrayList<Evento> eventos = new ArrayList<Evento>();
                    // TODOS LOS OBJETOS DEL PARSE.
                    for(ParseObject evnt : object) {
                        String eID = evnt.getObjectId();
                        String eNombre = evnt.getString("Nombre_Evento");
                        Date eFecha = evnt.getDate("Fecha_Hora");
                        Calendar c1 = Calendar.getInstance();
                        c1.setTime(eFecha);
                        int dia = c1.get(Calendar.DAY_OF_MONTH);
                        String hours = "" + c1.get(Calendar.HOUR_OF_DAY) + ":" + c1.get(Calendar.MINUTE);
                        String eDesc = evnt.getString("Descripcion");

                        Evento indiEvent = new Evento(eID, eNombre, dia,hours, eDesc);

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
      //  ParseObject equipo = new ParseObject("Equipos");
      //  ParseObject evento = new ParseObject("Eventos");

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                if (e == null) {
                    Log.e("Micky",list.toString());
                    ArrayList<Involucrado> invol = new ArrayList<Involucrado>();
                    for (ParseObject involucrados : list){
                        try {
                            // obtener los datos del equipo involucrados en el evento
                            ParseObject team = involucrados.getParseObject("Id_Equipo");
                            Log.e("ANDY YA OKAY ENTONCES", team.toString());
                            String tName = team.fetchIfNeeded().getString("Nombre_Equipo");
                            String tColor = team.fetchIfNeeded().getString("Color");
                            int tScore = team.fetchIfNeeded().getInt("Puntaje");
                            String id = team.fetchIfNeeded().getObjectId();
                            Equipo indiTeam = new Equipo(tName, tColor, tScore, id);
                            //obtener los datos del evento
                            ParseObject event = involucrados.getParseObject("Id_Evento");
                            String eID = event.getObjectId();
                            String eNombre = event.getString("Nombre_Evento");
                            Date eFecha = event.getDate("Fecha_Hora");
                            String eDesc = event.getString("Descripcion");
                            Evento indiEvent = new Evento(eID, eNombre, eFecha, eDesc);
                            // Sea crea los involucrados con su evento y su equipo participe
                            Involucrado invo = new Involucrado(indiTeam,indiEvent);
                            invol.add(invo);

                        } catch(Exception error) {
                            callback.fail(error.getMessage(), null);
                        }
                    }


                }else{

                }

            }
        });

    }
}

