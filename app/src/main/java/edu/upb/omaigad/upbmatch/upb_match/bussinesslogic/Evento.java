package edu.upb.omaigad.upbmatch.upb_match.bussinesslogic;

import android.util.Log;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Miguel on 17/06/2015.
 */
public class Evento {

    /**
     * atributos de la clase en funcion al parse
     */
    private String EventID;
    private String nombreEvento;
    private int dia;
    private String hora;
    private Date fecha;
    private String descripcion;

    /**
     * Construcotor de la clase
     * @param id
     * @param titulo
     * @param fecha
     * @param desc
     */
    public Evento(String id, String titulo, Date fecha,String desc){
       this.EventID = id;
        this.nombreEvento=titulo;
        this.fecha=fecha;
        this.descripcion=desc;

    }

    public Evento(String id, String titulo, int dia,String hora,String desc){
        this.EventID = id;
        this.nombreEvento=titulo;
        this.dia=dia;
        this.hora=hora;
        this.descripcion=desc;

    }

    public Evento(){

    }

    /**
     * getters para la clase eventos y retornar sus atributos
     * @return
     */
    public String getId(){return EventID;}
    public String getTitulo() {return nombreEvento; }
    public Date getFecha() {return fecha ; }
    public String getDescripcion() {return descripcion; }
    public int getDia(){return dia;}
    public String getHora() {return hora;}


    public void getInvolucrados(final CustomSimpleCallback<Involucrado> callback) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Involucrados");
        Log.e("Micky", "accede a la tabla");
        ParseObject evento = new ParseObject("Eventos");
        evento.setObjectId(this.EventID);
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
                            Calendar c1 = Calendar.getInstance();
                            c1.setTime(eFecha);
                            int dia = c1.get(Calendar.DAY_OF_MONTH);
                            String hours = "" + c1.get(Calendar.HOUR_OF_DAY) + ":" + c1.get(Calendar.MINUTE);


                            String eDesc = event.getString("Descripcion");


                            Evento indiEvent = new Evento(eID, eNombre, dia,hours, eDesc);
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
