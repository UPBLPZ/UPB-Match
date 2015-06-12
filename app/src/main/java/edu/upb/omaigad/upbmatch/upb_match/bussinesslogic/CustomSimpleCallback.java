package edu.upb.omaigad.upbmatch.upb_match.bussinesslogic;
import java.util.ArrayList;

/**
 * Created by andyibanezk on 6/9/15.
 */
public interface CustomSimpleCallback<T> {
    public void done(ArrayList<T> data);
    public void fail(String failMessage, ArrayList<T> cache);
}
