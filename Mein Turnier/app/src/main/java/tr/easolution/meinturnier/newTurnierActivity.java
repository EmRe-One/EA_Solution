package tr.easolution.meinturnier;

import android.content.Intent;
import android.location.Location;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import tr.easolution.meinturnier.lib.Storage;
import tr.easolution.meinturnier.lib.Turnier.Gruppen;
import tr.easolution.meinturnier.lib.Turnier.Turnier;
import tr.easolution.meinturnier.lib.Turnier.TurnierHinRueck;
import tr.easolution.meinturnier.lib.Turnier.Turniertyp;

public class newTurnierActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private String turniername;
    private Turniertyp turniertyp = Turniertyp.GRUPPE_KO_SYSTEM;
    private TurnierHinRueck turnierHinRueck = TurnierHinRueck.HIN;
    private List<Gruppen> gruppen = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_turnier);

        final EditText edit = (EditText) findViewById(R.id.edt_new_turnier);
        Spinner sp_turniertyp = (Spinner) findViewById(R.id.sp_turniertyp);

        sp_turniertyp.setOnItemSelectedListener(this);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.turniertyp, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        sp_turniertyp.setAdapter(adapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.create_new_turnier);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edit.getText().length() > 3){
                    turniername = edit.getText().toString();

                    Storage.add(getBaseContext(), new Turnier(new Date().getTime(),turniername, turniertyp, turnierHinRueck));
                    Intent i = new Intent(newTurnierActivity.this, TurnierActivity.class);
                    i.putExtra("position", Storage.turniere.size()-1);
                    startActivity(i);
                    finish();
                }else{
                    SweetAlertDialog dialog = new SweetAlertDialog(newTurnierActivity.this, SweetAlertDialog.ERROR_TYPE);
                    dialog.setTitleText("Name fehlerhaft")
                            .setContentText("Der Turnier-Name muss mindestens 3 Zeichen lang sein.")
                            .setConfirmText("Ok")
                            .show();
                }
            }
        });

    }


    /**
     * For the Spinner selection
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (position){
            case 0:
                turniertyp = Turniertyp.GRUPPE_KO_SYSTEM;
                break;
            case 1:
                turniertyp = Turniertyp.GRUPPEN;
                break;
            case 2:
                turniertyp = Turniertyp.LIGA;
                break;
            case 3:
                turniertyp = Turniertyp.KO_SYSTEM;
                break;
            default:
                turniertyp = Turniertyp.KO_SYSTEM;
        }
    }

    /**
     * If nothing select on the spinner
     * @param parent
     */
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.rbtn_hin:
                if (checked){
                    turnierHinRueck = TurnierHinRueck.HIN;
                }
                break;
            case R.id.rbtn_hinrueck:
                if (checked){
                    turnierHinRueck = TurnierHinRueck.HIN_RUECK;
                }
                break;
        }
    }

}
