package edu.upb.omaigad.upbmatch.upb_match.bussinesslogic;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Miguel on 17/06/2015.
 */
public class Evento {

    /**
     * atributos de la clase en funcion al parse
     */
    private String EventID;
    private String nombreEvento;
    private String dia;
    private String hora;
    private Date fecha;
    private String descripcion;

    /**
     * Construcotor de la clase
     * @param id
     * @param titulo
     * @param fecha
     * @param desc
     */
    public Evento(String id, String titulo, Date fecha,String desc){
       this.EventID = id;
        this.nombreEvento=titulo;
        this.fecha=fecha;
        this.descripcion=desc;

    }

    /**
     * Crea un nuevo evento.
     * @param id
     * @param titulo
     * @param dia
     * @param hora
     * @param desc
     */
    public Evento(String id, String titulo, String dia,String hora,String desc){
        this.EventID = id;
        this.nombreEvento=titulo;
        this.dia=dia;
        this.hora=hora;
        this.descripcion=desc;

    }

    /**
     * Crea un evento vacío.
     */
    public Evento(){

    }

    /**
     * getters para la clase eventos y retornar sus atributos
     * @return
     */
    public String getId(){return EventID;}

    /**
     * Retorna el título del evento.
     * @return String
     */
    public String getTitulo() {return nombreEvento; }

    /**
     * Retorna la fecha del evento.
     * @return Date
     */
    public Date getFecha() {return fecha ; }

    /**
     * Retorna la descripción del evento.
     * @return String
     */
    public String getDescripcion() {return descripcion; }

    /**
     * Retorna el día del evento.
     * @return String
     */
    public String getDia(){return dia;}

    /**
     * Retorna el día del evento.
     * @return
     */
    public String getHora() {return hora;}

}
