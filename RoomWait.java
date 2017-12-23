package com.a091517.ldr.nihuawocai;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import static android.content.ContentValues.TAG;

import java.util.ArrayList;

/**
 * Created by ldr on 2017/12/22.
 */

public class RoomWait extends Activity{
    private Button startGame;
    private Button quitRoom;
    private TextView roomNumber;
    private ArrayList<Integer> initScoreList;  // 该房间的分数表
    private ArrayList<String> wordAlreadyUsed;
    private int firstDrawer; // 游戏第一轮画图的人
    private static final String CREATE_ROOM="create_room";
    private static final String CURRENT_DRAWER="current_drawer";
    private static final String SCORE_LIST="score_list";
    private static final String WORDS_USED="used_words";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wait_game_start);
        startGame = (Button) findViewById(R.id.startGame);
        quitRoom = (Button) findViewById(R.id.quitRoom);
        roomNumber=(TextView)findViewById(R.id.currentRoomNumber);
        roomNumber.setText(this.getIntent().getStringExtra(CREATE_ROOM));
        firstDrawer=1;  //假设从玩家1开始轮流
        initScoreList=new ArrayList<Integer>();
        for(int i=0;i<6;++i)
            initScoreList.add(0); // 一开始所有人都没有分数,下标0对应玩家1 etc...
        wordAlreadyUsed=new ArrayList<String>();

        startGame.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                Log.i(TAG,"startGame");
                Intent intent=new Intent(RoomWait.this,Play.class);
                intent.putExtra(CREATE_ROOM,roomNumber.getText());
                intent.putExtra(CURRENT_DRAWER,firstDrawer);
                intent.putExtra(SCORE_LIST,initScoreList);
                intent.putExtra(WORDS_USED,wordAlreadyUsed);
                startActivity(intent);
            }
        });
    }
}
