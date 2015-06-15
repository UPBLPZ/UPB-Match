package edu.upb.omaigad.upbmatch.upb_match.views;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

import edu.upb.omaigad.upbmatch.upb_match.R;
import edu.upb.omaigad.upbmatch.upb_match.bussinesslogic.Actividad;
import edu.upb.omaigad.upbmatch.upb_match.bussinesslogic.CustomSimpleCallback;
import edu.upb.omaigad.upbmatch.upb_match.bussinesslogic.UPBMatchApplication;


public class ActivityScore extends ActionBarActivity {

    private UPBMatchApplication app;
    private TableLayout tablaPuntajeActividad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_score);

        app = (UPBMatchApplication) getApplication();

        tablaPuntajeActividad = (TableLayout) findViewById(R.id.activityScoreTable);

        updateTable();

    }

    private void updateTable(){
        app.getActivitiesManager().getActivities(new CustomSimpleCallback<Actividad>() {
            @Override
            public void done(ArrayList<Actividad> actividades) {
                String estado = actividades.get(0).getEstado();
                switch (estado){
                    case "Pendiente":
                        break;
                    case "En curso":
                        break;
                    case "Concluida":
                        actividades.get(0).getParticipantes(new CustomSimpleCallback<Actividad.Participante>() {
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
                Pendiente
                        Concluida
                        En curso
            }

            @Override
            public void fail(String failMessage, ArrayList<Actividad> cache) {
            }
        });
    }

    private void createDinamicContentTable(ArrayList<Actividad.Participante> participantes){
        tablaPuntajeActividad.removeAllViews();
        int tam = participantes.size();
        for(int cont = 0; cont < tam; cont++){
            TableRow fila = new TableRow(this);
            TextView equipo = new TextView(this);
            equipo.setText(participantes.get(cont).getNombre());
            TextView puntaje = new TextView(this);
            puntaje.setText(participantes.get(cont).()+"");
            fila.addView(equipo,0);
            fila.addView(puntaje,1);
            tablaPuntaje.addView(fila, cont);
        }
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
