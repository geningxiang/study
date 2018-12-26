package com.genx.mystudy.mr;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

/**
 * 单词统计类
 *
 * @author liming
 */
public class ModuleMapReduce extends Configured implements Tool {
    /**
     * TODO Map  开发时修改四个参数
     *
     * @author liming
     */
    public static class ModuleMapper extends Mapper<LongWritable, Text, Text, IntWritable> {
        public void map(LongWritable key, Text value, Context context)
                throws IOException, InterruptedException {
            //TODO 实现业务逻辑
        }

    }

    /**
     * TODO Reduce  开发时修改四个参数
     *
     * @author liming
     */
    public static class ModuleReducer extends Reducer<Text, IntWritable, Text, IntWritable> {
        protected void reduce(Text key, Iterable<IntWritable> values,
                              Context content)
                throws IOException, InterruptedException {
            //TODO 业务逻辑
        }

    }

    /**
     * Driver
     */
    // run 是 Tool中的方法
    public int run(String[] args) throws Exception {
        // 获取configuration 从继承的Configured类中获取
        Configuration cf = getConf();
        // 创建job
        try {
            // 配置文件 job名称
            Job job = Job.getInstance(cf, this.getClass().getSimpleName());
            // 设置运行类的类型
            job.setJarByClass(this.getClass());

            /**** input ******/
            // input map reduce output 串起来
            Path inPath = new Path(args[0]);
            FileInputFormat.addInputPath(job, inPath);
            /**** map ******/
            // map 方法类型嗯
            job.setMapperClass(ModuleMapper.class);
            // map 输出key value 类型
            job.setMapOutputKeyClass(Text.class);
            job.setOutputValueClass(IntWritable.class);
            /**** reduce ******/
            // reduce类型
            job.setReducerClass(ModuleReducer.class);

            // TODO reduce 输出 也就是job输出的类型 开发时需要修改
            job.setOutputKeyClass(Text.class);
            job.setOutputValueClass(IntWritable.class);
            /**** output ******/
            Path outPath = new Path(args[1]);
            FileOutputFormat.setOutputPath(job, outPath);
            /**** 提交job ******/
            // 返回布尔类型 这里设置true是打印日志信息 设置false是不打印日志
            boolean isSucc = job.waitForCompletion(true);

            return isSucc ? 0 : 1;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return 1;
    }

    public static void main(String[] args) throws Exception {
        //运行
        Configuration conf = new Configuration();
        //在这个里边 设置了传递的参数conf  然后在run方法中获取 都是父类的方法
        int status = ToolRunner.run(conf, new WordCountMapReduce(), args);
        System.exit(status);

    }


}