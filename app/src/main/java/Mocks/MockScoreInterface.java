package Mocks;

import edu.upb.omaigad.upbmatch.upb_match.ScoreInterface;

/**
 * Created by Mauricio on 09-Jun-15.
 */
public class MockScoreInterface implements ScoreInterface {

    public String[] getCareer(){
        String[] escuelas = {"Admi","Derecho","MEE","Marketing","Economia","IE","Comu & Diseño","Comercial","DTI","Financiera"};
        return escuelas;
    }
    public String[] getScores(){
        String[] puntajes = {"150","120","120","90","90","90","60","60","60","0"};
        return puntajes;
    }
}
