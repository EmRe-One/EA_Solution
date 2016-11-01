package tr.easolution.meinturnier.lib;

import android.content.Context;
import android.util.JsonReader;
import android.util.JsonToken;
import android.util.JsonWriter;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import tr.easolution.meinturnier.lib.Turnier.Gruppen;
import tr.easolution.meinturnier.lib.Turnier.Spieler;
import tr.easolution.meinturnier.lib.Turnier.Team;
import tr.easolution.meinturnier.lib.Turnier.Turnier;
import tr.easolution.meinturnier.lib.Turnier.TurnierHinRueck;
import tr.easolution.meinturnier.lib.Turnier.Turniertyp;

/**
 * Created by Emre Ak on 28.10.2016.
 */

public class Storage {
    private static final String FILENAME = "turniere.json";

    public static List<Turnier> turniere;

    public static void init(Context context) {

        File[] files = context.getFilesDir().listFiles();
        if (files.length != 0){
            turniere = loadTurniere(context);

            if(turniere == null){
                turniere = new ArrayList<>();
            }
        }

    }

    public static void add(Context c, Turnier t){
        turniere.add(t);
        saveTurniere(c);
    }

    /**
     *
     *
     * @param context
     */
    public static void saveTurniere(Context context) {
        try {
            FileOutputStream outputStream;
            outputStream = context.openFileOutput(FILENAME, Context.MODE_PRIVATE);
            JsonWriter writer = new JsonWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            writer.setIndent("  ");

            writeTurniereArray(writer, turniere);

            writer.close();
            Log.d("File-Dir", context.getFilesDir().getAbsolutePath());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void writeTurniereArray(JsonWriter writer, List<Turnier> turnier) throws IOException {
        writer.beginArray();
        if(turnier != null && !turnier.isEmpty()){
            for (Turnier t : turnier) {
                writeTurnier(writer, t);
            }
        }else{
            writer.nullValue();
        }

        writer.endArray();
    }

    private static void writeTurnier(JsonWriter writer, Turnier turnier) throws IOException {
        writer.beginObject();
        writer.name("id").value(turnier.getId());
        writer.name("mTurnierName").value(turnier.getTurnierName());
        writer.name("mTurnierTyp").value(turnier.getTurniertyp().valueOf());
        writer.name("mTurnierHinRueck").value(turnier.getTurnierHinRueck().valueOf());
        writer.name("mGruppen");
        writeGruppenArray(writer, turnier.getGruppen());
        writer.endObject();
    }

    private static void writeGruppenArray(JsonWriter writer, List<Gruppen> gruppen) throws IOException {
        writer.beginArray();
        if (gruppen != null && !gruppen.isEmpty()) {
            for (Gruppen g : gruppen) {
                writeGruppen(writer, g);
            }
        } else {
            writer.nullValue();
        }

        writer.endArray();
    }

    private static void writeGruppen(JsonWriter writer, Gruppen gruppen) throws IOException {
        writer.beginObject();
        writer.name("id").value(gruppen.getId());
        writer.name("mGruppenName").value(gruppen.getGruppenName());
        writer.name("mMannschaften");
        writeTeamArray(writer, gruppen.getMannschaften());
        writer.endObject();
    }

    private static void writeTeamArray(JsonWriter writer, List<Team> teams) throws IOException {
        writer.beginArray();
        if (teams != null && !teams.isEmpty()) {
            for (Team t : teams ) {
                writeTeam(writer, t);
            }
        } else {
            writer.nullValue();
        }

        writer.endArray();
    }

    private static void writeTeam(JsonWriter writer, Team team) throws IOException {
        writer.beginObject();
        writer.name("id").value(team.getId());
        writer.name("mTeamName").value(team.getTeamName());
        writer.name("mGesamtspiele").value(team.getGesamtspiele());
        writer.name("mSiege").value(team.getSiege());
        writer.name("mNiederlage").value(team.getNiederlagen());
        writer.name("mTore").value(team.getTore());
        writer.name("mGegenTore").value(team.getGegenTore());
        writer.name("mLogoResource").value(team.getLogoResource());
        writer.name("mSpieler");
        writeSpielerArray(writer, team.getSpieler());
        writer.endObject();
    }

    public static void writeSpielerArray(JsonWriter writer, List<Spieler> spieler) throws IOException {
        writer.beginArray();
        if (spieler != null && !spieler.isEmpty()) {
            for (Spieler s : spieler) {
                writeSpieler(writer, s);
            }
        } else {
            writer.nullValue();
        }

        writer.endArray();
    }

    public static void writeSpieler(JsonWriter writer, Spieler spieler) throws IOException {
        writer.beginObject();
        writer.name("id").value(spieler.getId());
        writer.name("mName").value(spieler.getName());
        writer.name("mTore").value(spieler.getTore());
        writer.endObject();
    }


    /**
     * JsonLoader
     *
     * @param context
     * @return
     */
    private static List<Turnier> loadTurniere(Context context) {
        try {
            FileInputStream inputStream;
            inputStream = context.openFileInput(FILENAME);

            JsonReader reader = new JsonReader(new InputStreamReader(inputStream, "UTF-8"));

            Log.d("File-Dir", context.getFilesDir().getAbsolutePath());
            return readTurnierArray(reader);
        } catch (Exception e) {
            e.getStackTrace();
        }
        return null;
    }

    private static List<Turnier> readTurnierArray(JsonReader reader) throws IOException {
        List<Turnier> turniereArray = new ArrayList<>();

        reader.beginArray();
        while (reader.hasNext()) {
            turniereArray.add(readTurnier(reader));
        }
        reader.endArray();

        return turniereArray;
    }

    private static Turnier readTurnier(JsonReader reader) throws IOException {
        long id = -1;
        String mTurnierName = null;
        Turniertyp mTurniertyp = Turniertyp.GRUPPE_KO_SYSTEM;
        TurnierHinRueck mTurnierHinRueck = TurnierHinRueck.HIN;
        List<Gruppen> mGruppen = null;

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();

            switch (name) {
                case "id":
                    id = reader.nextLong();
                    break;
                case "mTurnierName":
                    mTurnierName = reader.nextString();
                    break;
                case "mTurnierTyp":
                    mTurniertyp = Turniertyp.getTyp(reader.nextString());
                    break;
                case "mTurnierHinRueck":
                    mTurnierHinRueck = TurnierHinRueck.getTyp(reader.nextString());
                    break;
                case "mGruppen":
                    mGruppen = readGruppenArray(reader);
                    break;
                default:
                    reader.skipValue();
            }
        }
        reader.endObject();

        return new Turnier(id, mTurnierName, mTurniertyp, mTurnierHinRueck, mGruppen);
    }

    private static List<Gruppen> readGruppenArray(JsonReader reader) throws IOException {
        List<Gruppen> gruppen = new ArrayList<>();

        reader.beginArray();
        while (reader.hasNext()) {
            if(reader.peek() != JsonToken.NULL){
                gruppen.add(readGruppe(reader));
            }else{
                reader.skipValue();
            }
        }
        reader.endArray();

        return gruppen;
    }

    private static Gruppen readGruppe(JsonReader reader) throws IOException {
        long id = -1;
        String mGruppenName = null;
        List<Team> teams = null;

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            switch (name) {
                case "id":
                    id = reader.nextLong();
                    break;
                case "mGruppenName":
                    mGruppenName = reader.nextString();
                    break;
                case "mMannschaften":
                    teams = readTeamArray(reader);
                    break;
                default:
                    reader.skipValue();
            }
        }
        reader.endObject();

        return new Gruppen(id, mGruppenName, teams);
    }

