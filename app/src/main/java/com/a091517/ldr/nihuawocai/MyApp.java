package com.a091517.ldr.nihuawocai;

import android.app.Application;

/**
 * Created by apple on 2017/12/24.
 */

public class MyApp extends Application {
    private String user_name;
    private String port_number;
    private String player_state;
    private String player_number;
    private String remote_ip;
    private String remote_port;
    private String room_state;
    public void setUserName(String userName){
        this.user_name=userName;
    }
    public void setPortNumber(String portNumber){
        this.port_number=portNumber;
    }
    public void setPlayerState(String playerState){
        this.player_state=playerState;
    }
    public void setPlayerNumber(String playerNumber){
        this.player_number=playerNumber;
    }
    public void setRemoteIp(String remoteIp){
        this.remote_ip=remoteIp;
    }
    public void setRemotePort(String remotePort){
        this.remote_port=remotePort;
    }
    public void setRoomState(String roomState){
        this.room_state=roomState;
    }
    public String getUserName(){
        return user_name;
    }
    public String getPortNumber(){
        return port_number;
    }
    public String getPlayerState(){
        return player_state;
    }
    public String getPlayerNumber(){
        return player_number;
    }
    public String getRemoteIp(){
        return remote_ip;
    }
    public String getRemotePort(){
        return remote_port;
    }
    public String getRoomState(){
        return room_state;
    }
    @Override
    public void onCreate(){
        super.onCreate();
        user_name=new String();
        port_number=new String();
        player_state=new String();
        player_number=new String();
        remote_port=new String();
        remote_ip=new String();
        room_state=new String();
    }
}
