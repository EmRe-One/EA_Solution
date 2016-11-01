package tr.easolution.meinturnier;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;


/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        final ProgressBar bar = (ProgressBar) findViewById(R.id.progressBar);

        Thread logoTimer = new Thread(){
            public void run(){
                try{
                    while(bar.getProgress() < 100){
                        sleep(300);
                        bar.setProgress(bar.getProgress()+((int)(Math.random()*30)));
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finally{
                    Intent menuIntent = new Intent(SplashScreenActivity.this, MainActivity.class);
                    startActivity(menuIntent);
                    finish();
                }
            }
        };
        logoTimer.start();
    }
}
