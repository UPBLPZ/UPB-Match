package edu.upb.omaigad.upbmatch.upb_match.bussinesslogic;

/**
 * Created by Miguel on 17/06/2015.
 */

public interface EventsInterface {

    /**
     * METIDI BASE PARA JALAR DATOS
     * @param callback
     */
    public void getEvents(final CustomSimpleCallback<Evento> callback);
    public void getInvolucrados(final CustomSimpleCallback<Involucrado> callback);
}
