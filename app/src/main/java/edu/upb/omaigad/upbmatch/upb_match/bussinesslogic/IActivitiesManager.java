package edu.upb.omaigad.upbmatch.upb_match.bussinesslogic;

/**
 * Created by andyibanezk on 6/22/15.
 */
public interface IActivitiesManager {
    /**
     * Devuelve todas las actividades.
     * @param callback
     */
    public void getActivities(final CustomSimpleCallback<Actividad> callback);
}
