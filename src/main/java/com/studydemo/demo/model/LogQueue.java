package com.studydemo.demo.model;

import com.studydemo.demo.model.entity.SysLog;
import org.springframework.stereotype.Component;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * @Description
 * @Author teronb
 * @Date 2023/2/8 11:04
 */
@Component("logQueue")
public class LogQueue {
    //linkedList实现了Queue队列,可以LinkedList做一个队列,这里用阻塞队列BlockingQueue
    private volatile Queue<SysLog> dataQueue = new LinkedBlockingDeque<>();

    public void add(SysLog sysLog){
        dataQueue.add(sysLog);
    }

    public SysLog poll(){
        return dataQueue.poll();
    }

    public int getSize(){
        return dataQueue.size();
    }
}
