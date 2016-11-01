package tr.easolution.meinturnier;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import tr.easolution.meinturnier.lib.Storage;
import tr.easolution.meinturnier.lib.Turnier.Turnier;

import static android.Manifest.permission_group.STORAGE;

public class TurnierActivity extends AppCompatActivity {

    private int position = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_turnier);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle bundle = getIntent().getExtras();
        position = bundle.getInt("position");
        getSupportActionBar().setTitle(Storage.turniere.get(position).getText());

        (findViewById(R.id.toolbar)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder =
                        new AlertDialog.Builder(TurnierActivity.this);

                LayoutInflater inflater = getLayoutInflater();
                //this is what I did to added the layout to the alert dialog
                View layout = inflater.inflate(R.layout.dialog_turnier_namen_aendern,null);
                builder.setTitle("Turnier-Namen:");
                builder.setView(layout);

                final EditText edit = (EditText)layout.findViewById(R.id.edt_turniernamen);
                if (position != -1){
                    edit.setText(Storage.turniere.get(position).getTurnierName());
                }

                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (edit.getText().length() != 0 && position != -1){
                            Storage.turniere.get(position).setTurnierName(edit.getText().toString());
                            TurnierActivity.this.getSupportActionBar().setTitle(edit.getText().toString());
                            Storage.saveTurniere(getBaseContext());
                        }
                    }
                });
                builder.setNegativeButton("Abbrechen", null);
                builder.show();
            }
        });

    }
}

