package day.crease.day.Thread;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @ClassName: TestThread
 * @Description: 设定每个线程处理多少条数据
 * @Author yzp
 * @Date 2021/1/29
 * @Version 1.0
 */
public class TestThread {


    public List<Student> getList() {
        // 创建100个对象的list
        List<Student> list = new LinkedList<>();
        for (int i = 0; i < 500; i++) {
            list.add(new Student(i));
        }
        return list;
    }

    public Integer getHandleList() throws Exception{
        /*for(Student student:getList()){
            Object jsonObject = JSONArray.toJSON(student);
            System.out.println(jsonObject.toString());
        }*/
        List<Student> list = getList();
        int count = 20;
        int listSize = list.size();
        // 线程数量=集合数/每次处理数量
        int RunSize = (listSize / count) + 1;

        // 获取线程执行结果
        List<Future> futures = new ArrayList<>();
        Future fut = null;

        // 获取学生个数
        Integer totalStu = 0;

        // 初始化线程池
        ThreadPoolExecutor executor  = new ScheduledThreadPoolExecutor(RunSize);

        // 新建对象数组，存放每个线程应该处理的值
        List<Student> newList = null;
        for (int i = 0; i < RunSize; i++) {
            // 如果只有一个线程
            if ((i + 1) == RunSize) {
                // 索引起始位置、结束位置
                int startIndex = i * count;
                int endIndex = list.size();
                newList = list.subList(startIndex, endIndex);
            } else {
                int startIndex = i * count;
                int endIndex = (i + 1) * count;
                newList = list.subList(startIndex, endIndex);
            }
            // 执行任务
            ExecupteHp execupteHp = new ExecupteHp(newList);
            fut = executor.submit(execupteHp);
            futures.add(fut);
        }
        for(Future future : futures){
            totalStu += (Integer)future.get();
        }
        return totalStu;

    }

    class ExecupteHp implements Callable {

        private List<Student> list;
        int total = 0;

        public ExecupteHp(List<Student> list) {
            this.list = list;
        }

        @Override
        public Object call() throws Exception {
            if (list != null && list.size() > 0) {
                for (Student student : list) {
                    System.out.println(Thread.currentThread().getName()+"处理学生"+student.getId());
                    total++ ;
                    Thread.sleep(100);
                }
                System.out.println(Thread.currentThread().getName()+"处理学生个数："+total);
            }
            return total;
        }
    }

    public static void main(String[] args) {
        TestThread testThread = new TestThread();
        try {
            System.out.println(testThread.getHandleList());
            int value = testThread.getHandleList();
            System.out.println("value="+value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
