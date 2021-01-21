package day.crease.day.util;


import java.text.NumberFormat;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static java.lang.Thread.sleep;

/**
 * 生成指定的订单编码
 */
public class OrderNumber implements Runnable{

    public static void main(String[] args) {
        for (int i = 0;i<1000;i++){
            new OrderNumber().run();
        }
    }

    @Override
    public synchronized void  run() {
        try{
            NumberFormat numberFormat = NumberFormat.getInstance();
            numberFormat.setMaximumFractionDigits(2);
            int a = 3;
            int b = 99;
            String f = numberFormat.format((float)a/(float)b*100);
            System.out.println(f);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
