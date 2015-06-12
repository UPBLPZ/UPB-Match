package edu.upb.omaigad.upbmatch.upb_match.views;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.widget.TextView;

import java.util.ArrayList;

import Mocks.MockScoreInterface;
import edu.upb.omaigad.upbmatch.upb_match.R;
import edu.upb.omaigad.upbmatch.upb_match.bussinesslogic.CustomSimpleCallback;
import edu.upb.omaigad.upbmatch.upb_match.bussinesslogic.Equipo;
import edu.upb.omaigad.upbmatch.upb_match.bussinesslogic.UPBMatchApplication;


public class GlobalScore extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;
    private TextView puntajes[];
    private TextView escuelas[];
    private UPBMatchApplication app;
    private MockScoreInterface mockScoreInterface = new MockScoreInterface();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_global_score);
        app = (UPBMatchApplication) getApplication();
        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
        // Se up the textviews
        puntajes = new TextView[mockScoreInterface.getScores().length];
        escuelas = new TextView[mockScoreInterface.getCareer().length];
        puntajes[0] = (TextView) findViewById(R.id.puntaje1);
        puntajes[1] = (TextView) findViewById(R.id.puntaje2);
        puntajes[2] = (TextView) findViewById(R.id.puntaje3);
        puntajes[3] = (TextView) findViewById(R.id.puntaje4);
        puntajes[4] = (TextView) findViewById(R.id.puntaje5);
        puntajes[5] = (TextView) findViewById(R.id.puntaje6);
        puntajes[6] = (TextView) findViewById(R.id.puntaje7);
        puntajes[7] = (TextView) findViewById(R.id.puntaje8);
        puntajes[8] = (TextView) findViewById(R.id.puntaje9);
        puntajes[9] = (TextView) findViewById(R.id.puntaje10);

        escuelas[0] = (TextView) findViewById(R.id.escuela1);
        escuelas[1] = (TextView) findViewById(R.id.escuela2);
        escuelas[2] = (TextView) findViewById(R.id.escuela3);
        escuelas[3] = (TextView) findViewById(R.id.escuela4);
        escuelas[4] = (TextView) findViewById(R.id.escuela5);
        escuelas[5] = (TextView) findViewById(R.id.escuela6);
        escuelas[6] = (TextView) findViewById(R.id.escuela7);
        escuelas[7] = (TextView) findViewById(R.id.escuela8);
        escuelas[8] = (TextView) findViewById(R.id.escuela9);
        escuelas[9] = (TextView) findViewById(R.id.escuela10);

        for(int cont = 0; cont < mockScoreInterface.getCareer().length;cont++){
            puntajes[cont].setText(mockScoreInterface.getScores()[cont]);
            escuelas[cont].setText(mockScoreInterface.getCareer()[cont]);
        }
        update();
    }
    protected  void update(){
        app.getTeamsManager().getTeams(new CustomSimpleCallback<Equipo>() {
            @Override
            public void done(ArrayList<Equipo> data) {
                for(int cont = 0; cont < data.size();cont++){
                    Log.e(data.get(cont).getNombre(), "");
                    escuelas[cont].setText(data.get(cont).getNombre());

                    puntajes[cont].setText(data.get(cont).getPuntaje()+"");
                }
            }

            @Override
            public void fail(String failMessage, ArrayList<Equipo> cache) {
                Log.e("callback","NOen el done");
                puntajes[1].setText("No jala");
                escuelas[1].setText("No jala");
            }
        });
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
                .commit();
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.global_score, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_global_score, container, false);
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((GlobalScore) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }

}
