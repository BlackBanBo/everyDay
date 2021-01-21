package day.crease.day.util;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.locks.ReentrantLock;

public class Test {
    private static transient volatile Object[] array;

    public static void main(String[] args) {
        copyTest();
    }
    /*
     * 1、创建一个新数组，有值
     * 2、拷贝新数组
     * 3、修改新数组的值
     * 4、赋值给新数组
     *
     * */
    public static void copyTest() {
        /*final ReentrantLock lock = this.lock;
        lock.lock();*/
        Object[] objects = {1, 2, 3, 5, "a", "b", "c"};
        Object[] newArray = Arrays.copyOf(objects, objects.length);
        System.out.println("修改前");
        for (Object s : newArray) {
            System.out.println(s);
        }
        // 修改数组里的值
        for (int i = 0; i < newArray.length; i++) {
            newArray[i] = 20;
        }
        System.out.println("修改后");
        for (Object s : newArray) {
            System.out.println(s);
        }
        array = objects;

        //lock.unlock();
    }

    public static void t1(String name, int age) {
        System.out.println(age);
        t2(name);
    }

    public static void t2(String name) {
        System.out.println(name);
    }
}
