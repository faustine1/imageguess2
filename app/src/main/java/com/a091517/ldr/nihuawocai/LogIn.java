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

import java.io.BufferedReader;
import java.io.IOException;

/**
 * Created by ldr on 2017/12/21.
 */

public class LogIn extends Activity {
    private static final String CURRENT_USER="com.a091517.ldr.nihuawocai.currentUser";
    private static final String PORT_NUMBER="com.a091517.ldr.nihuawocai.portNumber";
    Button confirmButton;
    EditText userName;
    EditText password;
    JSONObject loginJSON=new JSONObject();
    private ClientSocket clientSocket;
    private String createUser;
    private String portNumber;
    private MyApp myApp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        clientSocket = new ClientSocket(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_in);
        confirmButton=(Button)findViewById(R.id.confirmLogIn);
        userName=(EditText) findViewById(R.id.userName);
        password=(EditText)findViewById(R.id.password);
        myApp=(MyApp)getApplication();

        confirmButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent=new Intent(LogIn.this,Welcome.class);
                try{
                    loginJSON.put("passWord",password.getText());
                    loginJSON.put("userName",userName.getText());
                    loginJSON.put("infoState",1);
                    myApp.setUserName(userName.getText().toString());
                    clientSocket.InfoToServer(loginJSON.toString(),new ClientSocket.DataListener(){
                        @Override
                        public void transData() {
                            try {
                                createUser = clientSocket.getServermessage();
                                JSONObject message = new JSONObject(createUser);
                                System.out.println(message.get("serverInfo").toString());
                                portNumber=message.get("portNumber").toString();
                                myApp.setPortNumber(portNumber);
                            }
                            catch (JSONException e){
                                e.printStackTrace();
                            }
                        }
                    });
                    intent.putExtra(CURRENT_USER,loginJSON.get("userName").toString());
                }
                catch(JSONException e) {
                    e.printStackTrace();
                }
                startActivity(intent);
            }
        });
    }
}
