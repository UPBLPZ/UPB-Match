package edu.upb.omaigad.upbmatch.upb_match.bussinesslogic;

/**
 * Created by Mauricio on 09-Jun-15.
 */
public interface ITeamsManager {

    /**
     * Devuelve todos los equipos, ordenados por score mayor a score menor.
     * @param callback
     */
    public void getTeams(final CustomSimpleCallback<Equipo> callback);

}