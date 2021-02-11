package com.kmeans;
import java.util.*;
import java.io.IOException;

import org.apache.hadoop.io.*;
import org.apache.hadoop.mapred.*;

public class KMeans {
    public static class Map extends MapReduceBase implements Mapper<LongWritable, Text, Text, IntWritable> {
        private final static IntWritable one = new IntWritable(1);
        private Text word = new Text();
        public void map(LongWritable key, Text value, OutputCollector<Text, IntWritable> output, Reporter reporter) throws IOException{
            // mapper code
            String line = value.toString();
            String pointStr[] = line.split(",");
            List<Double> point = new ArrayList<>();
            point.add(Double.valueOf(pointStr[0]));
            point.add(Double.valueOf(pointStr[1]));

            StringTokenizer tokenizer = new StringTokenizer(line);
            while(tokenizer.hasMoreTokens()){
                word.set(tokenizer.nextToken());
                output.collect(word, one); // {Mary, 1}, {had, 1}, {a, 1}, {little, 1}, {lamb, 1}, {little, 1}, {lamb, 1}
            }
        }
    }

    public static class Reduce extends MapReduceBase implements Reducer<Text, IntWritable, Text, IntWritable> {
        public void reduce(Text key, Iterator<IntWritable> values, OutputCollector<Text, IntWritable> output, Reporter reporter) throws IOException{
            // reducer code
            // Mary: {1}, had: {1}, a: {1}, little: {1, 1}, lamb: {1, 1}
            int sum = 0;
            while(values.hasNext()){
                sum += values.next().get();
            }
            output.collect(key, new IntWritable(sum));
        }
    }
}