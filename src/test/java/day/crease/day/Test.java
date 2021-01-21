package day.crease.day;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

public class Test {
    public static void main(String[] args) {
        /*File fileDir = new File("G:\\yzp_crwaler");
       try{
           BufferedWriter writer = new BufferedWriter(new FileWriter(fileDir+"/"+"site_url.txt"));
           writer.write("ninini");
           writer.newLine();
           writer.flush();
           writer.close();
       }catch (Exception e){
           e.printStackTrace();
       }*/
        //test();
//      int res =   GCdivisor(1,15);
      //  System.out.println(res);
//        System.out.println(15%1);
        test2();
    }

    public static void test(){
        System.out.println(new Timestamp(System.currentTimeMillis()));
        String a = "2021-01-09 17:23:51.304464";
        System.out.println(Timestamp.valueOf(a));;
    }

    public static int GCdivisor(int m,int n){
        int tmp;
        if(m<n){
            tmp = m;
            m = n;
            n = tmp;
        }
        if (n==0){
            return m;
        }else{
            return GCdivisor(n,m%n);
        }
    }

    public static void test2(){
        Map<String,String> map = new TreeMap<>();
        map.put("cd","vfv");
        map.put("vf","aa");
        map.put("bgbg","nyhn");
        map.put("aw","ccd");
        map.put("za","rewr");
        map.put("ni","object");

        map.forEach((k,v)->{
            System.out.println(k);
        });
    }

}
