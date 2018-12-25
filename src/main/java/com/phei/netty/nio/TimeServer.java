package com.phei.netty.nio;

/**
 * <p>标题: </p>
 * <p>功能描述: </p>
 *
 * <p>创建时间: 2018/12/25 7:17</p>
 * <p>作者：yaoq</p>
 * <p>修改历史记录：</p>
 * ====================================================================<br>
 */
public class TimeServer {
    public static void main(String[] args) {
        int port =8080;
        if(args !=null && args.length >0){
            port=Integer.valueOf(args[0]);
        }
        MultiplexerTimeServer timeServer=new MultiplexerTimeServer(port);
        new Thread(timeServer,"NIO-MultiplexerTimeServer-001").start();
    }
}
