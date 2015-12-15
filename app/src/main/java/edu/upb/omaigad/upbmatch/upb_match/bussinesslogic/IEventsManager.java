package edu.upb.omaigad.upbmatch.upb_match.bussinesslogic;

/**
 * Created by Miguel on 17/06/2015.
 */

public interface IEventsManager {

    /**
     * Devuelve los eventos del calendario.
     *
     * @param callback resultado de la busqueda en parse
     * @param months   indice del mes a usar
     */
    public void getEvents(final int months, final CustomSimpleCallback<Evento> callback);

    /**
     * Devuelve los involucrados en un Evento.
     *
     * @param callback resultado de la busqueda en parse
     */
    public void getInvolucrados(final CustomSimpleCallback<Involucrado> callback);

    /**
     * @param callback resultado de la busqueda en parse
     */
    void getAllEvents(final CustomSimpleCallback<Evento> callback);
}
