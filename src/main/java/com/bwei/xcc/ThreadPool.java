package com.bwei.xcc;
import java.util.concurrent.*;
/**
 * Created by Administrator on 2017/10/25.
 */
public class ThreadPool {
    public static void main(String args[]){
        ExecutorService threadPool = Executors.newFixedThreadPool(2 );
        MyTask t1 = new MyTask("线程1");
        MyTask t2 = new MyTask("线程2");
        MyTask t3 = new MyTask("线程3");
        threadPool.execute(t1);
        threadPool.execute(t3);
        threadPool.execute(t2);
    }
}
class MyTask implements Runnable{
    private String tname;
    public MyTask(String tname){
        this.tname = tname;
    }
    public void run(){
        System.out.println("\n=========任务"+tname+"开始执行===========");
        for(int i = 0;i<5;i++){
            System.out.println("["+tname+"_"+i+"]");
        }
        System.out.println("\n=========任务"+tname+"执行结束===========");
    }
}
