package com.a091517.ldr.nihuawocai;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;

/**
 * Created by ldr on 2017/12/17.
 */

public class Welcome extends Activity {
    Button createroom;
    Button joinroom;
    Button settings;
    Button aboutUs;
    TextView welcome;
    private static final String CURRENT_USER="com.a091517.ldr.nihuawocai.currentUser";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.after_log_in);
        createroom=(Button)findViewById(R.id.createRoom);
        joinroom=(Button)findViewById(R.id.joinRoom);
        settings=(Button)findViewById(R.id.settings);
        aboutUs=(Button)findViewById(R.id.about);
        welcome=(TextView)findViewById(R.id.currentUserName);
        welcome.setText("欢迎"+this.getIntent().getStringExtra(CURRENT_USER));

        createroom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Welcome.this, RoomCreate.class);
                startActivity(intent);
            }
        });

        joinroom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Welcome.this, RoomJoin.class);
                startActivity(intent);
            }
        });

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Welcome.this, Settings.class);
                startActivity(intent);
            }
        });

        aboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Welcome.this, About.class);
                startActivity(intent);
            }
        });
    }
}
