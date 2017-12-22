package com.a091517.ldr.nihuawocai;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

/**
 * Created by ldr on 2017/12/21.
 */

public class MainActivity extends Activity {
 //   ImageView pic=new ImageView(,R.drawable.icon_menu);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        timer.start();
    }

  /*      button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                Intent intent=new Intent(MainActivity.this,LogIn.class);
                startActivity(intent);
            }
        }); */

    private CountDownTimer timer=new CountDownTimer(2000,1000) {
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
