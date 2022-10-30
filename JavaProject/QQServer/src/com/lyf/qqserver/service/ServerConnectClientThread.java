package com.lyf.qqserver.service;

import com.lyf.qqcommon.Message;
import com.lyf.qqcommon.MessageType;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ServerConnectClientThread extends Thread{
    private Socket socket;
    private String userId;

    @Override
    public void run() {
        while(true){
            try {
                System.out.println("服务端和客户端保持通讯，读取数据...." + userId);
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                Message message = (Message) ois.readObject();
                if(message.getMesType().equals(MessageType.MESSAGE_GET_ONLINE_FRIEND)){
                    System.out.println(message.getSender() + "要在线用户列表");
                    String onlineUser = ManageClientThreads.getOnlineUser();
                    Message message2 = new Message();
                    message2.setMesType(MessageType.MESSAGE_RET_ONLINE_FRIEND);
                    message2.setContent(onlineUser);
                    message2.setGetter(message.getSender());
                    ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                    oos.writeObject(message2);
                }else{
                    System.out.println("其他Message");
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public ServerConnectClientThread(Socket socket, String userId) {
        this.socket = socket;
        this.userId = userId;
    }
}
