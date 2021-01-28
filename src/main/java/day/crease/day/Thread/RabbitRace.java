package day.crease.day.Thread;

/**
 * @ClassName: RabbitRace
 * @Description: 模拟龟兔赛跑：1、创建一个赛道；2、长度100步；3、乌龟和兔子同时赛跑，谁先跑完100步就是胜利者；
 * @Author yzp
 * @Date 2021/1/27
 * @Version 1.0
 */
public class RabbitRace implements Runnable {

    private volatile static String winner;


    @Override
    public void run() {
        // 还没产生胜利者，那就继续跑
        for (int i = 0; i <= 100; i++) {
            // 判断比赛是否结束,true表示结束
            boolean flag = isStop(i);
            if (flag) {
                break;
            }
            System.out.println(Thread.currentThread().getName() + "跑了" + i + "步");
        }
    }

    /**
     * 判断是否比赛结束：胜利者，步数=100
     *
     * @param step
     * @return
     */
    public boolean isStop(int step) {
        if (winner != null) {
            return true;
        }
        if (step == 100) {
            winner = Thread.currentThread().getName();
            System.out.println(Thread.currentThread().getName() + "赢了");
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        // 创建两个线程，分别代表乌龟和兔子
        RabbitRace rabbitRace = new RabbitRace();
        new Thread(rabbitRace, "乌龟").start();
        new Thread(rabbitRace, "兔子").start();
    }
}
