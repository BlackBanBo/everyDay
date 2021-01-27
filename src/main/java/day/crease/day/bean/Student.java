package day.crease.day.bean;

/**
 * @ClassName: Student
 * @Description: 创建一个饿汉式的单例模式
 * @Author yzp
 * @Date 2021/1/27
 * @Version 1.0
 */
public class Student {

    // 保证在类加载的时候就初始化
    private static final Student student = new Student();

    private Student() {
        System.out.println("实例化Student对象成功！");
    }

    public static Student getInstance(){
        return student;
    }

    public void run(){
        System.out.println("跑起来吧");
    }

}
