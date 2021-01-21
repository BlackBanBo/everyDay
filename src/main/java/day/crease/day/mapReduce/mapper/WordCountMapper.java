package day.crease.day.mapReduce.mapper;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * 可初始化配置，指定数据源
 * 拆分输入文件每一行的内容
 * 输出拆分结果，作为reducer的输入
 * @author yuanzhongping
 */
public class WordCountMapper extends Mapper {
    /**
     * 重写map方法
     * @param key 输入文件行首偏移量 默认应该是Long类型，这里对应Hadoop中的序列化类型Longwriteable
     * @param value 输入行内容 默认应该是String类型，Hadoop中的序列化类型为Text
     * @param context 上下文，承上启下
     * @throws IOException
     * @throws InterruptedException
     */
    @Override
    protected void map(Object key, Object value, Context context) throws IOException, InterruptedException {
        String line = value.toString();
        String[] words = line.split(" ");
        for(String word:words){
            //将单词作为key，值1 作为value,以便后续的数据分发
            //以便于相同单词会用到相同的reduce task
            //map task会收集写入一个文件中
         context.write(new Text(word),new IntWritable(1));
        }
    }
}
