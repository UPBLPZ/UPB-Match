package edu.upb.omaigad.upbmatch.upb_match.views;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.signature.StringSignature;

import edu.upb.omaigad.upbmatch.upb_match.R;
import edu.upb.omaigad.upbmatch.upb_match.bussinesslogic.Actividad;
import edu.upb.omaigad.upbmatch.upb_match.bussinesslogic.CustomSimpleCallback;
import edu.upb.omaigad.upbmatch.upb_match.bussinesslogic.UPBMatchApplication;

import java.util.ArrayList;
import java.util.UUID;


public class ActivityMenu extends Fragment {

    private TableLayout tablaActividades;
    protected CharSequence mTitle;
    protected UPBMatchApplication app;
    private SwipeRefreshLayout swipe;
    private ScrollView scroll;
    private ProgressBar loadAnimation;
    private View rootView;
    private GridView gridView;

    public static ActivityMenu newInstance() {
        ActivityMenu fragment = new ActivityMenu();
        return fragment;
    }

    public ActivityMenu() {
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.activity_activity_menu, container, false);
        app = (UPBMatchApplication) this.getActivity().getApplication();
        mTitle = "Actividades";
        this.getActivity().setTitle(mTitle);


        // Set up the drawer.
        loadAnimation = (ProgressBar) rootView.findViewById(R.id.loadAnimation);
        gridView = (GridView) rootView.findViewById(R.id.grid);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Actividad item = (Actividad) parent.getItemAtPosition(position);
                Intent intent = new Intent(getActivity(), ActivityActivity.class);
                intent.putExtra("nombre_actividad", item.getNombreActividad());
                intent.putExtra("numero_actividad", position);
                startActivity(intent);
            }
        });
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            gridView.setNumColumns(3);
        } else {
            gridView.setNumColumns(2);
        }


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

        /*gridView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {

            @Override
            public void onScrollChanged() {
                int scrollY = gridView.getChildAt();
                if (scrollY == 0) swipe.setEnabled(true);
                else swipe.setEnabled(false);

            }
        });*/
        gridView.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {
                boolean enable = false;
                if (gridView != null && gridView.getChildCount() > 0) {
                    // check if the first item of the list is visible
                    boolean firstItemVisible = gridView.getFirstVisiblePosition() == 0;
                    // check if the top of the first item is visible
                    boolean topOfFirstItemVisible = gridView.getChildAt(0).getTop() == 0;
                    // enabling or disabling the refresh layout
                    enable = firstItemVisible && topOfFirstItemVisible;
                }
                swipe.setEnabled(enable);
            }
        });

        return rootView;
    }

    private void updateTable() {
        gridView.setVisibility(View.INVISIBLE);
        if (getActivity() != null) {
            app.getActivitiesManager().getActivities(new CustomSimpleCallback<Actividad>() {
                @Override
                public void done(ArrayList<Actividad> data) {
                    loadAnimation.setVisibility(View.GONE);
                    gridView.setVisibility(View.VISIBLE);
                    gridView.setAdapter(new ActivityMenuAdapter(rootView.getContext(), data));
                }
                @Override
                public void fail(String failMessage, ArrayList<Actividad> cache) {

                }
            });
        }
    }

    private void createDynamicContentTable(final ArrayList<Actividad> actividades) {

        tablaActividades.removeAllViews();
        int tam = actividades.size();
        int contA = 0;

        for (int filas = 0; filas < tam / 2; filas++) {

            TableRow fila = new TableRow(rootView.getContext());

            for (int colum = 0; colum < 2; colum++) {

                LinearLayout actividad = new LinearLayout(rootView.getContext());

                final int numero_actividad = contA;

                ImageView icono = new ImageView(rootView.getContext());
                TextView nombre = new TextView(rootView.getContext());

                String recurso = "drawable";
                String nombre1 = "borde_esquinas_redondas";
                int res_imagen1 = getResources().getIdentifier(nombre1, recurso, rootView.getContext().getPackageName());

                String mDrawableName = actividades.get(contA).getIcono();
                int resID = getResources().getIdentifier(mDrawableName, "drawable", rootView.getContext().getPackageName());

                actividad.setOrientation(LinearLayout.VERTICAL);
                nombre.setTextSize(10);
                nombre.setGravity(Gravity.CENTER);
                nombre.setBackgroundResource(res_imagen1);

                icono.setBackgroundResource(resID);
                icono.setTag((String) actividades.get(contA).getNombreActividad());

                icono.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), ActivityActivity.class);
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

            tablaActividades.addView(fila, filas);
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

    private class ActivityMenuAdapter extends BaseAdapter {

        private Context context;
        private ArrayList<Actividad> actividades;

        public ActivityMenuAdapter(Context context, final ArrayList<Actividad> actividades) {
            this.context = context;
            this.actividades = actividades;
        }

        @Override
        public int getCount() {
            return actividades.size();
        }

        @Override
        public Actividad getItem(int position) {
            return actividades.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View view, ViewGroup viewGroup) {

            if (view == null) {
                LayoutInflater inflater = (LayoutInflater) context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.grid_item, viewGroup, false);
            }

            ImageView imagen = (ImageView) view.findViewById(R.id.imagen);

            TextView nombre = (TextView) view.findViewById(R.id.nombre);

            final Actividad item = getItem(position);

            Glide.with(imagen.getContext())
                    .load(getResources().getIdentifier(item.getIcono() + "2", "drawable", rootView.getContext().getPackageName()))
                    .signature(new StringSignature(UUID.randomUUID().toString()))
                    .into(imagen);
            //imagen.setImageResource(getResources().getIdentifier(item.getIcono()+"2", "drawable", rootView.getContext().getPackageName()));
            nombre.setText(item.getNombreActividad());
            return view;
        }
    }
}
