package com.a091517.ldr.nihuawocai;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by ldr on 2017/12/19.
 */

public class gameRoom extends Activity{
    Button startGame;
    Button quitRoom;
    TextView roomNumber;
    private static final String CREATE_ROOM="com.a091517.ldr.nihuawocai.create_room";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_room);
        startGame = (Button) findViewById(R.id.startGame);
        quitRoom = (Button) findViewById(R.id.quitRoom);
        roomNumber=(TextView)findViewById(R.id.currentRoomNumber);
        roomNumber.setText(this.getIntent().getStringExtra(CREATE_ROOM));

        startGame.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                Intent intent=new Intent(gameRoom.this,drawingBoard.class);
                intent.putExtra(CREATE_ROOM,roomNumber.getText());
                startActivity(intent);
            }
        });
    }
}
