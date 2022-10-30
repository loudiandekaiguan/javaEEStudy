package com.lyf.qqclient.service;

import com.lyf.qqcommon.Message;
import com.lyf.qqcommon.MessageType;
import com.lyf.qqcommon.User;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class UserClientService {
    boolean b = false;
    private User u = new User();
    Socket socket;


    public boolean checkUser(String userId, String pwd){
        u.setUserId(userId);
        u.setPasswd(pwd);

        try {
            socket = new Socket(InetAddress.getByName("127.0.0.1"), 9999);
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(u);

            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            Message ms = (Message)ois.readObject();

            if(ms.getMesType().equals(MessageType.MESSAGE_LOGIN_SUCCEED)){
                ClientConnectServerThread clientConnectServerThread = new ClientConnectServerThread(socket);
                clientConnectServerThread.start();
                ManageClientConnectServerThread.addClientConnectServerThread(userId, clientConnectServerThread);
                b= true;
            }else{

            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return b;
    }

    public void onlineFriendList(){
        Message message = new Message();
        message.setMesType(MessageType.MESSAGE_GET_ONLINE_FRIEND);
        try {
            ObjectOutputStream oos = new ObjectOutputStream
                    (ManageClientConnectServerThread.getClientConnectServerThread(u.getUserId()).getSocket().getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
