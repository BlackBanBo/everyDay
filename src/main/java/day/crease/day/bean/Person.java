package day.crease.day.bean;

/**
 * @ClassName: Person
 * @Description: 创建一个懒汉式的单例模式
 * @Author yzp
 * @Date 2021/1/27
 * @Version 1.0
 */
public class Person {

    // 懒汉式：在有需要的时候才去获取实例
    private static Person person = null;

    /**
     * 私有构造函数，避免外部实例化
     */
    private Person(){
        System.out.println("Person 初始化成功！");
    }

    /**
     * 对外提供获取实例的方法
     * 保证线程安全的条件：多个线程只能拿到唯一一个实例对象
     * @return
     */
    public static Person getInstance(){
        if(person == null){
            synchronized (Person.class){
                if(person == null){
                    person = new Person();
                }
            }
        }
        return person;
    }
}
