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
    private int dia;
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

    public Evento(String id, String titulo, int dia,String hora,String desc){
        this.EventID = id;
        this.nombreEvento=titulo;
        this.dia=dia;
        this.hora=hora;
        this.descripcion=desc;

    }

    public Evento(){

    }

    /**
     * getters para la clase eventos y retornar sus atributos
     * @return
     */
    public String getId(){return EventID;}
    public String getTitulo() {return nombreEvento; }
    public Date getFecha() {return fecha ; }
    public String getDescripcion() {return descripcion; }
    public int getDia(){return dia;}
    public String getHora() {return hora;}

}
