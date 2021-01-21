package day.crease.day.util;

import org.apache.hadoop.yarn.webapp.hamlet.Hamlet;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPool {
    /*创建缓存线程池*/
    public static void createPool(){
        ExecutorService threadPool = Executors.newFixedThreadPool(10);

        while(true){
            //提交多个线程任务，并执行
            threadPool.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName()+"is running!");
                    try{
                        Thread.sleep(3000);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    public static int test() throws Exception{
        return 8/0;
    }

    public static void main(String[] args) {
        try{
            ThreadPool.test();
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("11111");
        }
    }
}
