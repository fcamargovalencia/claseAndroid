package imc.cursoandroid.gdgcali.com.imccalculator.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;

import imc.cursoandroid.gdgcali.com.imccalculator.R;

public class SplashActivity extends Activity {

    boolean spActive;
    boolean spPaused;
    long spTime = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        spPaused = false;
        spActive = true;

        Thread splashTimer = new Thread() {
            public void run() {
                try {
                    long ms = 0;
                    while (spActive && ms < spTime) {
                        sleep(100);
                        if (!spPaused) ms += 100;
                    }
                    startActivity(new Intent(SplashActivity.this, LoginFbActivity.class));
                    finish();
                } catch (Exception e) {
                    System.out.println(e.toString());
                }
            }
        };
        splashTimer.start();
        setContentView(R.layout.activity_splash);
        return;
    }

    protected void onStop() {
        super.onStop();
    }

    protected void onPause(){
        super.onPause();
        spPaused = true;
    }

    protected void onResume(){
        super.onResume();
        spPaused = false;
    }

    protected void onDestroy(){
        super.onDestroy();
    }

    public boolean onKeyDown(int keyCode, KeyEvent event){
        super.onKeyDown(keyCode,event);
        spActive = false;
        return true;
    }

}
