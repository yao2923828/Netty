package com.phei.netty.bio;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * <p>标题: </p>
 * <p>功能描述: </p>
 *
 * <p>创建时间: 2018/12/24 19:30</p>
 * <p>作者：yaoq</p>
 * <p>修改历史记录：</p>
 * ====================================================================<br>
 */
public class TimeServer {
    public static void main(String[] args) throws IOException {
        int port = 8080;
        if (args != null && args.length > 0) {
            try {
                port = Integer.valueOf(args[0]);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        ServerSocket server=null;
        try {
            server=new ServerSocket(port);
            System.out.println("The time server is start in port : "+port);
            Socket socket=null;
            while (true){
                socket=server.accept();
                new Thread(new TimerServerHandler(socket)).start();
            }
        } finally {
            if(server !=null){
                System.out.println("The time server close");
                server.close();
                server=null;
            }
        }
    }
}
