package edu.upb.omaigad.upbmatch.upb_match;
import android.app.Application;
import com.parse.*;
import Mocks.MockScoreInterface;
/**
 * Created by andyibanezk on 6/8/15.
 */

public class UPBMatchApplication extends Application {

    public TeamsManager teamsManager;

    //public ScoreInterface scoreInterface;

    public void onCreate() {
        super.onCreate();
        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "vCDljkDZu04YLITBMCjZdWngHE4RU0ojdqmySUd4", "jwHg4psPxJfToPQk4lVj2rPztrD3Q13Io2KmoUzv");

        // Inicializando singletons.
        teamsManager = new TeamsManager();
        //scoreInterface = new MockScoreInterface();

    }


}
