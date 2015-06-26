package edu.upb.omaigad.upbmatch.upb_match.bussinesslogic;

/**
 * Created by Miguel on 17/06/2015.
 */

public interface IEventsManager {

    /**
     * Devuelve los eventos del calendario.
     * @param callback
     * @param months
     */
    public void getEvents(final int months, final CustomSimpleCallback<Evento> callback);

    /**
     * Devuelve los involucrados en un Evento.
     * @param callback
     */
    public void getInvolucrados(final CustomSimpleCallback<Involucrado> callback);
}
