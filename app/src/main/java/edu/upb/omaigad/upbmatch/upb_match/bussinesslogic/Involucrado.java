package edu.upb.omaigad.upbmatch.upb_match.bussinesslogic;

/**
 * Created by Miguel on 18/06/2015.
 */
public class Involucrado {

    /**
     * Atributos de la clase
     */
    private Equipo equipo;
    private Evento evento;


    public Involucrado(Equipo eq, Evento ev) {
        this.equipo = eq;
        this.evento = ev;

    }

    public Involucrado() {

    }

    /**
     * Metodo que retorna el equipo invlucrado en una actividad
     * @return
     */
    public Equipo getEquipo() {return equipo; }
    public Evento getEvento() {return evento; }


}
