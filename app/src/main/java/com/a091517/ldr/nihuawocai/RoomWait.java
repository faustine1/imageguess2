package com.a091517.ldr.nihuawocai;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ldr on 2017/12/22.
 */

public class RoomWait extends Activity{
    Button startGame;
    Button quitRoom;
    TextView roomNumber;
    private String localIP;
    private String remoteIP;
    private int localPort ;
    private static ClientSocket clientSocket;
    private MyApp myApp;
    private String gameData;
    private int startState=0;
    private static final String CREATE_ROOM="com.a091517.ldr.nihuawocai.create_room";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wait_game_start);
        clientSocket = new ClientSocket(this);
        quitRoom = (Button) findViewById(R.id.quitRoom);
        roomNumber=(TextView)findViewById(R.id.currentRoomNumber);
        roomNumber.setText(this.getIntent().getStringExtra(CREATE_ROOM));
        myApp=(MyApp)getApplication();
        localPort=Integer.parseInt(myApp.getPortNumber());
        localIP = clientSocket.getIp(this);
        clientSocket.InfoReceiver(localPort, new ClientSocket.DataListener() {
            @Override
            public void transData() {
                try {
                    gameData = clientSocket.getGameData();
                    JSONObject message = new JSONObject(gameData);
                    System.out.println(message.toString());
                    if (myApp.getRoomState() == "host") {
                        myApp.setRemoteIp(message.get("remoteIP").toString());
                        myApp.setRemotePort(message.get("remotePort").toString());
                    }
                    if(myApp.getRoomState()=="player"){
                        if(message.get("startstate").toString().equals("start")) {
                            synchronized (this){
                                startState = 1;
                            }
                        }
                    }
                }catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        if(myApp.getRoomState()=="player") {
            try {
                JSONObject playerinfo = new JSONObject();
                playerinfo.put("remoteIP", localIP);
                playerinfo.put("remotePort",myApp.getPortNumber());
                clientSocket.InfoSender(Integer.parseInt(myApp.getRemotePort()),myApp.getRemoteIp(),playerinfo.toString());
            }catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if(myApp.getRoomState()=="host") {
            startGame = (Button) findViewById(R.id.startGame);
            startGame.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        JSONObject playerinfo = new JSONObject();
                        playerinfo.put("startstate", "start");
                        clientSocket.InfoSender(Integer.parseInt(myApp.getRemotePort()), myApp.getRemoteIp(), playerinfo.toString());
                        System.out.println("send start");
                        System.out.println(playerinfo.toString());
                        System.out.println(Integer.parseInt(myApp.getRemotePort()));
                        System.out.println(myApp.getRemoteIp());
                        Intent intent = new Intent(RoomWait.this, Play.class);
                        intent.putExtra(CREATE_ROOM, roomNumber.getText());
                        startActivity(intent);
                    }catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        if(myApp.getRoomState()=="player"){
            /*while (startState==0){
                synchronized (this){
                    int i = startState;
                }
                try{
                    Thread.sleep(1000);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }
            }*/
            //if(startState==1){
                Intent intent = new Intent(RoomWait.this, Join.class);
                //intent.putExtra(CREATE_ROOM, roomNumber.getText());
                startActivity(intent);
            //}
        }
    }
}
