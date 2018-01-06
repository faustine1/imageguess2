package com.ImageGuess;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import org.json.JSONException;
import org.json.JSONObject;

import static android.content.ContentValues.TAG;

/**
 * ImageGuess Game
 * Room create interface
 * Created by Stanislas, Lisette, Faustine on 2017/12 in SJTU.
 */

public class RoomCreate extends Activity {
    private ImageButton confirmButton;
    private EditText roomNumber;
    private JSONObject createRoomJSON =new JSONObject();
    private ClientSocket clientSocket;
    private String createSuccess;
    private MyApp myApp;
    private String prompt;
    private String roomCreateState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.room_create);

        confirmButton = (ImageButton) findViewById(R.id.confirmCreateRoom);
        confirmButton.setBackgroundResource(R.drawable.button2);
        roomNumber = (EditText) findViewById(R.id.roomNumber);
        myApp=(MyApp) getApplication();
        clientSocket = new ClientSocket(this, myApp.getServerIP(), myApp.getServerPort());

        final Handler createHandler = new Handler(new Handler.Callback(){
            public boolean handleMessage(Message msg){
                if (msg.what == 1){
                    switch (roomCreateState) {
                        case "100":
                            myApp.setRoomState("host");
                            try{
                                prompt = "成功创建房间" + createRoomJSON.get("roomNumber").toString() + "快告诉你的朋友吧";
                                myApp.setRoomNumber(createRoomJSON.get("roomNumber").toString());
                            } catch (JSONException e){
                                e.printStackTrace();
                            }
                            Toast.makeText(RoomCreate.this, prompt, Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(RoomCreate.this, RoomWait.class);
                            startActivity(intent);
                            break;
                        case "101":
                            prompt = "房间创建失败，请重新输入";
                            Toast.makeText(RoomCreate.this, prompt, Toast.LENGTH_SHORT).show();
                            break;
                        default:
                            break;
                    }
                }
                return false;
            }
        });

        roomNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    createRoomJSON.put("roomNumber", s);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }


            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        confirmButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                try {
                    confirmButton.setBackgroundResource(R.drawable.button1);
                    createRoomJSON.put("userName",myApp.getUserName());
                    createRoomJSON.put("infoState",2);  //infoState 2 represents a room create behaviour.
                    clientSocket.InfoToServer(createRoomJSON.toString(), new ClientSocket.DataListener() {

                        @Override
                        public void transData() {
                            try {
                                createSuccess = clientSocket.getServerMessage();
                                JSONObject message = new JSONObject(createSuccess);
                                System.out.println(message.toString());
                                System.out.println(message.get("serverInfo").toString());
                                roomCreateState = message.get("roomCreateState").toString();
                                //clientSocket.shutDown();
                                Message msg = createHandler.obtainMessage();
                                msg.what = 1;
                                createHandler.sendMessage(msg);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                confirmButton.setBackgroundResource(R.drawable.button1);
            }
        });
    }
}
