package day.crease.day.mapReduce.master;


import day.crease.day.mapReduce.mapper.WordCountMapper;
import day.crease.day.mapReduce.reduce.WordCountReducer;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

/**
 * 相当于yarm集群的客户端
 * 需要在这里封装mr程序的相关运行参数，指定jar包，最后交给jarm执行
 */
public class WordCount {
    public static void main(String[] args) throws Exception{
        Configuration configuration = new Configuration();
        Job job = Job.getInstance(configuration,"wordCount");

        //指定本程序的jar包所在本地路径
        job.setJarByClass(WordCount.class);

        //指定本业务使用的map业务类
        job.setMapperClass(WordCountMapper.class);
        //指定本业务使用的reducer业务类
        job.setReducerClass(WordCountReducer.class);

        //指定mapper输出的键值类型
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);

        //指定最终的输出键值类型
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        //指定job的输入原始文件目录
        FileInputFormat.setInputPaths(job,new Path(args[1]));
        //指定job的输出结果所在目录
        FileOutputFormat.setOutputPath(job,new Path(args[2]));

        //将配置好的job相关参数以及job所用的Java类所在的jar包，提交给yarn去运行
        job.submit();

        //提交job配置，一直等到运行结束
        boolean res = job.waitForCompletion(true);
        System.exit(res? 0:1);
    }

}
