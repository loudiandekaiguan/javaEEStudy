package com.lyf.qqserver.service;

import com.lyf.qqcommon.Message;
import com.lyf.qqcommon.MessageType;
import com.lyf.qqcommon.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class QQServer {
    private ServerSocket ss = null;
    private static HashMap<String, User> vaildUsers = new HashMap<>();
    static{
        vaildUsers.put("100", new User("100", "123456"));
        vaildUsers.put("200", new User("200", "123456"));
        vaildUsers.put("300", new User("300", "123456"));
        vaildUsers.put("至尊宝", new User("至尊宝", "123456"));
        vaildUsers.put("紫霞仙子", new User("紫霞仙子", "123456"));
        vaildUsers.put("菩提老祖", new User("菩提老子", "123456"));

    }

    private boolean checkUser(String userId, String passwd){
        User user = vaildUsers.get(userId);
        if(user == null){
            return false;
        }
        if(!user.getPasswd().equals(passwd)){
            return false;
        }
        return true;
    }
    public QQServer(){
        try {
            System.out.println("服务端在端口9999监听....");
            ss = new ServerSocket(9999);
            while(true){
                Socket socket = ss.accept();
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());

                User u = (User) ois.readObject();

                Message message = new Message();
                if(checkUser(u.getUserId(), u.getPasswd())){
                    message.setMesType(MessageType.MESSAGE_LOGIN_SUCCEED);
                    oos.writeObject(message);

                    ServerConnectClientThread serverConnectClientThread = new ServerConnectClientThread(socket, u.getUserId());
                    serverConnectClientThread.start();
                    ManageClientThreads.addClientThread(u.getUserId(), serverConnectClientThread);
                }else{
                    message.setMesType(MessageType.MESSAGE_LOGIN_FAIT);
                    oos.writeObject(message);
                    socket.close();
                }
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }finally {
            try {
                ss.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
