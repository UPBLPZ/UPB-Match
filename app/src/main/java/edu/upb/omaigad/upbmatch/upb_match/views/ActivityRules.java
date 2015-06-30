package edu.upb.omaigad.upbmatch.upb_match.views;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ScrollView;
import android.widget.TableLayout;
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
    private CharSequence mTiTle;
    private int numero_actividad;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        numero_actividad = intent.getIntExtra("numero_actividad",0);
        mTiTle = intent.getCharSequenceExtra("nombre_actividad");
        setTitle("Reglamento: "+mTiTle);
        setContentView(R.layout.activity_activity_rules);
        app = (UPBMatchApplication) getApplication();
        tablaReglasActividad = (TableLayout) findViewById(R.id.activityRulesTable);
        updateTable();
    }

    public void updateTable(){
        app.getActivitiesManager().getActivities(new CustomSimpleCallback<Actividad>() {
            @Override
            public void done(ArrayList<Actividad> data) {
                String mDrawableName = data.get(numero_actividad).getFondo();
                int resID = getResources().getIdentifier(mDrawableName, "drawable", getPackageName());
                ScrollView scroll = (ScrollView)findViewById(R.id.scrollViewActivityRules);
                scroll.setBackgroundResource(resID);
                createDynamicContentTable(data.get(numero_actividad));
            }

            @Override
            public void fail(String failMessage, ArrayList<Actividad> cache) {
                if (failMessage == "cache") {
                    createDynamicContentTable(cache.get(numero_actividad));
                    Toast.makeText(getApplicationContext(), "Error de conectividad. Los datos cargados pueden no estar actualizados.", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "No se pudo cargar el reglamento.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void createDynamicContentTable(Actividad actividad){

        tablaReglasActividad.removeAllViews();

        TextView participantes = new TextView(this);
        participantes.setText("Participantes: "+actividad.getNumeroParticipantes());
        TextView fechahora = new TextView(this);
        fechahora.setText("Fecha/Hora: "+actividad.getFechaUHora());
        TextView reglamento = new TextView(this);
        reglamento.setText("Reglamento:\n"+actividad.getReglas());
        tablaReglasActividad.addView(participantes, 0);
        tablaReglasActividad.addView(fechahora,1);
        tablaReglasActividad.addView(reglamento,2);
        tablaReglasActividad.setBackgroundColor(Color.WHITE);

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
        } else if(id == R.id.home){
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

