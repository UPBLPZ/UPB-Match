package edu.upb.omaigad.upbmatch.upb_match.views;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import edu.upb.omaigad.upbmatch.upb_match.R;
import edu.upb.omaigad.upbmatch.upb_match.bussinesslogic.Actividad;
import edu.upb.omaigad.upbmatch.upb_match.bussinesslogic.CustomSimpleCallback;
import edu.upb.omaigad.upbmatch.upb_match.bussinesslogic.UPBMatchApplication;

/**
 * Created by mau_a on 06-Dec-15.
 */
public class ActivityActivity extends AppCompatActivity {
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;
    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private int numero_actividad;
    protected CharSequence mTitle;
    protected UPBMatchApplication app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //toolbar = (Toolbar) findViewById(R.id.toolbar);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        Intent intent = getIntent();
        app = (UPBMatchApplication) this.getApplication();

        mTitle = intent.getStringExtra("nombre_actividad");
        numero_actividad = intent.getIntExtra("numero_actividad", 0);

        setTitle(mTitle);

        //setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setOffscreenPageLimit(2);

        mViewPager.setAdapter(mSectionsPagerAdapter);

        tabLayout.setupWithViewPager(mViewPager);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_main, menu);
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


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            switch (position){
                case 0: return ActivityActivityScore.newInstance(numero_actividad);
                case 1: return ActivityActivityRules.newInstance(numero_actividad);

            }
            return null;
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Score";
                case 1:
                    return "Reglamento";
            }
            return null;
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class ActivityActivityScore extends Fragment{
        private TableLayout tablaPuntajeActividad;
        private int numero_actividad;
        private SwipeRefreshLayout swipe;
        private ScrollView scroll;
        protected CharSequence mTitle;
        protected UPBMatchApplication app;
        private View rootView;

        public static ActivityActivityScore newInstance(int numero_actividad) {
            ActivityActivityScore fragment = new ActivityActivityScore();
            Bundle args = new Bundle();
            args.putInt("NUMERO_ACTIVIDAD",numero_actividad);
            fragment.setArguments(args);
            return fragment;
        }
        public ActivityActivityScore(){

        }
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            rootView = inflater.inflate(R.layout.activity_activity_score2, container, false);

            app = (UPBMatchApplication) this.getActivity().getApplication();
            numero_actividad = getArguments().getInt("NUMERO_ACTIVIDAD");
            scroll = (ScrollView) rootView.findViewById(R.id.scrollViewActivityScore);
            tablaPuntajeActividad = (TableLayout) rootView.findViewById(R.id.activityScoreTable);
            tablaPuntajeActividad.setVisibility(View.INVISIBLE);
            updateTable();

            swipe = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_container);
            swipe.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary);
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
        private void updateTable() {
            app.getActivitiesManager().getActivities(new CustomSimpleCallback<Actividad>() {
                @Override
                public void done(ArrayList<Actividad> actividades) {
                    String estado = actividades.get(numero_actividad).getEstado();
                    switch (estado) {
                        case "Pendiente":
                            Toast.makeText(rootView.getContext(), actividades.get(numero_actividad).getNombreActividad() + " todavia no comenzo.", Toast.LENGTH_LONG).show();
                            break;
                        case "En curso":
                            Toast.makeText(rootView.getContext(), "Esta en curso.", Toast.LENGTH_LONG).show();
                            break;
                        case "Concluida":
                            tablaPuntajeActividad.setVisibility(View.VISIBLE);
                            actividades.get(numero_actividad).getParticipantes(new CustomSimpleCallback<Actividad.Participante>() {
                                @Override
                                public void done(ArrayList<Actividad.Participante> participantes) {
                                    createDynamicContentTable(participantes);
                                }

                                @Override
                                public void fail(String failMessage, ArrayList<Actividad.Participante> cache) {
                                    if (failMessage == "cache") {
                                        createDynamicContentTable(cache);
                                        Toast.makeText(rootView.getContext(), "Error de conectividad. Los datos cargados pueden no estar actualizados.", Toast.LENGTH_LONG).show();
                                    } else {
                                        Toast.makeText(rootView.getContext(), "No se pudo cargar los participantes.", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                            break;
                        default:
                            break;
                    }
                }

                @Override
                public void fail(String failMessage, ArrayList<Actividad> cache) {
                    if (failMessage == "cache") {
                        // TODO (para Mauri) hacerlo dinámico.

                        cache.get(numero_actividad).getParticipantes(new CustomSimpleCallback<Actividad.Participante>() {
                            @Override
                            public void done(ArrayList<Actividad.Participante> participantes) {
                                createDynamicContentTable(participantes);
                            }

                            @Override
                            public void fail(String failMessage, ArrayList<Actividad.Participante> cache) {
                                if (failMessage == "cache") {
                                    createDynamicContentTable(cache);
                                    Toast.makeText(rootView.getContext(), "Error de conectividad. Los datos cargados pueden no estar actualizados.", Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(rootView.getContext(), "No se pudo cargar los participantes.", Toast.LENGTH_LONG).show();
                                }
                            }
                        });

                        Toast.makeText(rootView.getContext(), "Error de conectividad. Los datos cargados pueden no estar actualizados.", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(rootView.getContext(), "No se pudo cargar la actividad.", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }

        private void createDynamicContentTable(ArrayList<Actividad.Participante> participantes){
            //Toast.makeText(getApplicationContext(),"Dentro de la creacion de la tabla", Toast.LENGTH_SHORT).show();
            tablaPuntajeActividad.removeAllViews();
            /*String recurso = "drawable";
            String nombre1 = "borde_esquinas_redondas";
            int res_imagen1 = getResources().getIdentifier(nombre1, recurso, rootView.getContext().getPackageName());*/
            int tam = participantes.size();

            for(int cont = 0; cont < tam; cont++){


                TableRow fila = new TableRow(rootView.getContext());
                TextView equipo = new TextView(rootView.getContext());
                TextView puntajeGanado = new TextView(rootView.getContext());
                TextView puntajePerdido = new TextView(rootView.getContext());
                TextView puntajeTotal = new TextView(rootView.getContext());
                ImageView polera = new ImageView(rootView.getContext());
                int pg = participantes.get(cont).getPuntaje();
                int pp = participantes.get(cont).getPuntosPerdidos();
                int pt = pg-pp;
                String recurso = "drawable";

                String nombre = "ic_account_circle_black_36dp";

                int res_imagen = getResources().getIdentifier(nombre, recurso, rootView.getContext().getPackageName());

                polera.setBackgroundColor(Color.parseColor("#" + participantes.get(cont).getEquipo().getColor()));
                polera.setImageResource(res_imagen);
                equipo.setText(" " + participantes.get(cont).getEquipo().getNombre() + "  ");
                puntajeGanado.setText(" " + pg + " ");
                puntajePerdido.setText(" " + pp + " ");
                puntajeTotal.setText(" " + pt+" ");


                equipo.setTextSize(16);
                puntajeGanado.setTextSize(18);
                puntajePerdido.setTextSize(18);
                puntajeTotal.setTextSize(18);

                fila.addView(polera, 0);
                fila.addView(equipo, 1);
                fila.addView(puntajeGanado,2);
                fila.addView(puntajePerdido, 3);
                fila.addView(puntajeTotal,4);

                tablaPuntajeActividad.addView(fila, cont);

            }
            TableRow fila = new TableRow(rootView.getContext());
            TextView polera = new TextView(rootView.getContext());
            TextView equipo = new TextView(rootView.getContext());
            TextView puntajeGanado = new TextView(rootView.getContext());
            TextView puntajePerdido = new TextView(rootView.getContext());
            TextView puntajeTotal = new TextView(rootView.getContext());

            polera.setText(" ");
            equipo.setText(" Carrera   ");
            puntajeGanado.setText(" PG  ");
            puntajePerdido.setText(" PP  ");
            puntajeTotal.setText(" PT");

            equipo.setTextSize(16);
            puntajeGanado.setTextSize(16);
            puntajePerdido.setTextSize(16);
            puntajeTotal.setTextSize(16);

            fila.addView(polera,0);
            fila.addView(equipo,1);
            fila.addView(puntajeGanado,2);
            fila.addView(puntajePerdido,3);
            fila.addView(puntajeTotal,4);


            tablaPuntajeActividad.addView(fila,0);
        }


    }
    public static class ActivityActivityRules extends Fragment{

        private UPBMatchApplication app;
        //private TableLayout tablaReglasActividad;
        private CharSequence mTiTle;
        private int numero_actividad;
        private SwipeRefreshLayout swipe;
        private ScrollView scroll;
        private View rootView;
        private LinearLayout ll;


        public static ActivityActivityRules newInstance(int numero_actividad) {
            ActivityActivityRules fragment = new ActivityActivityRules();
            Bundle args = new Bundle();
            args.putInt("NUMERO_ACTIVIDAD",numero_actividad);
            fragment.setArguments(args);
            return fragment;
        }
        public ActivityActivityRules(){

        }
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            rootView = inflater.inflate(R.layout.activity_activity_rules2, container, false);

            app = (UPBMatchApplication) this.getActivity().getApplication();
            numero_actividad = getArguments().getInt("NUMERO_ACTIVIDAD");
            scroll = (ScrollView) rootView.findViewById(R.id.scrollViewActivityRules);
            //tablaReglasActividad = (TableLayout) rootView.findViewById(R.id.activityRulesTable);
            updateTable();
            ll = (LinearLayout) rootView.findViewById(R.id.linearlayout);
            ll.setVisibility(View.INVISIBLE);
            swipe = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_container);
            swipe.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary);
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
        public void updateTable(){
            app.getActivitiesManager().getActivities(new CustomSimpleCallback<Actividad>() {
                @Override
                public void done(ArrayList<Actividad> data) {
                    /*String mDrawableName = data.get(numero_actividad).getFondo();
                    int resID = getResources().getIdentifier(mDrawableName, "drawable", rootView.getContext().getPackageName());
                    ScrollView scroll = (ScrollView)rootView.findViewById(R.id.scrollViewActivityRules);
                    scroll.setBackgroundResource(resID);*/
                    ll.setVisibility(View.VISIBLE);
                    createDynamicContentTable(data.get(numero_actividad));
                }

                @Override
                public void fail(String failMessage, ArrayList<Actividad> cache) {
                    if (failMessage == "cache") {
                        createDynamicContentTable(cache.get(numero_actividad));
                        Toast.makeText(rootView.getContext(), "Error de conectividad. Los datos cargados pueden no estar actualizados.", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(rootView.getContext(), "No se pudo cargar el reglamento.", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }

        private void createDynamicContentTable(Actividad actividad){

            //tablaReglasActividad.removeAllViews();


            TextView participantes = (TextView) rootView.findViewById(R.id.participantes);
            TextView fechahora = (TextView) rootView.findViewById(R.id.fechahora);
            TextView reglamento = (TextView) rootView.findViewById(R.id.reglamento);

            participantes.setText(" Participantes: " + actividad.getNumeroParticipantes() + " ");
            fechahora.setText(" Fecha/Hora: " + actividad.getFechaUHora() + " ");
            reglamento.setText(" Reglamento:\n " + actividad.getReglas());



            participantes.setTextSize(16);
            fechahora.setTextSize(16);
            reglamento.setTextSize(16);
        }
    }
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }
    }
}
