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
    private Button pmonth;
    private Button amonth;
    private Button nmonth;
    private String[] meses = {"enero","febrero","marzo","abril","mayo","junio","julio","agosto","septiembre","octubre","noviembre","diciembre"};
    private int am;
    private SwipeRefreshLayout swipe;
    private ScrollView scroll;

    public static Calendar newInstance(){
        Calendar fragment = new Calendar();
        return fragment;
    }

    public Calendar(){
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_calendar, container, false);
        app = (UPBMatchApplication) this.getActivity().getApplication();
        mTitle = "Calendario";
        this.getActivity().setTitle(mTitle);
        am = java.util.Calendar.getInstance().get(java.util.Calendar.MONTH);
        // Set up the drawer.
        scroll = (ScrollView) rootView.findViewById(R.id.scrollViewCalendar);


        tablaEventos = (TableLayout) rootView.findViewById(R.id.calendarTable);

        //Setup buttons
        pmonth = (Button) rootView.findViewById(R.id.bPMonth);
        amonth = (Button) rootView.findViewById(R.id.bAMonth);
        nmonth = (Button) rootView.findViewById(R.id.bNMonth);

        pmonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                am-=1;
                Log.e("am vale", am + "");
                updateCalendar();
            }
        });
        amonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("am vale", am + "");
                updateCalendar();
            }
        });
        nmonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                am+=1;
                Log.e("am vale", am + "");
                updateCalendar();
            }
        });




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
        Log.e("am vale", am + "");
        if(am > 1 && am < 9){
            pmonth.setText(meses[am-1]);
            amonth.setText(meses[am]);
            nmonth.setText(meses[am+1]);
            pmonth.setVisibility(View.VISIBLE);
            nmonth.setVisibility(View.VISIBLE);
            Log.e("am vale", am + "");
            refreshMonth();
        }else if(am == 1){
            pmonth.setText("");
            amonth.setText(meses[am]);
            nmonth.setText(meses[am+1]);
            pmonth.setVisibility(View.INVISIBLE);
            Log.e("am vale", am + "");
            refreshMonth();
        }else if(am == 9){
            pmonth.setText(meses[am-1]);
            amonth.setText(meses[am]);
            nmonth.setText("");
            nmonth.setVisibility(View.INVISIBLE);
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
        app.getEventsManager().getEvents(am, new CustomSimpleCallback<Evento>() {
            @Override
            public void done(ArrayList<Evento> data) {
                if(data.size() != 0){
                    createDynamicContentTable(data);
                }else{
                    Toast.makeText(app.getApplicationContext(), "No hay eventos en este mes.", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void fail(String failMessage, ArrayList<Evento> cache) {
                Toast.makeText(app.getApplicationContext(), "No se pudo cargar los eventos.", Toast.LENGTH_LONG).show();
            }
        });
    }
    private void createDynamicContentTable(ArrayList<Evento> eventos){
        int tam = eventos.size();
        Log.e("en create", tam + "");
        tablaEventos.setBackgroundColor(Color.WHITE);
        for(int cont = 0; cont < tam; cont++){
            TableRow fila = new TableRow(this.getActivity());
            String recurso = "drawable";
            String nombre1 = "borde_esquinas_redondas";
            int res_imagen1 = getResources().getIdentifier(nombre1, recurso, this.getActivity().getPackageName());
       //     fila.setBaselineAlignedChildIndex(2);

            LinearLayout h = new LinearLayout(this.getActivity());
            LinearLayout h2 = new LinearLayout(this.getActivity());
            LinearLayout v = new LinearLayout(this.getActivity());
            TextView dia = new TextView(this.getActivity());
            dia.setBackgroundResource(res_imagen1);
            TextView hora = new TextView(this.getActivity());
            hora.setBackgroundResource(res_imagen1);
            TextView titulo = new TextView(this.getActivity());
            titulo.setBackgroundResource(res_imagen1);
            TextView descripcion = new TextView(this.getActivity());

            h.setOrientation(LinearLayout.HORIZONTAL);
            h2.setOrientation(LinearLayout.HORIZONTAL);
            v.setOrientation(LinearLayout.VERTICAL);

            dia.setText(" " + eventos.get(cont).getDia() + " ");
        //    dia.setBackgroundColor(Color.WHITE);
            hora.setText("  " + eventos.get(cont).getHora() + " ");
      //      hora.setBackgroundColor(Color.WHITE);
            titulo.setText("  " + eventos.get(cont).getTitulo() + " ");
      //      titulo.setBackgroundColor(Color.WHITE);

            descripcion.setText(eventos.get(cont).getDescripcion());
        //    descripcion.setBackgroundColor(Color.WHITE);


            h2.addView(hora,0);
            h2.addView(titulo,1);
            v.addView(h2,0);
            v.addView(descripcion,1);
            h.addView(dia,0);
            h.addView(v,1);
            fila.addView(h,0);

            tablaEventos.addView(fila, cont);
            tablaEventos.setBackgroundResource(res_imagen1);
        }
    }

    /*public void onClickPM(View view){
        am-=1;
        Log.e("am vale", am + "");
        updateCalendar();
    }
    public void onClickAM(View view){
        Log.e("am vale", am + "");
        updateCalendar();
    }
    public void onClickNM(View view){
        am+=1;
        Log.e("am vale", am + "");
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


        return super.onOptionsItemSelected(item);
    }*/
}
