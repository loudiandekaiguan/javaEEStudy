package com.lyf.qqclient.service;

import com.lyf.qqcommon.Message;
import com.lyf.qqcommon.MessageType;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientConnectServerThread extends Thread{
    private Socket socket;
    public ClientConnectServerThread(Socket socket){
        this.socket = socket;
    }

    @Override
    public void run() {
        while(true){
            try {
                System.out.println("客户端线程等待读取从服务端发送的消息");
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                Message message = (Message)ois.readObject();
                if(message.getMesType().equals(MessageType.MESSAGE_RET_ONLINE_FRIEND)){
                    String[] onlineUsers = message.getContent().split(" ");
                    System.out.println("========在线用户列表========");
                    for(String str : onlineUsers){
                        System.out.println("用户:" + str);
                    }
                }else{
                    System.out.println("其他类型的Message");
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        }
    }

    public Socket getSocket() {
        return socket;
    }
}
