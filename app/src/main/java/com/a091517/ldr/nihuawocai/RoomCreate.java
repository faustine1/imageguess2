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
    JSONObject jsonObject=new JSONObject();
    private static final String CREATE_ROOM="com.a091517.ldr.nihuawocai.create_room";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.room_create);
        confirmButton = (Button) findViewById(R.id.confirmCreateRoom);
        roomNumber = (EditText) findViewById(R.id.roomNumber);

        roomNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    jsonObject.put("roomNumber", s);
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
            @Override
            public void onClick(View v) {

                try {
                    String prompt = "成功创建房间" + jsonObject.get("roomNumer").toString()+"快告诉你的朋友吧";
                    Toast.makeText(RoomCreate.this, prompt, Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {

                }

                Intent intent = new Intent(RoomCreate.this, RoomWait.class);
                try {
                    intent.putExtra(CREATE_ROOM, jsonObject.get("roomNumber").toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                startActivity(intent);
            }
        });
    }

}
