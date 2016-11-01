package tr.easolution.meinturnier.lib.Turnier;

/**
 * Created by Emre Ak on 29.10.2016.
 */
public enum TurnierHinRueck {
    // mit Turniertyp.values() kann man alle elemente aus der liste abfangen

    HIN("Hinrunde"),
    HIN_RUECK ("Hin- und Rückrunde");

    private String bezeichnung;

    TurnierHinRueck(String bezeichnung){
        this.bezeichnung = bezeichnung;
    }

    public String valueOf(){
        return this.bezeichnung;
    }

    public static TurnierHinRueck getTyp(String typ){
        switch (typ){
            case "Hinrunde":
                return HIN;
            case "Hin- und Rückrunde":
                return HIN_RUECK;
            default:
                return HIN;
        }
    }
}