package Mocks;

import android.os.Handler;

import com.parse.ParseObject;
import com.parse.ParseQuery;

import edu.upb.omaigad.upbmatch.upb_match.bussinesslogic.CustomSimpleCallback;
import edu.upb.omaigad.upbmatch.upb_match.bussinesslogic.Equipo;
import edu.upb.omaigad.upbmatch.upb_match.bussinesslogic.Evento;
import edu.upb.omaigad.upbmatch.upb_match.bussinesslogic.EventsInterface;
import edu.upb.omaigad.upbmatch.upb_match.bussinesslogic.Involucrado;

/**
 * Created by Natalia Decormis on 6/19/2015.
 */
public class MockEventsManager implements EventsInterface {

    public MockEventsManager() {

    }

    @Override
    public void getEvents(String months,final CustomSimpleCallback<Evento> callback) {


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
