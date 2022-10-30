package com.lyf.qqclient.service;

import java.util.HashMap;

public class ManageClientConnectServerThread{
    private static HashMap<String, ClientConnectServerThread> hm = new HashMap<>();

    public static void addClientConnectServerThread(String userId, ClientConnectServerThread c){
        hm.put(userId, c);
    }

    public static ClientConnectServerThread getClientConnectServerThread(String userId){
        return hm.get(userId);
    }
}
