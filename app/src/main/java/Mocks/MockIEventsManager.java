package Mocks;

import android.os.Handler;

import com.parse.ParseObject;
import com.parse.ParseQuery;

import edu.upb.omaigad.upbmatch.upb_match.bussinesslogic.CustomSimpleCallback;
import edu.upb.omaigad.upbmatch.upb_match.bussinesslogic.Evento;
import edu.upb.omaigad.upbmatch.upb_match.bussinesslogic.IEventsManager;
import edu.upb.omaigad.upbmatch.upb_match.bussinesslogic.Involucrado;

/**
 * Created by Natalia Decormis on 6/19/2015.
 */
public class MockIEventsManager implements IEventsManager {

    public MockIEventsManager() {

    }

    @Override
    public void getEvents(final CustomSimpleCallback<Evento> callback) {


        (new Handler()).postDelayed(
                new Runnable() {
                    @Override
                    public void run() {

                        // preparar datos

                        // ...

                        // enviar

                        //...
                        callback.done(null);

                    }
                },
                200  // callback in 200 ms
        );

    }

    @Override

    public void getInvolucrados(final CustomSimpleCallback<Involucrado> callback) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Involucrados");
    }

}
