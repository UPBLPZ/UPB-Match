package edu.upb.omaigad.upbmatch.upb_match.views;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import edu.upb.omaigad.upbmatch.upb_match.R;
import edu.upb.omaigad.upbmatch.upb_match.bussinesslogic.CustomSimpleCallback;
import edu.upb.omaigad.upbmatch.upb_match.bussinesslogic.Equipo;
import edu.upb.omaigad.upbmatch.upb_match.bussinesslogic.UPBMatchApplication;

import java.util.ArrayList;


public class GlobalScore extends android.support.v4.app.Fragment{

    protected CharSequence mTitle;
    protected UPBMatchApplication app;
    private TableLayout tablaPuntaje;
    private SwipeRefreshLayout swipe;
    private ProgressBar loadAnimation;
    private ScrollView scroll;
    private View rootView;

    public static GlobalScore newInstance(){
        GlobalScore fragment = new GlobalScore();
        return fragment;
    }

    public GlobalScore(){
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.activity_global_score, container, false);

        app = (UPBMatchApplication) this.getActivity().getApplication();

        mTitle = "Score Global";

        this.getActivity().setTitle(mTitle);

        scroll = (ScrollView) rootView.findViewById(R.id.scrollViewGlobalScore);
        loadAnimation = (ProgressBar) rootView.findViewById(R.id.loadAnimation);


        tablaPuntaje = (TableLayout) rootView.findViewById(R.id.scoreTable);


        updateTable();

        swipe = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_container);
        swipe.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary);

        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                updateTable();
                //swipe.setRefreshing(false);
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

    private  void updateTable(){
        tablaPuntaje.setVisibility(View.GONE);
        app.getTeamsManager().getTeams(new CustomSimpleCallback<Equipo>() {
            @Override
            public void done(ArrayList<Equipo> equipos) {
                loadAnimation.setVisibility(View.GONE);
                tablaPuntaje.setVisibility(View.VISIBLE);
                swipe.setRefreshing(false);
                createDynamicContentTable(equipos);
            }

            @Override
            public void fail(String failMessage, ArrayList<Equipo> cache) {
                Log.e("ANDY TEAMS ACT", "NOPE");
                if (failMessage == "cache") {
                    createDynamicContentTable(cache);
                    Toast.makeText(app.getApplicationContext(), "Error de conexión. Los datos mostrados pueden no estar actualizados.", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(app.getApplicationContext(), "No se pudieron cargar datos.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    private void createDynamicContentTable(ArrayList<Equipo> equipos){

        tablaPuntaje.removeAllViews();
        int tam = equipos.size();
        //Setup of the title row
        TableRow titulo = new TableRow(rootView.getContext());

        TextView titulocolor = new TextView(rootView.getContext());
        titulocolor.setText("");
        titulocolor.setTextSize(18);

        TextView tituloequipo = new TextView(rootView.getContext());
        tituloequipo.setText("  Carrera");
        tituloequipo.setTextSize(18);

        TextView titulopuntaje = new TextView(rootView.getContext());
        titulopuntaje.setText("  PT");
        titulopuntaje.setTextSize(18);

        titulo.addView(titulocolor, 0);
        titulo.addView(tituloequipo,1);
        titulo.addView(titulopuntaje,2);

        tablaPuntaje.addView(titulo, 0);

        //Setup content rows
        for(int cont = 0; cont < tam; cont++){
            TableRow fila = new TableRow(rootView.getContext());

            String recurso = "drawable";

            String nombre = "ic_account_circle_black_36dp";

            int res_imagen = getResources().getIdentifier(nombre, recurso, rootView.getContext().getPackageName());

            //fila.setBackgroundColor(Integer.parseInt(String.valueOf(0xffffffff)));
            ImageView color = new ImageView(rootView.getContext());
            color.setBackgroundColor(Color.parseColor("#" + equipos.get(cont).getColor()));
            color.setImageResource(res_imagen);

            TextView equipo = new TextView(rootView.getContext());
            equipo.setTextSize(16);
            equipo.setText("  " + equipos.get(cont).getNombre());

            TextView puntaje = new TextView(rootView.getContext());
            puntaje.setTextSize(18);
            puntaje.setText("  " + equipos.get(cont).getPuntaje() + "  ");

            fila.addView(color, 0);
            fila.addView(equipo,1);
            fila.addView(puntaje,2);
            tablaPuntaje.addView(fila, cont+1);
        }
    }
}
