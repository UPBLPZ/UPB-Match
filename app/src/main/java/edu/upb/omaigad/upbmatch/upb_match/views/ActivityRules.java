package edu.upb.omaigad.upbmatch.upb_match.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import edu.upb.omaigad.upbmatch.upb_match.R;
import edu.upb.omaigad.upbmatch.upb_match.bussinesslogic.Actividad;
import edu.upb.omaigad.upbmatch.upb_match.bussinesslogic.CustomSimpleCallback;
import edu.upb.omaigad.upbmatch.upb_match.bussinesslogic.UPBMatchApplication;

import java.util.ArrayList;

public class ActivityRules extends ActionBarActivity {

    private UPBMatchApplication app;
    private TableLayout tablaReglasActividad;
    private CharSequence mTiTle;
    private int numero_actividad;
    private SwipeRefreshLayout swipe;
    private ProgressBar loadAnimation;
    private ScrollView scroll;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        numero_actividad = intent.getIntExtra("numero_actividad", 0);
        mTiTle = intent.getCharSequenceExtra("nombre_actividad");
        setTitle("Reglamento: " + mTiTle);
        setContentView(R.layout.activity_activity_rules);
        app = (UPBMatchApplication) this.getApplication();
        scroll = (ScrollView) findViewById(R.id.scrollViewActivityRules);
        tablaReglasActividad = (TableLayout) findViewById(R.id.activityRulesTable);
        updateTable();

        swipe = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                updateTable();

            }
        });

        scroll.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {

            @Override
            public void onScrollChanged() {
                int scrollY = scroll.getScrollY();
                if (scrollY == 0) swipe.setEnabled(true);
                else swipe.setEnabled(false);

            }
        });
    }

    public void updateTable(){
        tablaReglasActividad.setVisibility(View.INVISIBLE);
        app.getActivitiesManager().getActivities(new CustomSimpleCallback<Actividad>() {

            @Override
            public void done(ArrayList<Actividad> data) {

                String mDrawableName = data.get(numero_actividad).getFondo();
                int resID = getResources().getIdentifier(mDrawableName, "drawable", getPackageName());
                ScrollView scroll = (ScrollView)findViewById(R.id.scrollViewActivityRules);
                scroll.setBackgroundResource(resID);
                createDynamicContentTable(data.get(numero_actividad));
                swipe.setRefreshing(false);
                tablaReglasActividad.setVisibility(View.VISIBLE);
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

        String recurso = "drawable";
        String nombre1 = "borde_esquinas_redondas";
        int res_imagen1 = getResources().getIdentifier(nombre1, recurso, getPackageName());

        TextView participantes = new TextView(this);
        TextView fechahora = new TextView(this);
        TextView reglamento = new TextView(this);

        participantes.setText(" Participantes: " + actividad.getNumeroParticipantes() + " ");
        fechahora.setText(" Fecha/Hora: " + actividad.getFechaUHora() + " ");
        reglamento.setText(" Reglamento:\n " + actividad.getReglas());

        participantes.setBackgroundResource(res_imagen1);
        fechahora.setBackgroundResource(res_imagen1);
        reglamento.setBackgroundResource(res_imagen1);

        participantes.setTextSize(16);
        fechahora.setTextSize(16);
        reglamento.setTextSize(16);

        TextView blank = new TextView(this);
        blank.setText(" ");
        TextView blank1 = new TextView(this);
        blank.setText(" ");
        TextView blank2 = new TextView(this);
        blank.setText(" ");
        TextView blank3 = new TextView(this);
        blank.setText(" ");

        tablaReglasActividad.addView(blank, 0);
        tablaReglasActividad.addView(blank1,1);
        tablaReglasActividad.addView(participantes, 2);
        tablaReglasActividad.addView(blank2,3);
        tablaReglasActividad.addView(fechahora,4);
        tablaReglasActividad.addView(blank3,5);
        tablaReglasActividad.addView(reglamento, 6);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
       // getMenuInflater().inflate(R.menu.menu_activity_rules, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (item.getItemId()) {
            case android.R.id.home:
                // this takes the user 'back', as if they pressed the left-facing triangle icon on the main android toolbar.
                // if this doesn't work as desired, another possibility is to call `finish()` here.
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
        //noinspection SimplifiableIfStatement
        /*if (id == R.id.action_settings) {
            return true;
        } else if(id == R.id.home){
           this.onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);*/
    }
}

