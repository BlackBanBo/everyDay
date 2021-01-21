package day.crease.day.config;

import day.crease.day.util.HTTPUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;

/**
 * 站长之家-网站规则top200
 */
public class CrawlerSitesUrl {
    // 爬取地址
    static  URL realURL=null;
    static URLConnection connection=null;
    static File fileDir;
    static PrintWriter pw;
    static BufferedReader reader;
    static String line = null;
    public static void crwal(String url){
       try{
           realURL = new URL(url);
           connection = realURL.openConnection();
           // 新建文件，用于存放爬取结果
           fileDir = new File("G:\\yzp_crwaler");
           if(!fileDir.exists()){
               fileDir.mkdirs();
           }
           pw = new PrintWriter(new FileWriter(fileDir+"/"+"site_url.txt"),true);
           reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
           line = reader.readLine();
           while (line  != null){
                pw.print(line);
           }
           System.out.println("爬完了！！");
       }catch (Exception e){
            e.printStackTrace();
       }finally {
           try{
               pw.close();
               reader.close();
           }catch (IOException e){
               e.printStackTrace();
           }
       }

    }

    public static void main(String[] args) {
        System.out.println("开始爬了……");
        //初始化一个httpclient
        crwal("https://top.chinaz.com/hangye/");
    }
}
