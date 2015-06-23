package edu.upb.omaigad.upbmatch.upb_match.bussinesslogic;

/**
 * Created by Miguel on 17/06/2015.
 */

public interface IEventsManager {

    /**
     * METIDI BASE PARA JALAR DATOS
     * @param callback
     * @param months
     */
    public void getEvents(final String months, final CustomSimpleCallback<Evento> callback);
    public void getInvolucrados(final CustomSimpleCallback<Involucrado> callback);
}
