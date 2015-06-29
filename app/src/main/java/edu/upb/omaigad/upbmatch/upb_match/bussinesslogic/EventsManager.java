package edu.upb.omaigad.upbmatch.upb_match.bussinesslogic;

import android.util.Log;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

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
    public void getEvents(final int months,final CustomSimpleCallback<Evento> callback) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Eventos");

        Calendar c1 = Calendar.getInstance();
        c1.set(2015,months,1);

        Calendar c2 = Calendar.getInstance();

        int topDay=30;

        if (months==1) {
            topDay = 28;
        }else if(months==3 || months==5 || months ==8 || months==10){
            topDay=30;
        }else{
            topDay=31;
        }


        c2.set(2015,months,topDay);
        Date low =c1.getTime();
        Date up = c2.getTime();

        query.whereGreaterThanOrEqualTo("Fecha_Evento",low);
        query.whereLessThanOrEqualTo("Fecha_Evento",up );

        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> list, ParseException e) {

                if (e == null) {
                    ArrayList<Evento> eventos = new ArrayList<Evento>();

                    for(ParseObject event : list){
                        String eId = event.getObjectId();
                        String eTitulo = event.getString("Nombre_Evento");
                        Date eDate = event.getDate("Fecha_Evento");
                        Calendar cal = Calendar.getInstance();
                        cal.setTime(eDate);

                        int eDay = cal.get(Calendar.DAY_OF_MONTH);
                        String eHora = "" + cal.get(Calendar.HOUR_OF_DAY) +":" + cal.get(Calendar.MINUTE);
                        String eDesc = event.getString("Descripcion");
                        Evento neoEvent = new Evento(eId,eTitulo,eDay,eHora,eDesc);
                        eventos.add(neoEvent);

                    }
                    callback.done(eventos);
                }else {

                    callback.fail(e.getMessage(),null );
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
                            Date eFecha = event.getDate("Fecha_Evento");
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

