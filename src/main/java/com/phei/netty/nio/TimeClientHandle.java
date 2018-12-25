package com.phei.netty.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * <p>标题: </p>
 * <p>功能描述: </p>
 *
 * <p>版权: 税友软件集团股份有限公司</p> 
 * <p>创建时间: 2018/12/25 7:43</p> 
 * <p>作者：yaoq</p>
 * <p>修改历史记录：</p>
 * ====================================================================<br>
 */
public class TimeClientHandle implements Runnable{
    private String host;
    private int port;
    private Selector selector;
    private SocketChannel socketChannel;
    private volatile boolean stop;

    public TimeClientHandle(String host, int port) {
        this.host=host==null?"127.0.0.1":host;
        this.port=port;
        try {
            selector=Selector.open();
            socketChannel=SocketChannel.open();
            socketChannel.configureBlocking(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void run() {
        try {
            doConnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
        while(!stop){
            try {
                selector.select(1000);
                Set<SelectionKey> selectionKeys=selector.selectedKeys();
                Iterator<SelectionKey> it=selectionKeys.iterator();
                SelectionKey key=null;
                while (it.hasNext()){
                    key=it.next();
                    it.remove();
                    handleInput(key);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        if(selector!=null){
            try {
                selector.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void doConnect() throws IOException {
        if(socketChannel.connect(new InetSocketAddress(host,port))){
            socketChannel.register(selector, SelectionKey.OP_READ);
            doWrite(socketChannel);
        }
    }

    private void handleInput(SelectionKey key) throws IOException {
        if(key.isValid()){
            SocketChannel sc= (SocketChannel) key.channel();
            if(key.isConnectable()){
                if(sc.finishConnect()){
                    sc.register(selector,SelectionKey.OP_READ );
                    doWrite(sc);
                }else {
                    System.exit(1);
                }
            }
            if(key.isReadable()){
                ByteBuffer readBuffer=ByteBuffer.allocate(1024);
                int readBytes=sc.read(readBuffer);
                if(readBytes>0){
                    readBuffer.flip();
                    byte [] bytes=new byte[readBuffer.remaining()];
                    readBuffer.get(bytes);
                    String body=new String(bytes,"UTF-8");
                    System.out.println("Now is :"+body);
                    this.stop=true;
                }else if(readBytes<0){
                    key.cancel();
                    sc.close();
                }
            };
        }
    }

    private void doWrite(SocketChannel sc) throws IOException {
        byte [] req="QUERY TIME ORDER".getBytes();
        ByteBuffer writeBuffer=ByteBuffer.allocate(req.length);
        writeBuffer.put(req);
        writeBuffer.flip();
        sc.write(writeBuffer);
        if(!writeBuffer.hasRemaining()){
            System.out.println("Send order 2 server succeed");
        }
    }

}
