package com.a091517.ldr.nihuawocai;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ldr on 2017/12/17.
 */

public class RoomJoin extends Activity {
    Button confirmButton;
    EditText roomNumber;
    JSONObject joinroomJSON=new JSONObject();
    private static final String JOIN_ROOM="com.a091517.ldr.nihuawocai.join_room";
    private ClientSocket clientSocket;
    private String joinSuccess;
    private MyApp myApp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.room_join);
        clientSocket = new ClientSocket(this);
        confirmButton = (Button) findViewById(R.id.confirmJoinRoom);
        roomNumber = (EditText) findViewById(R.id.roomNumber);
        myApp=(MyApp)getApplication();
        roomNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    joinroomJSON.put("roomNumber", s);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }


            @Override
            public void afterTextChanged(Editable s) {
                //   Toast.makeText(MainActivity.this,s,Toast.LENGTH_SHORT).show();
            }
        });

        confirmButton.setOnClickListener(new View.OnClickListener() {
            String prompt;
            String roomJoinState = null;
            @Override
            public void onClick(View v) {
                try {
                    joinroomJSON.put("userName",myApp.getUserName());
                    joinroomJSON.put("infoState",3);
                    clientSocket.InfoToServer(joinroomJSON.toString(), new ClientSocket.DataListener() {
                        @Override
                        public void transData() {
                            try {
                                joinSuccess = clientSocket.getServermessage();
                                JSONObject message = new JSONObject(joinSuccess);
                                System.out.println(message.toString());
                                roomJoinState = message.get("roomJoinState").toString();
                                myApp.setRemoteIp(message.get("remoteIP").toString());
                                myApp.setRemotePort(message.get("remotePort").toString());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    while(roomJoinState==null){}
                    switch (roomJoinState) {
                        case "102":
                            myApp.setRoomState("player");
                            prompt = "成功加入房间" + joinroomJSON.get("roomNumber").toString();
                            Toast.makeText(RoomJoin.this, prompt, Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(RoomJoin.this, RoomWait.class);
                            try {
                                intent.putExtra(JOIN_ROOM, joinroomJSON.get("roomNumber").toString());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            startActivity(intent);
                            break;
                        case "103":
                            prompt = "房间号不存在，请重新输入";
                            Toast.makeText(RoomJoin.this, prompt, Toast.LENGTH_SHORT).show();
                            break;
                        case "104":
                            prompt = "房间人数已满，请重新输入";
                            Toast.makeText(RoomJoin.this, prompt, Toast.LENGTH_SHORT).show();
                            break;
                        default:
                            break;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
