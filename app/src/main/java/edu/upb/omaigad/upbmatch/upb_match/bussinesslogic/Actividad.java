package edu.upb.omaigad.upbmatch.upb_match.bussinesslogic;

import android.media.MediaRouter;
import android.util.Log;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andyibanezk on 6/11/15.
 */
public class Actividad {

    public class Participante {
        Equipo equipo;
        int puntaje;
        int puntosPerdidos;

        Participante(Equipo equipo, int puntaje, int puntosPerdidos) {
            this.equipo = equipo;
            this.puntaje = puntaje;
            this.puntosPerdidos = puntosPerdidos;
        }

        public Equipo getEquipo() {
            return equipo;
        }

        public int getPuntaje() {
            return puntaje;
        }

        public int getPuntosPerdidos() {
            return puntosPerdidos;
        }
    }

    private String estado;
    private String fechaUHora;
    private String ID;
    private String nombreActividad;
    private String numeroParticipantes;
    private String reglas;

    public String getEstado() {
        return estado;
    }

    public String getFechaUHora() {
        return fechaUHora;
    }

    public String getID() {
        return this.ID;
    }

    public String getNombreActividad() {
        return nombreActividad;
    }

    public String getNumeroParticipantes() {
        return numeroParticipantes;
    }

    public String reglas() {
        return reglas;
    }

    public Actividad(String estado, String fechaUHora, String ID, String nombreActividad, String numeroParticipantes, String reglas) {
        this.estado = estado;
        this.fechaUHora = fechaUHora;
        this.ID = ID;
        this.nombreActividad = nombreActividad;
        this.numeroParticipantes = numeroParticipantes;
        this.reglas = reglas;
    }

    public Actividad() {

    }


    public void getParticipantes(final CustomSimpleCallback<Participante> callback) {
        Log.e("ANDY LOG", "Supercat");
        ParseQuery query = ParseQuery.getQuery("Participacion");
        ParseObject acti = new ParseObject("Actividades");
        acti.setObjectId(this.ID);
        Log.e("ANDY LOG", "Chu");
        //query.whereEqualTo("Id_Actividad", acti);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                if (e == null) {
                    Log.e("ANDY PARSE LA LISTA", list.toString());

                    ArrayList<Participante> partis = new ArrayList<Participante>();
                    for (ParseObject participant : list) {
                        try {
                            ParseObject team = participant.getParseObject("Id_Equipo");
                            Log.e("ANDY YA OKAY ENTONCES", team.toString());
                            String tName = team.fetchIfNeeded().getString("Nombre_Equipo");
                            String tColor = team.fetchIfNeeded().getString("Color");
                            int tScore = team.fetchIfNeeded().getInt("Puntaje");
                            String id = team.fetchIfNeeded().getObjectId();
                            Equipo indiTeam = new Equipo(tName, tColor, tScore, id);
                            Participante dudeBro = new Participante(indiTeam, participant.getInt("Puntos_Ganados"), participant.getInt("Puntos_Perdido"));
                            partis.add(dudeBro);
                        } catch(Exception errorcito) {
                            callback.fail(errorcito.getMessage(), null);
                        }
                    }
                    callback.done(partis);
                } else {
                    // TODO: Pasar el cache cuando relevante
                    callback.fail(e.getMessage(), new ArrayList<Participante>());
                }
            }
        });
    }

    public Participante hiddenCreateParticipante(Equipo e, int punt, int perds) {
        return new Actividad.Participante(e, punt, perds);
    }
}
