package com.ImageGuess;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * ImageGuess Game
 * Login interface
 * Created by Stanislas, Lisette, Faustine on 2017/12 in SJTU.
 */

public class LogIn extends Activity {
    private ImageButton loginButton;
    private ImageButton registerButton;
    private EditText userName;
    private EditText passWord;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private CheckBox rememberPass;
    private JSONObject loginJSON=new JSONObject();
    private ClientSocket clientSocket;
    private String createUser;
    private int portNumber;
    private MyApp myApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_in);

        loginButton = (ImageButton)findViewById(R.id.confirmLogIn);
        loginButton.setBackgroundResource(R.drawable.button2);
        registerButton = (ImageButton)findViewById(R.id.register);
        registerButton.setBackgroundResource(R.drawable.button2);
        userName  = (EditText) findViewById(R.id.userName);
        passWord = (EditText)findViewById(R.id.passWord);
        pref= PreferenceManager.getDefaultSharedPreferences(this);
        rememberPass=(CheckBox)findViewById(R.id.rememberPassword);
        boolean isRemember = pref.getBoolean("remember_password",false);
        if(isRemember){
            String account=pref.getString("account","");
            String password=pref.getString("password","");
            userName.setText(account);
            passWord.setText(password);
            rememberPass.setChecked(true);
        }
        myApp = (MyApp)getApplication();
        clientSocket = new ClientSocket(this, myApp.getServerIP(), myApp.getServerPort());

        loginButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(LogIn.this,Welcome.class);
                loginButton.setBackgroundResource(R.drawable.button1);
                try{
                    loginJSON.put("passWord",passWord.getText());
                    loginJSON.put("userName",userName.getText());
                    loginJSON.put("infoState",1);  //infoState 1 represents a login behaviour.
                    myApp.setUserName(userName.getText().toString());
                    clientSocket.InfoToServer(loginJSON.toString(), new ClientSocket.DataListener(){
                        @Override
                        public void transData() {
                            try {
                                createUser = clientSocket.getServerMessage();
                                JSONObject message = new JSONObject(createUser);
                                System.out.println(message.get("serverInfo").toString());  //Print server's return information.
                                portNumber = Integer.parseInt(message.get("portNumber").toString());  //Get port number for UDP receiver.
                                myApp.setPortNumber(portNumber);
                            }
                            catch (JSONException e){
                                e.printStackTrace();
                            }
                        }
                    });
                }
                catch(JSONException e) {
                    e.printStackTrace();
                }
                editor=pref.edit();
                if(rememberPass.isChecked()){
                    editor.putBoolean("remember_password",true);
                    editor.putString("account",userName.getText().toString());
                    editor.putString("password",passWord.getText().toString());
                }else{
                    editor.clear();
                }
                editor.apply();
                startActivity(intent);
                loginButton.setBackgroundResource(R.drawable.button2);
            }
        });
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LogIn.this, Register.class);
                registerButton.setBackgroundResource(R.drawable.button1);
                startActivity(intent);
                registerButton.setBackgroundResource(R.drawable.button2);
            }
        });
    }
}
