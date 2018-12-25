package com.phei.netty.nio;

/**
 * <p>标题: </p>
 * <p>功能描述: </p>
 *
 * <p>创建时间: 2018/12/24 21:51</p>
 * <p>作者：yaoq</p>
 * <p>修改历史记录：</p>
 * ====================================================================<br>
 */
public class TimeClient {

    public static void main(String[] args) {
        int port = 8080;
        if (args != null && args.length > 0) {
            port = Integer.valueOf(port);
        }
        new Thread(new TimeClientHandle("127.0.0.1", port),"TimeClient-001").start();
    }
}
