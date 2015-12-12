package edu.upb.omaigad.upbmatch.upb_match.views;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import edu.upb.omaigad.upbmatch.upb_match.R;
import edu.upb.omaigad.upbmatch.upb_match.bussinesslogic.CustomSimpleCallback;
import edu.upb.omaigad.upbmatch.upb_match.bussinesslogic.Evento;
import edu.upb.omaigad.upbmatch.upb_match.bussinesslogic.UPBMatchApplication;

import java.util.ArrayList;

public class Calendar extends Fragment {

    private TableLayout tablaEventos;
    protected CharSequence mTitle;
    protected UPBMatchApplication app;
    private String[] meses = {"Enero","Febrero","Marzo","Abril","Mayo","Junio","Julio","Agosto","Septiembre","Octubre","Noviembre","Diciembre"};
    private int am;
    private SwipeRefreshLayout swipe;
    private ScrollView scroll;
    private View rootView;

    public static Calendar newInstance(){
        Calendar fragment = new Calendar();
        return fragment;
    }

    public Calendar(){
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.activity_calendar, container, false);
        app = (UPBMatchApplication) this.getActivity().getApplication();
        mTitle = "Calendario";
        this.getActivity().setTitle(mTitle);
        am = java.util.Calendar.getInstance().get(java.util.Calendar.MONTH);
        // Set up the drawer.
        scroll = (ScrollView) rootView.findViewById(R.id.scrollViewCalendar);


        tablaEventos = (TableLayout) rootView.findViewById(R.id.calendarTable);

        //Setup buttons
        updateCalendar();

        swipe = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_container);
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                updateCalendar();
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

    private void updateCalendar(){
        tablaEventos.removeAllViews();
        for(int currentM = 0;currentM < 12;currentM++){
            refreshMonth(currentM);
        }
    }

    private void refreshMonth(final int currentM){
        app.getEventsManager().getEvents(currentM, new CustomSimpleCallback<Evento>() {
            @Override
            public void done(ArrayList<Evento> data) {
                if(data.size() != 0){
                    createDynamicContentTable(data,currentM);
                }
            }
            @Override
            public void fail(String failMessage, ArrayList<Evento> cache) {
                Toast.makeText(app.getApplicationContext(), "No se pudo cargar los eventos.", Toast.LENGTH_LONG).show();
            }
        });
    }
    private void createDynamicContentTable(ArrayList<Evento> eventos,int currentM){

        //Setup month title
        TableRow mes = new TableRow(rootView.getContext());
        TextView titulomes = new TextView(rootView.getContext());
        titulomes.setText(meses[currentM]);
        titulomes.setTextSize(20);
        mes.addView(titulomes);
        tablaEventos.addView(mes);

        int tam = eventos.size();
        for(int cont = 0; cont < tam; cont++){
            TableRow fila = new TableRow(rootView.getContext());

            TextView titulo = new TextView(rootView.getContext());
            titulo.setText(""+eventos.get(cont).getTitulo());

            TextView descripcion = new TextView(rootView.getContext());
            descripcion.setText(""+eventos.get(cont).getDescripcion());

            TextView dia = new TextView(rootView.getContext());
            dia.setText(""+eventos.get(cont).getDia());

            TextView hora = new TextView(rootView.getContext());
            hora.setText(""+eventos.get(cont).getHora());

            LinearLayout h = new LinearLayout(rootView.getContext());
            LinearLayout v = new LinearLayout(rootView.getContext());
            Log.e("dia",eventos.get(cont).getDia()+"" );
            Log.e("mes",eventos.get(cont).getFecha()+"" );
            v.addView(titulo,0);
            v.addView(descripcion,1);
            v.addView(hora,2);
            h.addView(dia,0);
            h.addView(v, 1);
            h.setOrientation(LinearLayout.HORIZONTAL);
            v.setOrientation(LinearLayout.VERTICAL);

            fila.addView(h);
            tablaEventos.addView(fila);
        }
    }

}
