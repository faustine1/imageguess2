package com.ImageGuess;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * ImageGuess Game
 * Welcome interface
 * Created by Stanislas, Lisette, Faustine on 2017/12 in SJTU.
 */

public class Welcome extends Activity {
    private Button createRoom;
    private Button joinRoom;
    private TextView welcome;
    private MyApp myApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myApp = (MyApp) getApplication();
        setContentView(R.layout.after_log_in);
        createRoom =(Button)findViewById(R.id.createRoom);
        joinRoom =(Button)findViewById(R.id.joinRoom);
        welcome=(TextView)findViewById(R.id.currentUserName);
        String title = "欢迎 "+myApp.getUserName();
        welcome.setText(title);

        createRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Welcome.this, RoomCreate.class);
                startActivity(intent);
            }
        });

        joinRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Welcome.this, RoomJoin.class);
                startActivity(intent);
            }
        });
    }
}
