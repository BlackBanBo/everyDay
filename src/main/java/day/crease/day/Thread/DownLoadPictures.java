package day.crease.day.Thread;

import day.crease.day.util.FileUtil;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * @ClassName: DownLoadPictures
 * @Description: 创建多个线程，下载网上的图片
 * @Author yzp
 * @Date 2021/1/26
 * @Version 1.0
 */
public class DownLoadPictures extends Thread {

    private String url;
    private String name;

   public  DownLoadPictures(String url,String name){
        this.url = url;
        this.name = name;
    }

    @Override
    public void run() {
       // 重写父类的run方法
        downLoad downLoad = new downLoad();
        downLoad.downPics(url,name);
        System.out.println("下载了"+name);
    }

    /**
     * 图片下载器
     */
    class downLoad{
        public void downPics(String url,String name){
            try {
                FileUtils.copyURLToFile(new URL(url),new File(name));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        DownLoadPictures thread1 = new DownLoadPictures("https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=3363295869,2467511306&fm=26&gp=0.jpg","1.jpg");
        DownLoadPictures thread2 = new DownLoadPictures("http%3A%2F%2Fa2.att.hudong.com%2F27%2F81%2F01200000194677136358818023076.jpg","2.jpg");
        DownLoadPictures thread3 = new DownLoadPictures("https://ss0.baidu.com/7Po3dSag_xI4khGko9WTAnF6hhy/zhidao/pic/item/9c16fdfaaf51f3de9ba8ee1194eef01f3a2979a8.jpg","3.jpg");

        thread1.start();
        thread2.start();
        thread3.start();
    }
}
