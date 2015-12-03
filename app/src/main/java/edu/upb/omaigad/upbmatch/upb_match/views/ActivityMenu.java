package edu.upb.omaigad.upbmatch.upb_match.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import edu.upb.omaigad.upbmatch.upb_match.R;
import edu.upb.omaigad.upbmatch.upb_match.bussinesslogic.Actividad;
import edu.upb.omaigad.upbmatch.upb_match.bussinesslogic.CustomSimpleCallback;
import edu.upb.omaigad.upbmatch.upb_match.bussinesslogic.UPBMatchApplication;

import java.util.ArrayList;


public class ActivityMenu extends Fragment {

    private TableLayout tablaActividades;
    protected CharSequence mTitle;
    protected UPBMatchApplication app;
    private SwipeRefreshLayout swipe;
    private ScrollView scroll;
    private View rootView;

    public static ActivityMenu newInstance(){
        ActivityMenu fragment = new ActivityMenu();
        return fragment;
    }

    public ActivityMenu(){
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.activity_activity_menu, container, false);
        app = (UPBMatchApplication) this.getActivity().getApplication();
        mTitle = "Actividades";
        this.getActivity().setTitle(mTitle);

        // Set up the drawer.
        scroll = (ScrollView) rootView.findViewById(R.id.scrollViewActivityMenu);

        tablaActividades = (TableLayout) rootView.findViewById(R.id.activityMenuTable);

        updateTable();

        swipe = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_container);
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

        return rootView;
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

            TableRow fila = new TableRow(rootView.getContext());

            for(int colum = 0;colum < 3;colum++){

                LinearLayout actividad = new LinearLayout(rootView.getContext());

                final int numero_actividad = contA;

                ImageView icono = new ImageView(rootView.getContext());
                TextView nombre = new TextView(rootView.getContext());

                String recurso = "drawable";
                String nombre1 = "borde_esquinas_redondas";
                int res_imagen1 = getResources().getIdentifier(nombre1, recurso, rootView.getContext().getPackageName());

                String mDrawableName = actividades.get(contA).getIcono();
                int resID = getResources().getIdentifier(mDrawableName , "drawable", rootView.getContext().getPackageName());

                actividad.setOrientation(LinearLayout.VERTICAL);
                nombre.setTextSize(10);
                nombre.setGravity(Gravity.CENTER);
                nombre.setBackgroundResource(res_imagen1);

                icono.setBackgroundResource(resID);
                icono.setTag((String) actividades.get(contA).getNombreActividad());

                icono.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), ActivityScore.class);
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
}