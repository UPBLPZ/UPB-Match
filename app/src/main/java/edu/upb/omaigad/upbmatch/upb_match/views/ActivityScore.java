package edu.upb.omaigad.upbmatch.upb_match.views;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;

import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import edu.upb.omaigad.upbmatch.upb_match.R;
import edu.upb.omaigad.upbmatch.upb_match.bussinesslogic.Actividad;
import edu.upb.omaigad.upbmatch.upb_match.bussinesslogic.CustomSimpleCallback;


public class ActivityScore extends BaseActivity {


    private TableLayout tablaPuntajeActividad;
    private Button botonReglas;
    private int numero_actividad;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_score);
        Intent intent = getIntent();

        mNavigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = intent.getStringExtra("nombre_actividad");
        numero_actividad = intent.getIntExtra("numero_actividad",0);

        setTitle(mTitle);

        /*Log.e("esta en la activdad",(String)mTitle);
        Log.e("nactividad",numero_actividad+"");*/
        // Set up the drawer.
        mNavigationDrawerFragment.setUp(R.id.navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout));

        tablaPuntajeActividad = (TableLayout) findViewById(R.id.activityScoreTable);
        botonReglas = (Button) findViewById(R.id.buttonRules);
        botonReglas.setText("Ir a reglamento");
        updateTable();

    }

    private void updateTable() {
        app.getActivitiesManager().getActivities(new CustomSimpleCallback<Actividad>() {
            @Override
            public void done(ArrayList<Actividad> actividades) {
                String mDrawableName = actividades.get(numero_actividad).getFondo();
                int resID = getResources().getIdentifier(mDrawableName, "drawable", getPackageName());
                ScrollView scroll = (ScrollView)findViewById(R.id.scrollViewActivityScore);
                scroll.setBackgroundResource(resID);
                String estado = actividades.get(numero_actividad).getEstado();
                switch (estado) {
                    case "Pendiente":
                        Toast.makeText(getApplicationContext(), actividades.get(numero_actividad).getNombreActividad() + " todavia no comenzo.", Toast.LENGTH_LONG).show();
                        break;
                    case "En curso":
                        Toast.makeText(getApplicationContext(), "Esta en curso.", Toast.LENGTH_LONG).show();
                        break;
                    case "Concluida":
                        actividades.get(numero_actividad).getParticipantes(new CustomSimpleCallback<Actividad.Participante>() {
                            @Override
                            public void done(ArrayList<Actividad.Participante> participantes) {
                                createDynamicContentTable(participantes);
                            }

                            @Override
                            public void fail(String failMessage, ArrayList<Actividad.Participante> cache) {
                                if (failMessage == "cache") {
                                    createDynamicContentTable(cache);
                                    Toast.makeText(getApplicationContext(), "Error de conectividad. Los datos cargados pueden no estar actualizados.", Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(getApplicationContext(), "No se pudo cargar los participantes.", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void fail(String failMessage, ArrayList<Actividad> cache) {
                if (failMessage == "cache") {
                    // TODO (para Mauri) hacerlo dinámico.

                    cache.get(numero_actividad).getParticipantes(new CustomSimpleCallback<Actividad.Participante>() {
                        @Override
                        public void done(ArrayList<Actividad.Participante> participantes) {
                            createDynamicContentTable(participantes);
                        }

                        @Override
                        public void fail(String failMessage, ArrayList<Actividad.Participante> cache) {
                            if (failMessage == "cache") {
                                createDynamicContentTable(cache);
                                Toast.makeText(getApplicationContext(), "Error de conectividad. Los datos cargados pueden no estar actualizados.", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(getApplicationContext(), "No se pudo cargar los participantes.", Toast.LENGTH_LONG).show();
                            }
                        }
                    });

                    Toast.makeText(getApplicationContext(), "Error de conectividad. Los datos cargados pueden no estar actualizados.", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(),"No se pudo cargar la actividad.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void createDynamicContentTable(ArrayList<Actividad.Participante> participantes){
        //Toast.makeText(getApplicationContext(),"Dentro de la creacion de la tabla", Toast.LENGTH_SHORT).show();
        tablaPuntajeActividad.removeAllViews();
        String recurso = "drawable";
        String nombre1 = "borde_esquinas_redondas";
        int res_imagen1 = getResources().getIdentifier(nombre1, recurso, getPackageName());
        int tam = participantes.size();

        for(int cont = 0; cont < tam; cont++){

            String nombre = "polera";
            TableRow fila = new TableRow(this);
            TextView equipo = new TextView(this);
            TextView puntajeGanado = new TextView(this);
            TextView puntajePerdido = new TextView(this);
            TextView puntajeTotal = new TextView(this);
            ImageView polera = new ImageView(this);
            int pg = participantes.get(cont).getPuntaje();
            int pp = participantes.get(cont).getPuntosPerdidos();
            int pt = pg-pp;
            int res_imagen = getResources().getIdentifier(nombre, recurso, getPackageName());


            polera.setBackgroundColor(Color.parseColor("#" + participantes.get(cont).getEquipo().getColor()));
            polera.setImageResource(res_imagen);
            equipo.setText(" " + participantes.get(cont).getEquipo().getNombre() + "  ");
            puntajeGanado.setText(" " + pg + " ");
            puntajePerdido.setText(" " + pp + " ");
            puntajeTotal.setText(" " + pt + " ");

            equipo.setBackgroundResource(res_imagen1);
            puntajeGanado.setBackgroundResource(res_imagen1);
            puntajePerdido.setBackgroundResource(res_imagen1);
            puntajeTotal.setBackgroundResource(res_imagen1);

            equipo.setTextSize(16);
            puntajeGanado.setTextSize(18);
            puntajePerdido.setTextSize(18);
            puntajeTotal.setTextSize(18);

            fila.addView(polera, 0);
            fila.addView(equipo, 1);
            fila.addView(puntajeGanado,2);
            fila.addView(puntajePerdido, 3);
            fila.addView(puntajeTotal,4);

            tablaPuntajeActividad.addView(fila, cont);

        }
        TableRow fila = new TableRow(this);
        TextView polera = new TextView(this);
        TextView equipo = new TextView(this);
        TextView puntajeGanado = new TextView(this);
        TextView puntajePerdido = new TextView(this);
        TextView puntajeTotal = new TextView(this);

        polera.setText(" ");
        equipo.setText(" Carrera   ");
        puntajeGanado.setText(" PG  ");
        puntajePerdido.setText(" PP  ");
        puntajeTotal.setText(" PT  ");

        equipo.setTextSize(16);
        puntajeGanado.setTextSize(16);
        puntajePerdido.setTextSize(16);
        puntajeTotal.setTextSize(16);

        equipo.setBackgroundResource(res_imagen1);
        puntajeGanado.setBackgroundResource(res_imagen1);
        puntajePerdido.setBackgroundResource(res_imagen1);
        puntajeTotal.setBackgroundResource(res_imagen1);

        TextView blank = new TextView(this);
        blank.setText(" ");
        TextView blank1 = new TextView(this);
        blank.setText(" ");

        fila.addView(polera,0);
        fila.addView(equipo,1);
        fila.addView(puntajeGanado,2);
        fila.addView(puntajePerdido,3);
        fila.addView(puntajeTotal,4);

        tablaPuntajeActividad.addView(blank,0);
        tablaPuntajeActividad.addView(blank1,1);
        tablaPuntajeActividad.addView(fila,2);
    }
    public  void onClick(View view){
        Intent intent = new Intent(this, ActivityRules.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra("nombre_actividad",mTitle);
        intent.putExtra("numero_actividad",numero_actividad);
        startActivity(intent);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_score, menu);
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
