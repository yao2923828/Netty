package com.phei.netty.pio;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * <p>标题: </p>
 * <p>功能描述: </p>
 *
 * <p>版权: 税友软件集团股份有限公司</p> 
 * <p>创建时间: 2018/12/24 23:24</p> 
 * <p>作者：yaoq</p>
 * <p>修改历史记录：</p>
 * ====================================================================<br>
 */
public class TimerServerHandlerExecutorPool {
    private ExecutorService executor;
    public TimerServerHandlerExecutorPool(int maxPoolSize,int queueSize){
        executor=new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors(), maxPoolSize, 120L, TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(queueSize));
    }
    public void execute(Runnable task){
        executor.execute(task);
    }

}
