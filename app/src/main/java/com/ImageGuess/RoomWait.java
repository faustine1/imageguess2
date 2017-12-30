package com.ImageGuess;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import static android.content.ContentValues.TAG;

/**
 * ImageGuess Game
 * Room wait interface
 * Created by Stanislas, Lisette, Faustine on 2017/12 in SJTU.
 */

public class RoomWait extends Activity{
    private Button startGame;
    private Button quitRoom;
    private TextView roomNumber;
    private String localIP;
    private int localPort ;
    private static ClientSocket clientSocket;
    private MyApp myApp;
    private String gameData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wait_game_start);
        quitRoom = (Button) findViewById(R.id.quitRoom);
        roomNumber = (TextView) findViewById(R.id.currentRoomNumber);
        myApp = (MyApp) getApplication();
        roomNumber.setText(myApp.getRoomNumber());
        clientSocket = new ClientSocket(this, myApp.getServerIP(), myApp.getServerPort());
        localPort = myApp.getPortNumber();
        localIP = clientSocket.getIp(this);

        final Handler startHandler = new Handler(new Handler.Callback(){
            public boolean handleMessage(Message msg){
                if (msg.what == 1){
                    Intent intent = new Intent(RoomWait.this, Join.class);
                    startActivity(intent);
                }
                return false;
            }
        });

        clientSocket.InfoReceiver(localPort, new ClientSocket.DataListener() {
            @Override
            public void transData() {
                try {
                    gameData = clientSocket.getGameData();
                    JSONObject message = new JSONObject(gameData);
                    System.out.println(message.toString());
                    if (myApp.getRoomState().equals("host")) {
                        myApp.setRemoteIP(message.get("remoteIP").toString());
                        myApp.setRemotePort(Integer.parseInt(message.get("remotePort").toString()));
                    }
                    if(myApp.getRoomState().equals("player")){
                        if(message.get("startState").toString().equals("start")) {
                            clientSocket.shutDown();
                            Message msg = startHandler.obtainMessage();
                            msg.what = 1;
                            startHandler.sendMessage(msg);
                        }
                    }
                }catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        if(myApp.getRoomState().equals("player")) {
            try {
                JSONObject playerInfo = new JSONObject();
                playerInfo.put("remoteIP", localIP);
                playerInfo.put("remotePort",myApp.getPortNumber());
                clientSocket.InfoSender(myApp.getRemotePort(),myApp.getRemoteIP(),playerInfo.toString());
            }catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if(myApp.getRoomState().equals("host")) {
            startGame = (Button) findViewById(R.id.startGame);
            startGame.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        JSONObject playerInfo = new JSONObject();
                        playerInfo.put("startState", "start");
                        clientSocket.InfoSender(myApp.getRemotePort(), myApp.getRemoteIP(), playerInfo.toString());
                        /*
                        System.out.println("send start");
                        System.out.println(playerInfo.toString());
                        System.out.println(Integer.parseInt(myApp.getRemotePort()));
                        System.out.println(myApp.getRemoteIP());
                        */
                        Intent intent = new Intent(RoomWait.this, Play.class);
                        startActivity(intent);
                    }catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }
}
