package edu.upb.omaigad.upbmatch.upb_match.views;

import android.app.ActionBar;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import edu.upb.omaigad.upbmatch.upb_match.R;
import edu.upb.omaigad.upbmatch.upb_match.bussinesslogic.Actividad;
import edu.upb.omaigad.upbmatch.upb_match.bussinesslogic.CustomSimpleCallback;
import edu.upb.omaigad.upbmatch.upb_match.bussinesslogic.UPBMatchApplication;

public class ActivityRules extends ActionBarActivity {

    private UPBMatchApplication app;
    private TableLayout tablaReglasActividad;
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        setContentView(R.layout.activity_activity_rules);
        app = (UPBMatchApplication) getApplication();
        tablaReglasActividad = (TableLayout) findViewById(R.id.activityRulesTable);
        updateTable();
    }

    public void updateTable(){
        app.getActivitiesManager().getActivities(new CustomSimpleCallback<Actividad>() {
            @Override
            public void done(ArrayList<Actividad> data) {
                createDinamicContentTable(data.get(14));
            }

            @Override
            public void fail(String failMessage, ArrayList<Actividad> cache) {

            }
        });
    }

    private void createDinamicContentTable(Actividad actividad){
        
        tablaReglasActividad.removeAllViews();

        TextView participantes = new TextView(this);
        participantes.setText("Participantes: 23");
        TextView fechahora = new TextView(this);
        fechahora.setText("Fecha/Hora: 22/05/2015");
        TextView reglamento = new TextView(this);
        reglamento.setText("Reglamento:\nLas reglas son");
        tablaReglasActividad.addView(participantes, 0);
        tablaReglasActividad.addView(fechahora,1);
        tablaReglasActividad.addView(reglamento,2);
        
    }
    

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_rules, menu);
        return true;
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
