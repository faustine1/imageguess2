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

public class RoomCreate extends Activity {
    Button confirmButton;
    EditText roomNumber;
    JSONObject creatroomJSON=new JSONObject();
    private static final String CREATE_ROOM="com.a091517.ldr.nihuawocai.create_room";
    private ClientSocket clientSocket;
    private String createSuccess;
    private MyApp myApp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.room_create);
        clientSocket = new ClientSocket(this);
        confirmButton = (Button) findViewById(R.id.confirmCreateRoom);
        roomNumber = (EditText) findViewById(R.id.roomNumber);
        myApp=(MyApp)getApplication();
        roomNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    creatroomJSON.put("roomNumber", s);

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
            String roomCreateState = null;
            @Override
            public void onClick(View v) {
                try {
                    creatroomJSON.put("userName",myApp.getUserName());
                    creatroomJSON.put("infoState",2);
                    clientSocket.InfoToServer(creatroomJSON.toString(), new ClientSocket.DataListener() {
                        @Override
                        public void transData() {
                            try {
                                createSuccess = clientSocket.getServermessage();
                                JSONObject message = new JSONObject(createSuccess);
                                System.out.println(message.toString());
                                System.out.println(message.get("serverInfo").toString());
                                roomCreateState = message.get("roomCreateState").toString();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    while(roomCreateState==null){}
                    switch (roomCreateState) {
                        case "100":
                            myApp.setRoomState("host");
                            prompt = "成功创建房间" + creatroomJSON.get("roomNumber").toString() + "快告诉你的朋友吧";
                            Toast.makeText(RoomCreate.this, prompt, Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(RoomCreate.this, RoomWait.class);
                            try {
                                intent.putExtra(CREATE_ROOM, creatroomJSON.get("roomNumber").toString());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            startActivity(intent);
                            break;
                        case "101":
                            prompt = "房间创建失败，请重新输入";
                            Toast.makeText(RoomCreate.this, prompt, Toast.LENGTH_SHORT).show();
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
