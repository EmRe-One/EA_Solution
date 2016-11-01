package tr.easolution.meinturnier.lib.Turnier;

import java.util.Enumeration;

/**
 * Created by Emre Ak on 27.10.2016.
 */

public enum Turniertyp {
    // mit Turniertyp.values() kann man alle elemente aus der liste abfangen

    LIGA ("Liga"),
    GRUPPEN ("Gruppen"),
    KO_SYSTEM ("KO-Runde"),
    GRUPPE_KO_SYSTEM ("Gruppen + KO-Runde");

    private String bezeichnung;

    Turniertyp(String bezeichnung){
        this.bezeichnung = bezeichnung;
    }

    public String valueOf(){
        return this.bezeichnung;
    }

    public static Turniertyp getTyp(String typ){
        switch (typ){
            case "Liga":
                return LIGA;
            case "Gruppen":
                return GRUPPEN;
            case "KO-Runde":
                return KO_SYSTEM;
            default:
                return GRUPPE_KO_SYSTEM;
        }
    }
}
