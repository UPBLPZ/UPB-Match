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

        Participante(Equipo equipo, int puntaje) {
            this.equipo = equipo;
            this.puntaje = puntaje;
        }

        Equipo getEquipo() {
            return equipo;
        }

        int getPuntaje() {
            return puntaje;
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


    public void getParticipantes(final CustomSimpleCallback<Participante> callback) {
        ParseQuery query = ParseQuery.getQuery("Participantes");
        query.whereEqualTo("Id_Actividad", this.ID);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                if (e == null) {
                    //Log.e("ANDY PARSE", parseObject.toString());

                    ArrayList<Participante> partis = new ArrayList<Participante>();
                    for (ParseObject participant : list) {
                        ParseObject team = participant.getParseObject("Id_Equipo");
                        String tName = team.getString("Nombre_Equipo");
                        String tColor = team.getString("Color");
                        int tScore = team.getInt("Puntaje");
                        int tLost = team.getInt("Puntos_Perdidos");
                        String id = team.getObjectId();
                        Equipo indiTeam = new Equipo(tName, tColor, tScore, tLost, id);
                        Participante dudeBro = new Participante(indiTeam, participant.getInt("puntaje"));
                        partis.add(dudeBro);
                    }
                    callback.done(partis);
                } else {
                    // TODO: Pasar el cache cuando relevante
                    callback.fail("Fail", new ArrayList<Participante>());
                }
            }
        });
    }
}
