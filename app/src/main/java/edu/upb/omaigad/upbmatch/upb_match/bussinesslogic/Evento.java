package edu.upb.omaigad.upbmatch.upb_match.bussinesslogic;

/**
 * Created by Miguel on 17/06/2015.
 */
public class Evento {

    /**
     * atributos de la clase en funcion al parse
     */
    private String nombreEvento;
    private String fecha;
    private String descripcion;

    /**
     * Construcore de la clase
     * @param titulo titulo del evento
     * @param fecha fecha del evento

     * @param desc decripcion del evento
     */
    public Evento(String titulo, String fecha,String desc){
        this.nombreEvento=titulo;
        this.fecha=fecha;
        this.descripcion=desc;

    }

    public Evento(){

    }


    /**
     * getters para la clase eventos y retornar sus atributos
     * @return
     */
    public String getTitulo() {return nombreEvento; }
    public String getFecha() {return fecha; }
    public String getDescripcion() {return descripcion; }






}
