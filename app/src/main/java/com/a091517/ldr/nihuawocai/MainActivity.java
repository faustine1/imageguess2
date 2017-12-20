package com.a091517.ldr.nihuawocai;

import android.content.Context;
import android.os.Bundle;
import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.content.Intent;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends Activity {
    private static final String CURRENT_USER="com.a091517.ldr.nihuawocai.currentUser";
    Button confirmButton;
    EditText userName;
    EditText password;
    JSONObject jsonObject=new JSONObject();
    private static ClientSocket clientSocket = new ClientSocket();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        confirmButton=(Button)findViewById(R.id.confirmLogIn);
        userName=(EditText) findViewById(R.id.userName);
        password=(EditText)findViewById(R.id.password);

        userName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    jsonObject.put("username", s);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }


            @Override
            public void afterTextChanged(Editable s){
             //   Toast.makeText(MainActivity.this,s,Toast.LENGTH_SHORT).show();
            }
        });


        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    jsonObject.put("password", s);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
             //   Toast.makeText(MainActivity.this,s,Toast.LENGTH_SHORT).show();
            }
        });

        confirmButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                clientSocket.InfoToServer(jsonObject.toString());
                Intent intent=new Intent(MainActivity.this,Welcome.class);
                try{
                    intent.putExtra(CURRENT_USER,jsonObject.get("username").toString());
                }
                catch(JSONException e) {
                    e.printStackTrace();
                }
                startActivity(intent);
            }
        });
    }
}
