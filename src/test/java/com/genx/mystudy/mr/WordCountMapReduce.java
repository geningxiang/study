package com.genx.mystudy.mr;

import java.io.IOException;
import java.util.StringTokenizer;

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
//文件内容 类似
/*
 * aa  vv  bb
 *  tt  yy jj
 *  ss dd gg
 *  hh
 *  ff
 *  ddd dd
 * */

/**
 * 单词统计类
 *
 * @author liming
 */
public class WordCountMapReduce extends Configured implements Tool {
    /**
     * Map
     * Mapper<KEYIN, VALUEIN, KEYOUT, VALUEOUT>
     *
     * @author liming
     */
    public static class WorldCountMapper extends Mapper<LongWritable, Text, Text, IntWritable> {
        private Text mapOutputKey = new Text();
        private final static IntWritable mapOutPutValue = new IntWritable(1);

        @Override
        public void map(LongWritable key, Text value, Context context)
                throws IOException, InterruptedException {
            //TODO
            String lineValue = value.toString();
            //分割  lineValue.split(" ") 可以这样写  但是不好 吃内存
            StringTokenizer stringTokenizer = new StringTokenizer(lineValue);
            //迭代 拿数据
            while (stringTokenizer.hasMoreElements()) {
                String wordVal = stringTokenizer.nextToken();
                //变成key - value 对
                //map输出的key
                mapOutputKey.set(wordVal);
                //mapOutPutValue =1
                //map输出
                context.write(mapOutputKey, mapOutPutValue);
            }

        }

    }

    /**
     * Reduce
     * Map的类型 就是 Reduce的输入类型
     *
     * @author liming
     */
    public static class WorldCountReducer extends Reducer<Text, IntWritable, Text, IntWritable> {
        //reduce输出
        private IntWritable outPutVal = new IntWritable();

        protected void reduce(Text key, Iterable<IntWritable> values,
                              Context content)
                throws IOException, InterruptedException {

            //TODO
            /*map的输出类似于：
             * <aa,1>
             * <bb,1>
             * <jj,1>
             * <aa,1>
             *
             * map--->shuffle-->reduce 这个过程很复杂  也就是将相同key的value放到一个集合中 ----> <aa,list(1,1)>
             *  这叫做分组group
             *
             * */

            //统计和  比如：<aa,list(1,1) 结果是1+1=2 最后输出就是<aa,2>
            int sum = 0;
            for (IntWritable value : values) {
                sum += value.get();
            }
            outPutVal.set(sum);

            //reduce输出
            content.write(key, outPutVal);

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
            job.setMapperClass(WorldCountMapper.class);
            // map 输出key value 类型
            job.setMapOutputKeyClass(Text.class);
            job.setOutputValueClass(IntWritable.class);
            /**** reduce ******/
            // reduce类型
            job.setReducerClass(WorldCountReducer.class);
            // reduce 输出 也就是job输出
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
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return 1;
    }

    public static void main(String[] args) throws Exception {
        //运行
        //int status = new WordCountMapReduce().run(args);
        Configuration conf = new Configuration();
        //在这个里边 设置了传递的参数conf  然后在run方法中获取 都是父类的方法
        int status = ToolRunner.run(conf, new WordCountMapReduce(), args);

        System.exit(status);

    }


}