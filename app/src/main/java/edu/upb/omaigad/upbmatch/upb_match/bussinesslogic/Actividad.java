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

    public Actividad(ParseObject act) {
        String tEstado = act.getString("Estado");
        String tFechaHora = act.getString("Fecha_Hora");
        String tID = act.getObjectId();
        String tNombre = act.getString("Nombre_Actividad");
        String tNumPartici = act.getString("Numero_Participantes");
        String tReglas = act.getString("Reglas");
        this.estado = tEstado;
        this.fechaUHora = tFechaHora;
        this.ID = tID;
        this.nombreActividad = tNombre;
        this.numeroParticipantes = tNumPartici;
        this.reglas = tReglas;
    }

    public Actividad() {

    }


    public void getParticipantes(final CustomSimpleCallback<Participante> callback) {
        Log.e("ANDY LOG", "Supercat");
        ParseQuery query = ParseQuery.getQuery("Participacion");
        final ParseObject acti = ParseObject.createWithoutData("Actividades", this.ID);
        acti.setObjectId(this.ID);
        Log.e("ANDY LOG", "Chu");
        query.whereEqualTo("Id_Actividad", acti);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                if (e == null) {
                    Log.e("ANDY PARSE LA LISTA", list.toString());

                    ArrayList<Participante> partis = new ArrayList<Participante>();
                    for (ParseObject participant : list) {
                        try {
                            Equipo indiTeam = new Equipo(participant);
                            Participante dudeBro = new Participante(indiTeam, participant.getInt("Puntos_Ganados"), participant.getInt("Puntos_Perdido"));
                            partis.add(dudeBro);
                        } catch(Exception errorcito) {
                            callback.fail(errorcito.getMessage(), null);
                        }
                    }
                    try {
                        Log.e("ANDY BOY", "Pineando y despineando");
                        ParseObject.unpinAll("PARTICIPANTES_LABEL");
                    } catch(Exception exi) {
                        Log.e("ANDY CHE", "Ututuy no se pudo.");
                    }

                    ParseObject.pinAllInBackground("PARTICIPANTES_LABEL", list);

                    if(list.toArray().length <= 0) {
                        participantesCache(callback);
                    }

                    callback.done(partis);
                } else {
                    Log.e("ANDY PARTICIPANTES", e.getMessage());
                    participantesCache(callback);
                }
            }
        });
    }

    public void participantesCache(final CustomSimpleCallback<Participante> callback) {
        ParseQuery query = ParseQuery.getQuery("Participacion");
        final ParseObject acti = ParseObject.createWithoutData("Actividades", this.ID);
        acti.setObjectId(this.ID);
        ParseQuery cacheQuery = ParseQuery.getQuery("Participacion");
        cacheQuery.whereEqualTo("Id_Actividad", acti);
        cacheQuery.fromLocalDatastore();
        //cacheQuery.setCachePolicy(ParseQuery.CachePolicy.CACHE_ONLY);
        cacheQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                Log.e("ANDY PARTI", "Los partis");
                if (e == null) {
                    ArrayList<Participante> partis = new ArrayList<Participante>();
                    for (ParseObject participant : list) {
                        try {
                            Equipo indiTeam = new Equipo(participant);
                            Participante dudeBro = new Participante(indiTeam, participant.getInt("Puntos_Ganados"), participant.getInt("Puntos_Perdido"));
                            partis.add(dudeBro);
                        } catch (Exception errorcito) {
                            callback.fail(errorcito.getMessage(), null);
                        }
                    }

                    if (partis.toArray().length > 0) {
                        Log.e("ANDY PARTI", "hay mas 0 participantes");
                        callback.fail("cache", partis);
                    } else {
                        Log.e("Andy Parti", "Menos de 0 participantes");
                        callback.fail("no cache", null);
                    }
                } else {
                    Log.e("ANDY PARTIS", "LOS PARTIS EN FAIL " + e.getMessage());
                    callback.fail(e.getMessage(), null);
                }
            }
        });
    }

    public Participante hiddenCreateParticipante(Equipo e, int punt, int perds) {
        return new Actividad.Participante(e, punt, perds);
    }
}
