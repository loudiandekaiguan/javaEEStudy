package com.lyf.qqclient.view;

import com.lyf.qqclient.service.UserClientService;
import com.lyf.qqclient.utils.Utility;
import com.lyf.qqcommon.User;

public class QQView {
    private boolean loop = true;
    private String Key = "";
    private UserClientService userClientService = new UserClientService();

    public static void main(String[] args) {
        new QQView().mainMenu();
    }

    private void mainMenu(){
        while(loop){
            System.out.println("=============欢迎登录网络通信系统=============");
            System.out.println("\t\t\t  1  登录系统");
            System.out.println("\t\t\t  9  退出系统");
            System.out.print("请输入你的选择：");
            Key = Utility.readString(1);

            switch(Key){
                case "1":
                    System.out.print("请输入用户号：");
                    String userId = Utility.readString(50);
                    System.out.print("请输入密 码");
                    String pwd = Utility.readString(50);
                    if(userClientService.checkUser(userId, pwd)){
                        System.out.println("=============欢迎（用户 " + userId +  "）=============");
                        while(loop){
                            System.out.println("\n============网络通信系统二级菜单（用户" + userId + "）============");
                            System.out.println("\t\t\t  1  显示在线用户列表");
                            System.out.println("\t\t\t  2  群发消息");
                            System.out.println("\t\t\t  3  私聊消息");
                            System.out.println("\t\t\t  4  发送文件");
                            System.out.println("\t\t\t  9  退出系统");
                            System.out.print("请输入你的选择：");
                            Key = Utility.readString(1);
                            switch (Key){
                                case "1":
                                    userClientService.onlineFriendList();
                                    break;
                                case "2":
                                    System.out.println("群发消息");
                                    break;
                                case "3":
                                    System.out.println("私聊消息");
                                    break;
                                case "4":
                                    System.out.println("发送文件");
                                    break;
                                case "9":
                                    loop = false;
                                    break;
                            }
                        }
                    }else{
                        System.out.println("登录失败");
                    }
                    break;
                case "9":
                    loop = false;
                    break;
            }
        }
    }
}