    private static List<Team> readTeamArray(JsonReader reader) throws IOException {
        List<Team> teams = new ArrayList<>();

        reader.beginArray();
        while (reader.hasNext()) {
            if(reader.peek() != JsonToken.NULL){
                teams.add(readTeam(reader));
            }else{
                reader.skipValue();
            }
        }
        reader.endArray();

        return teams;
    }

    private static Team readTeam(JsonReader reader) throws IOException {
        long id = -1;
        String mTeamName = null;
        int gesamtspiele = -1,
                siege = -1,
                niederlagen = -1,
                tore = -1,
                gegentore = -1,
                mLogoResource = -1;
        List<Spieler> spieler = null;

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            switch (name) {
                case "id":
                    id = reader.nextLong();
                    break;
                case "mTeamName":
                    mTeamName = reader.nextString();
                    break;
                case "mGesamtspiele":
                    gesamtspiele = reader.nextInt();
                    break;
                case "mSiege":
                    siege = reader.nextInt();
                    break;
                case "mNiederlage":
                    niederlagen = reader.nextInt();
                    break;
                case "mTore":
                    tore = reader.nextInt();
                    break;
                case "mGegenTore":
                    gegentore = reader.nextInt();
                    break;
                case "mLogoResource":
                    mLogoResource = reader.nextInt();
                    break;
                case "mSpieler":
                    spieler = readSpielerArray(reader);
                    break;
                default:
                    reader.skipValue();
            }
        }
        reader.endObject();

        return new Team(id, mTeamName, gesamtspiele, siege, niederlagen, tore, gegentore, mLogoResource, spieler);
    }

    private static List<Spieler> readSpielerArray(JsonReader reader) throws IOException {
        List<Spieler> spieler = new ArrayList<>();
        reader.beginArray();
        while (reader.hasNext()) {
            if(reader.peek() != JsonToken.NULL){
                spieler.add(readSpieler(reader));
            }else{
                reader.skipValue();
            }
        }
        reader.endArray();

        return spieler;
    }

    private static Spieler readSpieler(JsonReader reader) throws IOException {
        long id = -1;
        String mName = null;
        int mTore = -1;

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            switch (name) {
                case "id":
                    id = reader.nextLong();
                    break;
                case "mName":
                    mName = reader.nextString();
                    break;
                case "mTore":
                    mTore = reader.nextInt();
                    break;
                default:
                    reader.skipValue();
            }
        }
        reader.endObject();

        return new Spieler(id, mName, mTore);
    }

}
