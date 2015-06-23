package edu.upb.omaigad.upbmatch.upb_match.views;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.codec.binary.StringUtils;

import org.w3c.dom.Text;

import java.util.ArrayList;

import edu.upb.omaigad.upbmatch.upb_match.R;
import edu.upb.omaigad.upbmatch.upb_match.bussinesslogic.CustomSimpleCallback;
import edu.upb.omaigad.upbmatch.upb_match.bussinesslogic.Evento;

public class Calendar extends BaseActivity {

    private TableLayout tablaEventos;
    private Button pmonth;
    private Button amonth;
    private Button nmonth;
    private String[] meses = {"enero","febrero","marzo","abril","mayo","junio","julio","agosto","septiembre","octubre","noviembre","diciembre"};
    private int am;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        mNavigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();
        am = java.util.Calendar.getInstance().get(java.util.Calendar.MONTH);
        // Set up the drawer.
        mNavigationDrawerFragment.setUp(R.id.navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout));
        tablaEventos = (TableLayout) findViewById(R.id.calendarTable);
        pmonth = (Button) findViewById(R.id.bPMonth);
        amonth = (Button) findViewById(R.id.bAMonth);
        nmonth = (Button) findViewById(R.id.bNMonth);


        updateCalendar();
    }

    private void updateCalendar(){
        tablaEventos.removeAllViews();
        Log.e("am vale",am+"");
        if(am > 1 && am < 9){
            pmonth.setText(meses[am-1]);
            amonth.setText(meses[am]);
            nmonth.setText(meses[am+1]);
            Log.e("am vale", am + "");
            refreshMonth();
        }else if(am == 1){
            pmonth.setText("");
            amonth.setText(meses[am]);
            nmonth.setText(meses[am+1]);
            Log.e("am vale", am + "");
            refreshMonth();
        }else if(am == 9){
            pmonth.setText(meses[am-1]);
            amonth.setText(meses[am]);
            nmonth.setText("");
            Log.e("am vale", am + "");
            refreshMonth();
        }else if(am < 1){
            am = 1;
            updateCalendar();
        }else{
            am = 9;
            updateCalendar();
        }
    }

    private void refreshMonth(){
        app.getEventsManager().getEvents(meses[am], new CustomSimpleCallback<Evento>() {
            @Override
            public void done(ArrayList<Evento> data) {
                if(data.size() != 0){
                    createDinamicContentTable(data);
                }else{
                    Toast.makeText(getApplicationContext(), "No hay eventos en este mes.", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void fail(String failMessage, ArrayList<Evento> cache) {
                Toast.makeText(getApplicationContext(),"No se pudo cargar los eventos.", Toast.LENGTH_LONG).show();
            }
        });
    }
    private void createDinamicContentTable(ArrayList<Evento> eventos){
        int tam = eventos.size();
        Log.e("en create",tam+"");
        for(int cont = 0; cont < tam; cont++){
            TableRow fila = new TableRow(this);
            LinearLayout h = new LinearLayout(this);
            LinearLayout h2 = new LinearLayout(this);
            LinearLayout v = new LinearLayout(this);
            TextView dia = new TextView(this);
            TextView hora = new TextView(this);
            TextView titulo = new TextView(this);
            TextView descripcion = new TextView(this);

            h.setOrientation(LinearLayout.HORIZONTAL);
            h2.setOrientation(LinearLayout.HORIZONTAL);
            v.setOrientation(LinearLayout.VERTICAL);

            dia.setText(eventos.get(cont).getDia());
            hora.setText("  "+eventos.get(cont).getHora());
            titulo.setText("  " + eventos.get(cont).getTitulo());
            descripcion.setText(eventos.get(cont).getDescripcion());

            h2.addView(hora,0);
            h2.addView(titulo,1);
            v.addView(h2,0);
            v.addView(descripcion,1);
            h.addView(dia,0);
            h.addView(v,1);
            fila.addView(h,0);

            tablaEventos.addView(fila,cont);
        }
    }

    public void onClickPM(View view){
        am-=1;
        Log.e("am vale",am+"");
        updateCalendar();
    }
    public void onClickAM(View view){
        Log.e("am vale",am+"");
        updateCalendar();
    }
    public void onClickNM(View view){
        am+=1;
        Log.e("am vale",am+"");
        updateCalendar();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_calendar, menu);
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
