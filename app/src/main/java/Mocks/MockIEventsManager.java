package Mocks;

import android.os.Handler;

import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.Random;

import edu.upb.omaigad.upbmatch.upb_match.bussinesslogic.CustomSimpleCallback;
import edu.upb.omaigad.upbmatch.upb_match.bussinesslogic.Equipo;
import edu.upb.omaigad.upbmatch.upb_match.bussinesslogic.Evento;
import edu.upb.omaigad.upbmatch.upb_match.bussinesslogic.IEventsManager;
import edu.upb.omaigad.upbmatch.upb_match.bussinesslogic.Involucrado;

/**
 * Created by Natalia Decormis on 6/19/2015.
 */
public class MockIEventsManager implements IEventsManager{

    public MockIEventsManager() {

    }
    @Override
    public void getEvents(final String month, final CustomSimpleCallback<Evento> callback) {
        String[] meses = {"febrero","marzo","abril","mayo","junio","julio","agosto","septiembre","octubre"};

        Handler handler = new Handler();
        Random rnd = new Random();

        int time = (int)(rnd.nextDouble() * 290 + 10);

        if (Math.random() < 0.80) {
            handler.postDelayed(
                    new Runnable() {

                        public void run() {

                            ArrayList<Evento> eventos = new ArrayList<Evento>();
                            switch (month) {

                                case "junio":
                                    eventos.add(new Evento("kasjjsv","Futbol", 16, "10:00", "Se jugara la final"));
                                    eventos.add(new Evento("sfdshg","Pelota quemada", 20, "12:30", "Dos partidos simultaneos de semfinal"));
                                    eventos.add(new Evento("sdfgsdfh","Reunion capitanes", 29, "19:00", "Para tratar temas sobre el festival de danza"));
                                    break;

                                case "julio":
                                    // no hay eventos en julio!!! para probar
                                    break;

                                case "agosto":
                                    eventos.add(new Evento("esrjsg","Volley", 1, "10:00", "Empiezan partidos"));
                                    eventos.add(new Evento("nvfdj","Futbol Tenis", 10, "12:30", "Dos partidos de semfinal"));
                                    eventos.add(new Evento("wrheg","Volley", 30, "19:00", "se juega la Final"));
                                    break;

                                case "septiembre":
                                    eventos.add(new Evento("dfbhr","Spirit Week", 2, "10:00", "Una semana todos disfrazados segun el tema"));
                                    eventos.add(new Evento("trrte","Recoleccion de peluches", 20, "12:30", "Busca el saco de recoleccion de tu carrera"));
                                    eventos.add(new Evento("shrgt","Reunion capitanes", 29, "19:00", "Para tratar temas sobre el festival de Selfie UPB"));
                                    break;

                                case "octubre":
                                            eventos.add(new Evento("afbdbg","Jalar la cuerda", 24, "12:30", "Dia del Upbmatch"));
                                    eventos.add(new Evento("adgefh","Encostalados", 24, "19:00", "Dia del Upbmatch"));
                                    break;
                                default:
                                    break;
                            }

                            callback.done(eventos);

                        }
                    },
                    time
            );
        } else {
            handler.postDelayed(
                    new Runnable() {

                        public void run() {

                            callback.fail("El mock manager dice que ha fallado", null);
                        }
                    },
                    time
            );

        }

    }

    @Override
    public void getInvolucrados(CustomSimpleCallback<Involucrado> callback) {

    }
}
