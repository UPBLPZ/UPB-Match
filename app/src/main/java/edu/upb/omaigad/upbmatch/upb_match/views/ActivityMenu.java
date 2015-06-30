package edu.upb.omaigad.upbmatch.upb_match.views;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

import edu.upb.omaigad.upbmatch.upb_match.R;
import edu.upb.omaigad.upbmatch.upb_match.bussinesslogic.Actividad;
import edu.upb.omaigad.upbmatch.upb_match.bussinesslogic.CustomSimpleCallback;

public class ActivityMenu extends BaseActivity{

    private TableLayout tablaActividades;
    private SwipeRefreshLayout swipe;
    private ScrollView scroll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_menu);
        mNavigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = "Actividades";
        setTitle(mTitle);
        // Set up the drawer.
        mNavigationDrawerFragment.setUp(R.id.navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout));
        scroll = (ScrollView) findViewById(R.id.scrollViewActivityMenu);
        tablaActividades = (TableLayout) findViewById(R.id.activityMenuTable);


        updateTable();

        swipe = (SwipeRefreshLayout) findViewById(R.id.swipe_container);

        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                updateTable();
                swipe.setRefreshing(false);
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
    private void updateTable(){
        app.getActivitiesManager().getActivities(new CustomSimpleCallback<Actividad>() {
            @Override
            public void done(ArrayList<Actividad> data) {
                createDynamicContentTable(data);
            }

            @Override
            public void fail(String failMessage, ArrayList<Actividad> cache) {

            }
        });
    }
    private void createDynamicContentTable(final ArrayList<Actividad> actividades){

        int tam = actividades.size();
        int contA = 0;

        for(int filas = 0;filas < tam/3;filas++){

            TableRow fila = new TableRow(this);

            for(int colum = 0;colum < 3;colum++){

                LinearLayout actividad = new LinearLayout(this);

                final int numero_actividad = contA;

                ImageView icono = new ImageView(this);
                TextView nombre = new TextView(this);

                String recurso = "drawable";
                String nombre1 = "borde_esquinas_redondas";
                int res_imagen1 = getResources().getIdentifier(nombre1, recurso, getPackageName());

                String mDrawableName = actividades.get(contA).getIcono();
                int resID = getResources().getIdentifier(mDrawableName , "drawable", getPackageName());

                actividad.setOrientation(LinearLayout.VERTICAL);
                nombre.setTextSize(10);
                nombre.setGravity(Gravity.CENTER);
                nombre.setBackgroundResource(res_imagen1);

                icono.setBackgroundResource(resID);
                icono.setTag((String) actividades.get(contA).getNombreActividad());

                icono.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(ActivityMenu.this, ActivityScore.class);
                        intent.putExtra("nombre_actividad", (String) v.getTag());
                        intent.putExtra("numero_actividad", numero_actividad);
                        startActivity(intent);
                    }
                });
                nombre.setText(actividades.get(contA).getNombreActividad());
                actividad.addView(icono, 0);
                actividad.addView(nombre, 1);
                fila.addView(actividad, colum);

                contA++;
            }

            tablaActividades.addView(fila,filas);
        }
        /*if(contA != tam-1){
                TableRow fila = new TableRow(this);
                for(int colum = 0;colum < 3;colum++){
                    LinearLayout actividad = new LinearLayout(this);
                    actividad.setOrientation(LinearLayout.VERTICAL);
                    ImageView icono = new ImageView(this);
                    TextView nombre = new TextView(this);

                    String mDrawableName = "ic_launcher";
                    int resID = getResources().getIdentifier(mDrawableName , "drawable", getPackageName());

                    icono.setBackgroundResource(resID);
                    nombre.setText(actividades.get(contA).getNombreActividad());
                    actividad.addView(icono, 0);
                    actividad.addView(nombre, 1);
                    fila.addView(actividad,colum);
                    contA++;
                }
                tablaActividades.addView(fila);
        }*/
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }
}