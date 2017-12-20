package com.a091517.ldr.nihuawocai;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * Created by Administrator on 2017/12/20.
 */

public class ClientSocket {
    //private static Socket TCPSocket;
    //private static DatagramSocket UDPSocket;
    private static final String serverIP = "127.0.0.1";
    private static final int serverPort = 8000;
    ClientSocket(){

    }
    public void InfoToServer(String sendMessage){
        TCPSocket tcpSocket = new TCPSocket(serverIP, serverPort, sendMessage);
        new Thread(tcpSocket).start();
    }

    public class TCPSocket implements Runnable {

        private int serverPort;
        private String serverIP;
        private String sendMessage;

        public TCPSocket(String serverIP, int serverPort, String message){
            this.serverIP = serverIP;
            this.serverPort = serverPort;
            this.sendMessage = message;
        }

        @Override
        public void run() {
            try{
                //serverIP = InetAddress.getLocalHost().getHostAddress();
                Socket socket = new Socket(serverIP, serverPort);
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                //BufferedReader line = new BufferedReader(new InputStreamReader(System.in));
                //out.println(line.readLine());
                //line.close();
                out.println(sendMessage);
                out.close();
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                socket.close();
            } catch(IOException e){
                e.printStackTrace();
            }
        }
    }

    public class UDPSender implements Runnable {

        private int PortNumber;
        private byte[] RawData;
        private String IPaddress;
        private String GameData;

        public UDPSender(int portnumber, String ipaddress, String gamedata) {
            this.PortNumber = portnumber;
            this.IPaddress = ipaddress;
            this.GameData = gamedata;
        }

        @Override
        public void run() {
            try{
                RawData = new byte[1024];
                DatagramSocket clientSocket = new DatagramSocket();
                InetAddress IP = InetAddress.getByName(IPaddress);
                try{
                    //System.out.println(GameData);
                    RawData = GameData.getBytes("ASCII");
                    DatagramPacket sendPacket = new DatagramPacket(RawData, RawData.length, IP, PortNumber);
                    clientSocket.send(sendPacket);
                    clientSocket.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }catch (SocketException | UnknownHostException s){
                s.printStackTrace();
            }
        }
    }

    public class UDPReceiver implements Runnable {

        private int PortNumber;
        private byte[] RawData;
        private String GameData;


        public UDPReceiver(int portnumber) {
            this.PortNumber = portnumber;
        }

        @Override
        public void run() {
            try {
                DatagramSocket serverSocket = new DatagramSocket(PortNumber);
                while (true) {
                    try {
                        //String message = "111";
                        //RawData = message.getBytes("ASCII");
                        RawData = new byte[1024];
                        DatagramPacket receivePacket = new DatagramPacket(RawData, RawData.length);
                        //System.out.println("Receiving...");
                        serverSocket.receive(receivePacket);
                        GameData = new String(receivePacket.getData(), 0, receivePacket.getLength(), "ASCII");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } catch (SocketException s) {
                s.printStackTrace();
            }
        }
    }
}
