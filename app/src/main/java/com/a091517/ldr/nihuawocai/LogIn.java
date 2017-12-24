package com.a091517.ldr.nihuawocai;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ldr on 2017/12/21.
 */

public class LogIn extends Activity {
    private static final String CURRENT_USER="com.a091517.ldr.nihuawocai.currentUser";
    Button confirmButton;
    EditText userName;
    EditText password;
    JSONObject jsonObject=new JSONObject();
    private ClientSocket clientSocket = new ClientSocket(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_in);
        confirmButton=(Button)findViewById(R.id.confirmLogIn);
        userName=(EditText) findViewById(R.id.userName);
        password=(EditText)findViewById(R.id.password);


        confirmButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                clientSocket.InfoToServer(jsonObject.toString());
                Intent intent=new Intent(LogIn.this,Welcome.class);
                try{
                    jsonObject.put("password",password.getText());
                    jsonObject.put("username",userName.getText());
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
