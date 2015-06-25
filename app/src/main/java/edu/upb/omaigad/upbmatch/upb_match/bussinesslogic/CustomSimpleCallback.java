package edu.upb.omaigad.upbmatch.upb_match.bussinesslogic;
import java.util.ArrayList;

/**
 * Created by andyibanezk on 6/9/15.
 */
public interface CustomSimpleCallback<T> {
    /**
     * Método llamado cuando una operación de Parse termina satisfactoriamente.
     * @param data
     */
    public void done(ArrayList<T> data);

    /**
     * Método llamado cuando una operación de Parse falla, opcionalmente pasando un cache.
     * @param failMessage
     * @param cache
     */
    public void fail(String failMessage, ArrayList<T> cache);
}
