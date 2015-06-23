package edu.upb.omaigad.upbmatch.upb_match.bussinesslogic;
import android.app.Application;
import com.parse.*;

import Mocks.MockIEventsManager;

/**
 * Created by andyibanezk on 6/8/15.
 */

public class UPBMatchApplication extends Application {

    private TeamsManager teamsManager;
    private ActivitiesManager activitiesManager;
    private IEventsManager eventsManager;

    public void onCreate() {
        super.onCreate();
        // Enable Local Datastore.
        //Parse.enableLocalDatastore(this);
        Parse.initialize(this, "vCDljkDZu04YLITBMCjZdWngHE4RU0ojdqmySUd4", "jwHg4psPxJfToPQk4lVj2rPztrD3Q13Io2KmoUzv");

        // Inicializando singletons.
        teamsManager = new TeamsManager();
        activitiesManager = new ActivitiesManager();
        eventsManager = new MockIEventsManager();
    }

    public TeamsManager getTeamsManager() {
        return teamsManager;
    }

    public ActivitiesManager getActivitiesManager() { return activitiesManager; }
    public IEventsManager getEventsManager() {return eventsManager;}

}
