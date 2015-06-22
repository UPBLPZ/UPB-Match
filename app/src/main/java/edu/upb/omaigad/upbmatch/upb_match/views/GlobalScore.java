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
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

import Mocks.MockScoreInterface;
import edu.upb.omaigad.upbmatch.upb_match.R;
import edu.upb.omaigad.upbmatch.upb_match.bussinesslogic.Actividad;
import edu.upb.omaigad.upbmatch.upb_match.bussinesslogic.CustomSimpleCallback;
import edu.upb.omaigad.upbmatch.upb_match.bussinesslogic.Equipo;
import edu.upb.omaigad.upbmatch.upb_match.bussinesslogic.UPBMatchApplication;


public class GlobalScore extends BaseActivity{

    private TableLayout tablaPuntaje;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_global_score);

        mNavigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(R.id.navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout));

        // Set up the table
        tablaPuntaje = (TableLayout) findViewById(R.id.scoreTable);
        updateTable();
    }
    private  void updateTable(){
        app.getTeamsManager().getTeams(new CustomSimpleCallback<Equipo>() {
            @Override
            public void done(ArrayList<Equipo> equipos) {
                createDinamicContentTable(equipos);
            }

            @Override
            public void fail(String failMessage, ArrayList<Equipo> cache) {
                Log.e("callback", "NOen el done");
            }
        });
    }
    private void createDinamicContentTable(ArrayList<Equipo> equipos){
        tablaPuntaje.removeAllViews();
        int tam = equipos.size();
        for(int cont = 0; cont < tam; cont++){
            TableRow fila = new TableRow(this);
            TextView equipo = new TextView(this);
            equipo.setText(equipos.get(cont).getNombre());
            TextView puntaje = new TextView(this);
            puntaje.setText(equipos.get(cont).getPuntaje()+"");
            fila.addView(equipo,0);
            fila.addView(puntaje,1);
            tablaPuntaje.addView(fila, cont);
        }

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
}
