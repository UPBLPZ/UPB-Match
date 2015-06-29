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

        /**
         * Crea un nuevo participante.
         *
         * @param equipo
         * @param puntaje
         * @param puntosPerdidos
         */
        Participante(Equipo equipo, int puntaje, int puntosPerdidos) {
            this.equipo = equipo;
            this.puntaje = puntaje;
            this.puntosPerdidos = puntosPerdidos;
        }

        /**
         * Retorna el equipo del participante.
         *
         * @return Equipo
         */
        public Equipo getEquipo() {
            return equipo;
        }

        /**
         * Retorna el puntaje del participante.
         *
         * @return int
         */
        public int getPuntaje() {
            return puntaje;
        }

        /**
         * Retorna los puntos perdidos.
         * @return int
         */
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
    private String fondo;
    /**
     * El estado de la actividad.
     * @return String
     */
    public String getEstado() {
        return estado;
    }

    /**
     * Fecha y hora de la actividad.
     * @return String
     */
    public String getFechaUHora() {
        return fechaUHora;
    }

    /**
     * ID Parse del objeto.
     * @return String
     */
    public String getID() {
        return this.ID;
    }

    /**
     * El nombre de la actividad.
     * @return String
     */
    public String getNombreActividad() {
        return nombreActividad;
    }

    /**
     * Retorna el número de participantes.
     * @return String
     */
    public String getNumeroParticipantes() {
        return numeroParticipantes;
    }

    /**
     * Retorna las reglas de la actividad.
     * @return String
     */
    public String getReglas() {
        return reglas;
    }

    /**
     * Retorna el string del fondo de la actividad
     * @return String
     */
    public String getFondo(){return  fondo;}

    /**
     * Crea una actividad manualmente.
     *
     * @param estado
     * @param fechaUHora
     * @param ID
     * @param nombreActividad
     * @param numeroParticipantes
     * @param reglas
     * @param fondo
     */
    public Actividad(String estado, String fechaUHora, String ID, String nombreActividad, String numeroParticipantes, String reglas, String fondo) {
        this.estado = estado;
        this.fechaUHora = fechaUHora;
        this.ID = ID;
        this.nombreActividad = nombreActividad;
        this.numeroParticipantes = numeroParticipantes;
        this.reglas = reglas;
        this.fondo = fondo;
    }

    /**
     * Crea un actividad con un objeto de Parse.
     * @param act
     */
    public Actividad(ParseObject act) {
        String tEstado = act.getString("Estado");
        String tFechaHora = act.getString("Fecha_Hora");
        String tID = act.getObjectId();
        String tNombre = act.getString("Nombre_Actividad");
        String tNumPartici = act.getString("Numero_Participantes");
        String tReglas = act.getString("Reglas");
        String tFondo = act.getString("FondoACtividad");
        this.estado = tEstado;
        this.fechaUHora = tFechaHora;
        this.ID = tID;
        this.nombreActividad = tNombre;
        this.numeroParticipantes = tNumPartici;
        this.reglas = tReglas;
    }

    /**
     * Crea una actividad vacía.
     */
    public Actividad() {

    }

    /**
     * Devuelve los participantes de esta actividad.
     * @param callback
     */
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
                            Log.e("ANDY", "PERO CREO QUE LLEGA AQUI");
                            ParseObject team = obj.getParseObject("Id_Equipo");
                            Equipo indiTeam = new Equipo(obj);
                            Log.e("ANDY INDITEAM", "OH SHIT");
                            Participante dudeBro = new Participante(indiTeam, participant.getInt("Puntos_Ganados"), participant.getInt("Puntos_Perdido"));
                            partis.add(dudeBro);
                        } catch(Exception errorcito) {
                            Log.e("ANDY EXCE", errorcito.getMessage());
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

                    callback.done(partis);
                } else {
                    Log.e("ANDY PARTICIPANTES", e.getMessage());
                    participantesCache(callback);
                }
            }
        });
    }

    /**
     * Devuelve el cache de los participantes.
     * @param callback
     */
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

    /**
     * Crea un participante (solo utilizado para debugeo.)
     * @param e
     * @param punt
     * @param perds
     * @return
     */
    public Participante hiddenCreateParticipante(Equipo e, int punt, int perds) {
        return new Actividad.Participante(e, punt, perds);
    }
}
