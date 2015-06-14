package edu.upb.omaigad.upbmatch.upb_match.bussinesslogic;

import android.media.MediaRouter;

import com.parse.ParseQuery;

/**
 * Created by andyibanezk on 6/11/15.
 */
public class Actividad {

    public class Participante {
        Equipo equipo;
        int puntaje;
    }

    private String estado;
    private String fechaUHora;
    private String ID;
    private String nombreActividad;
    private String numeroParticipantes;
    private String reglas;

    private String getEstado() {
        return estado;
    }

    private String getFechaUHora() {
        return fechaUHora;
    }

    private String getID() {
        return this.ID;
    }

    private String getNombreActividad() {
        return nombreActividad;
    }

    private String getNumeroParticipantes() {
        return numeroParticipantes;
    }

    private String reglas() {
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

    public void getParticipantes(CustomSimpleCallback<Participante> participants) {
        ParseQuery query = ParseQuery.getQuery("Actividades");
        query.whereEqualTo("objectId", this.ID);
        query.include("Id_Equipo");
    }
}
