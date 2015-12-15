package edu.upb.omaigad.upbmatch.upb_match.views;

import android.app.ActionBar;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.Layout;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
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
    private String[] meses = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};
    private int am;
    private SwipeRefreshLayout swipe;
    private ScrollView scroll;
    private View rootView;
    private ProgressBar loadAnimation;

    public static Calendar newInstance() {
        Calendar fragment = new Calendar();
        return fragment;
    }

    public Calendar() {
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
        loadAnimation = (ProgressBar) rootView.findViewById(R.id.loadAnimation);

        tablaEventos = (TableLayout) rootView.findViewById(R.id.calendarTable);

        //Setup buttons
        updateCalendar();

        swipe = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_container);
        swipe.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary);
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                updateCalendar();
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

    private void updateCalendar() {
        tablaEventos.removeAllViews();
        refreshTable();
    }

    private void refreshTable() {
        tablaEventos.setVisibility(View.INVISIBLE);
        if (getActivity() != null) {
            app.getEventsManager().getAllEvents(new CustomSimpleCallback<Evento>() {
                @Override
                public void done(ArrayList<Evento> data) {
                    tablaEventos.setVisibility(View.VISIBLE);
                    loadAnimation.setVisibility(View.GONE);
                    swipe.setRefreshing(false);
                    createDynamicContentTable(data);
                }
                @Override
                public void fail(String failMessage, ArrayList<Evento> cache) {

                }
            });
        }
    }

    private void createDynamicContentTable(ArrayList<Evento> eventos) {
        int eventsPM[] = new int[12];
        for (int cont = 0; cont < eventos.size(); cont++) {
            eventsPM[eventos.get(cont).getFecha().getMonth()]++;
        }


        int tam = eventos.size();
        int cont = 0;
        for (int contM = 0; contM < 12; contM++) {
            if (eventsPM[contM] != 0) {
                TableRow mes = new TableRow(rootView.getContext());
                TextView titulomes = new TextView(rootView.getContext());
                titulomes.setText(meses[contM]);
                titulomes.setTextSize(22);

                mes.addView(titulomes);
                mes.setPadding(8, 0, 0, 0);
                tablaEventos.addView(mes);
            }
            for (int contE = 0; contE < eventsPM[contM]; cont++, contE++) {
                TableRow fila = new TableRow(rootView.getContext());

                TextView titulo = new TextView(rootView.getContext());
                titulo.setText("" + eventos.get(cont).getTitulo());

                TextView descripcion = new TextView(rootView.getContext());
                descripcion.setText("" + eventos.get(cont).getDescripcion());


                TextView dia = new TextView(rootView.getContext());
                if (eventos.get(cont).getDia() < 10) {
                    dia.setText("  " + eventos.get(cont).getDia());
                } else {
                    dia.setText("" + eventos.get(cont).getDia());
                }
                dia.setTextSize(18);
                dia.setPadding(0, 0, 16, 0);
                dia.setGravity(Gravity.RIGHT);

                TextView hora = new TextView(rootView.getContext());
                hora.setText("" + eventos.get(cont).getHora());
                hora.setGravity(Gravity.RIGHT);
                LinearLayout h = new LinearLayout(rootView.getContext());
                LinearLayout v = new LinearLayout(rootView.getContext());

                v.addView(titulo, 0);
                v.addView(descripcion, 1);
                v.addView(hora, 2);
                h.addView(dia, 0);
                h.addView(v, 1);
                h.setOrientation(LinearLayout.HORIZONTAL);
                v.setOrientation(LinearLayout.VERTICAL);

                fila.setPadding(16, 0, 0, 0);
                fila.addView(h);
                tablaEventos.addView(fila);
            }
        }
    }

}
