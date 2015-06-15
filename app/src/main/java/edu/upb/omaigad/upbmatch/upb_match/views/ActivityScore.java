package edu.upb.omaigad.upbmatch.upb_match.views;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_score);

        app = (UPBMatchApplication) getApplication();

        tablaPuntajeActividad = (TableLayout) findViewById(R.id.activityScoreTable);

        updateTable();

    }

    private void updateTable(){
        Log.e("Upadate table","");
        app.getActivitiesManager().getActivities(new CustomSimpleCallback<Actividad>() {
            @Override
            public void done(ArrayList<Actividad> actividades) {
                String estado = actividades.get(1).getEstado();
                if(estado == null){
                    Toast.makeText(getApplicationContext(), estado, Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(), "Estado esta null", Toast.LENGTH_SHORT).show();
                }

                /*switch (estado){
                    case "Pendiente":
                        Toast.makeText(getApplicationContext(), "Todavia no comenzo.", Toast.LENGTH_SHORT).show();
                        break;
                    case "En curso":
                        Toast.makeText(getApplicationContext(), "Esta en curso.", Toast.LENGTH_SHORT).show();
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
                }*/
            }
            @Override
            public void fail(String failMessage, ArrayList<Actividad> cache) {
            }
        });
    }

    private void createDinamicContentTable(ArrayList<Actividad.Participante> participantes){
        tablaPuntajeActividad.removeAllViews();
        Log.e("Logre entrar","");
        int tam = participantes.size();

        for(int cont = 1; cont < tam; cont++){
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
