package edu.upb.omaigad.upbmatch.upb_match.views;

import android.app.ActionBar;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
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
import android.widget.FrameLayout;
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
import java.util.Random;

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
        if (isAdded()) {
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
        Random rand = new Random();
        int eventsPM[] = new int[12];

        for (int cont = 0; cont < eventos.size(); cont++) {
            eventsPM[eventos.get(cont).getFecha().getMonth()]++;
        }


        int tam = eventos.size();
        int colorE[] = new int[tam];
        int cont = 0;
        for (int contM = 0; contM < 12; contM++) {
            if (eventsPM[contM] != 0) {
                TableRow mes = new TableRow(rootView.getContext());
                TextView titulomes = new TextView(rootView.getContext());
                titulomes.setTypeface(null, Typeface.BOLD);
                titulomes.setText(meses[contM]);
                titulomes.setTextSize(22);

                mes.addView(titulomes);
                mes.setPadding(16, 0, 0, 0);

                tablaEventos.addView(mes);
            }
            for (int contE = 0; contE < eventsPM[contM]; cont++, contE++) {
                TableRow fila = new TableRow(rootView.getContext());

                TextView titulo = new TextView(rootView.getContext());
                titulo.setTextSize(18);
                titulo.setText("" + eventos.get(cont).getTitulo());
                titulo.setTypeface(null, Typeface.BOLD);

                TextView descripcion = new TextView(rootView.getContext());
                descripcion.setText("" + eventos.get(cont).getDescripcion());
                descripcion.setPadding(0,0,16,0);




                TextView dia = new TextView(rootView.getContext());
                if (eventos.get(cont).getDia() < 10) {
                    dia.setText("  " + eventos.get(cont).getDia());
                } else {
                    dia.setText("" + eventos.get(cont).getDia());
                }
                dia.setTextSize(20);
                dia.setPadding(0, 0, 16, 0);
                dia.setGravity(Gravity.RIGHT);
                dia.setTypeface(null, Typeface.BOLD);

                TextView hora = new TextView(rootView.getContext());
                hora.setText("" + eventos.get(cont).getHora());
                hora.setGravity(Gravity.RIGHT);
                LinearLayout h = new LinearLayout(rootView.getContext());
                LinearLayout v = new LinearLayout(rootView.getContext());
                //
                GradientDrawable gd = new GradientDrawable();

                // Specify the shape of drawable
                gd.setShape(GradientDrawable.RECTANGLE);
                if(isAdded()){
                    int r;
                    if(cont == 0){
                       r = rand.nextInt(11)+1;
                    }else{
                        do{
                            r = rand.nextInt(11)+1;
                        }while(r == colorE[cont-1]);
                    }
                    colorE[cont] = r;
                    int res_imagen = getResources().getIdentifier("color"+r, "color", rootView.getContext().getPackageName());
                    int res_imagen1 = getResources().getIdentifier("color"+r+"l", "color", rootView.getContext().getPackageName());
                    gd.setColor(getResources().getColor(res_imagen));
                    titulo.setTextColor(getResources().getColor(res_imagen1));
                    descripcion.setTextColor(getResources().getColor(res_imagen1));
                    hora.setTextColor(getResources().getColor(res_imagen1));
                    gd.setStroke(2,getResources().getColor(res_imagen));
                }
                gd.setCornerRadius(2.0f);
                v.setBackground(gd);
                //
                v.setPadding(16, 16, 16, 16);
                v.addView(titulo, 0);
                v.addView(descripcion, 1);
                //descripcion.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, (float) .0));
                v.addView(hora, 2);
                h.addView(dia, 0);
                h.addView(v, 1);
                //
                h.setOrientation(LinearLayout.HORIZONTAL);
                v.setOrientation(LinearLayout.VERTICAL);

                fila.setPadding(32, 8, 16, 8);
                fila.addView(h);
                //fila.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, (float) .0));
                v.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, (float) .0));
                tablaEventos.addView(fila);
            }
        }
    }

}
