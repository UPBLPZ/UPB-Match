package edu.upb.omaigad.upbmatch.upb_match.views;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;

import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import edu.upb.omaigad.upbmatch.upb_match.R;
import edu.upb.omaigad.upbmatch.upb_match.bussinesslogic.Actividad;
import edu.upb.omaigad.upbmatch.upb_match.bussinesslogic.CustomSimpleCallback;
import edu.upb.omaigad.upbmatch.upb_match.bussinesslogic.UPBMatchApplication;


public class ActivityScore extends ActionBarActivity {

    private UPBMatchApplication app;
    private TableLayout tablaPuntajeActividad;
    private Button botonReglas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_score);

        app = (UPBMatchApplication) getApplication();

        tablaPuntajeActividad = (TableLayout) findViewById(R.id.activityScoreTable);
        botonReglas = (Button) findViewById(R.id.buttonRules);
        botonReglas.setText("Ir a reglamento");
        updateTable();

    }

    private void updateTable(){
        app.getActivitiesManager().getActivities(new CustomSimpleCallback<Actividad>() {
            @Override
            public void done(ArrayList<Actividad> actividades) {
                String estado = actividades.get(12).getEstado();
                switch (estado){
                    case "Pendiente":
                        Toast.makeText(getApplicationContext(), actividades.get(12).getNombreActividad()+" todavia no comenzo.", Toast.LENGTH_LONG).show();
                        break;
                    case "En curso":
                        Toast.makeText(getApplicationContext(), "Esta en curso.", Toast.LENGTH_LONG).show();
                        break;
                    case "Concluida":
                       actividades.get(12).getParticipantes(new CustomSimpleCallback<Actividad.Participante>() {
                            @Override
                            public void done(ArrayList<Actividad.Participante> participantes) {
                                createDinamicContentTable(participantes);
                            }

                            @Override
                            public void fail(String failMessage, ArrayList<Actividad.Participante> cache) {

                            }
                        });
                        break;
                    default:
                        break;
                }
            }
            @Override
            public void fail(String failMessage, ArrayList<Actividad> cache) {
            }
        });
    }

    private void createDinamicContentTable(ArrayList<Actividad.Participante> participantes){
        //Toast.makeText(getApplicationContext(),"Dentro de la creacion de la tabla", Toast.LENGTH_SHORT).show();
        tablaPuntajeActividad.removeAllViews();
        int tam = participantes.size();

        for(int cont = 0; cont < tam; cont++){
            TableRow fila = new TableRow(this);
            TextView equipo = new TextView(this);
            equipo.setText(participantes.get(cont).getEquipo().getNombre());
            TextView puntajeGanado = new TextView(this);
            puntajeGanado.setText(participantes.get(cont).getPuntaje()+"");
            TextView puntajePerdido = new TextView(this);
            puntajePerdido.setText(participantes.get(cont).getPuntosPerdidos()+"");;
            fila.addView(equipo,0);
            fila.addView(puntajeGanado,1);
            fila.addView(puntajePerdido,2);
            tablaPuntajeActividad.addView(fila, cont);
        }
        TableRow fila = new TableRow(this);
        TextView equipo = new TextView(this);
        equipo.setText("Carrera");
        TextView puntajeGanado = new TextView(this);
        puntajeGanado.setText("Puntos ganados");
        TextView puntajePerdido = new TextView(this);
        puntajePerdido.setText("Puntos perdidos");;
        fila.addView(equipo,0);
        fila.addView(puntajeGanado,1);
        fila.addView(puntajePerdido,2);
        tablaPuntajeActividad.addView(fila,0);
    }
    public  void onClick(View view){
        Intent intent = new Intent(this, ActivityRules.class);
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

;