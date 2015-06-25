package edu.upb.omaigad.upbmatch.upb_match.bussinesslogic;

import android.util.Log;

import com.parse.ParseObject;

/**
 * Created by andyibanezk on 6/9/15.
 */
public class Equipo {
    private String color;
    private String nombre;
    private int puntaje;
    private String ID;

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

    public Equipo(ParseObject team) throws Exception {
        //ParseObject team = obj.getParseObject("Id_Equipo");
        String tName = team.fetchIfNeeded().getString("Nombre_Equipo");
        String tColor = team.fetchIfNeeded().getString("Color");
        int tScore = team.fetchIfNeeded().getInt("Puntaje");
        String id = team.fetchIfNeeded().getObjectId();
        this.nombre = tName;
        this.color = tColor;
        this.puntaje = tScore;
        this.ID = id;
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

}
