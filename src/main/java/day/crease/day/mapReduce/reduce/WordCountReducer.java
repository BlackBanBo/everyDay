package day.crease.day.mapReduce.reduce;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * 接收mapper传过来的数据
 * 计算输入单词在输入文件中出现的次数
 */
public class WordCountReducer extends Reducer<Text, IntWritable, Text, IntWritable>  {
    /**
     *
     * @param key mapper传过来的key-单词
     * @param values mapper传过来的value-g个数
     * @param context
     * @throws IOException
     * @throws InterruptedException
     */
    @Override
    protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
        int count = 0;
        for(IntWritable value:values){
            count += value.get();
        }
        context.write(key,new IntWritable(count));
    }
}
