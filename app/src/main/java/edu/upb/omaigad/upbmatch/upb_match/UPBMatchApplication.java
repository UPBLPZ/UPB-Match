package edu.upb.omaigad.upbmatch.upb_match;
import android.app.Application;
import com.parse.*;

/**
 * Created by andyibanezk on 6/8/15.
 */

public class UPBMatchApplication extends Application {

    private TeamsManager teamsManager;

    public void onCreate() {
        super.onCreate();
        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "vCDljkDZu04YLITBMCjZdWngHE4RU0ojdqmySUd4", "jwHg4psPxJfToPQk4lVj2rPztrD3Q13Io2KmoUzv");

        // Inicializando singletons.
        teamsManager = new TeamsManager();

    }

    public TeamsManager getTeamsManager() {
        return teamsManager;
    }
}
