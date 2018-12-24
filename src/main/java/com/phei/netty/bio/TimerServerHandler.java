package com.phei.netty.bio;

import java.net.Socket;

/**
 * <p>标题: </p>
 * <p>功能描述: </p>
 *
 * <p>创建时间: 2018/12/24 19:39</p>
 * <p>作者：yaoq</p>
 * <p>修改历史记录：</p>
 * ====================================================================<br>
 */
public class TimerServerHandler implements Runnable {
    private Socket socket;
    public TimerServerHandler(Socket socket) {
        this.socket=socket;
    }

    public TimerServerHandler() {
    }

    public void run() {

    }
}
