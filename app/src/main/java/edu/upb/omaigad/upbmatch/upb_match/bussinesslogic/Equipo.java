package edu.upb.omaigad.upbmatch.upb_match.bussinesslogic;

/**
 * Created by andyibanezk on 6/9/15.
 */
public class Equipo {
    private String color;
    private String nombre;
    private int puntaje;

    public Equipo(String color, String nombre, int puntaje) {
        this.color = color;
        this.nombre = nombre;
        this.puntaje = puntaje;
    }

    public String getColor() {
        return color;
    }

    public String getNombre() {
        return nombre;
    }

    public int getPuntaje() {
        return puntaje;
    }
}
