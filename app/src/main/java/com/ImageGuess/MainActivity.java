package com.ImageGuess;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;

/**
 * ImageGuess Game
 * Starting page
 * Created by Stanislas, Lisette, Faustine on 2017/12 in SJTU.
 */

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        timer.start();
    }

    private CountDownTimer timer = new CountDownTimer(2000,1000) {
        @Override
        public void onTick(long millisUntilFinished) {
        }

        @Override
        public void onFinish() {
            Intent intent=new Intent(MainActivity.this,LogIn.class);
            startActivity(intent);
        }
    };
}
