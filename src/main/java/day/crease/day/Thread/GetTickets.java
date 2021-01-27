package day.crease.day.Thread;

/**
 * @ClassName: TestThread
 * @Description: 模拟抢票机制
 * @Author yzp
 * @Date 2021/1/26
 * @Version 1.0
 */
public class GetTickets implements Runnable{

    // 假设一共10张票
    private int ticketNum = 10;

    @Override
    public void run() {
        while (true){
            synchronized (GetTickets.class){
                if (ticketNum <= 0){
                    break;
                }
                System.out.println(Thread.currentThread().getName()+"抢到了第"+ticketNum--+"张票");
            }
            // 抢完释放锁，休息100毫秒
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        GetTickets thread = new GetTickets();
        // 3个人抢这10张票
        new Thread(thread,"花花").start();
        new Thread(thread,"小白兔").start();
        new Thread(thread,"蜗牛").start();
    }
}
