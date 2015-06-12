package edu.upb.omaigad.upbmatch.upb_match.bussinesslogic;

/**
 * Created by andyibanezk on 6/11/15.
 */
public class Actividad {
    private String estado;
    private String fechaUHora;
    private int id;
    private String nombreActividad;
    private String numeroParticipantes;
    private String reglas;

    private String getEstado() {
        return estado;
    }

    private String getFechaUHora() {
        return fechaUHora;
    }

    private int getID() {
        return id;
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

    public Actividad(String estado, String fechaUHora, int id, String nombreActividad, String numeroParticipantes, String reglas) {
        this.estado = estado;
        this.fechaUHora = fechaUHora;
        this.id = id;
        this.nombreActividad = nombreActividad;
        this.numeroParticipantes = numeroParticipantes;
        this.reglas = reglas;
    }
}
