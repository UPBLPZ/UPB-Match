package edu.upb.omaigad.upbmatch.upb_match.bussinesslogic;

/**
 * Created by andyibanezk on 6/9/15.
 */
public class Equipo {
    private String color;
    private String nombre;
    private int puntaje;
    private String ID;
    private int puntosPerdidos;

    public Equipo(String nombre, String color, int puntaje, int puntosPerdidos, String ID) {
        this.color = color;
        this.nombre = nombre;
        this.puntaje = puntaje;
        this.ID = ID;
        this.puntosPerdidos = puntosPerdidos;
    }

    /** Made By Miguel Frade
     * Construcor witohut losing points per team
     * @param nom
     * @param Color
     * @param pts
     * @param id
     */

    public Equipo(String nom, String Color, int pts,String id){
        this.nombre=nom;
        this.color=Color;
        this.puntaje=pts;
        this.ID=id;
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

    public String getID() {
        return this.ID;
    }

    public int getPuntosPerdidos() {
        return this.puntosPerdidos;
    }
}
