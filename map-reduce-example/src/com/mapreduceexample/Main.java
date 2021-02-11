package com.mapreduceexample;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapred.*;

public class Main {


    public static void main(String[] args) throws IOException {
        File directory = new File("output");
        FileUtils.deleteDirectory(directory);
	    // driver code
        JobConf conf = new JobConf(WordCounter.class);
        conf.setJobName("wordcounter");
        conf.setOutputKeyClass(Text.class);
        conf.setOutputValueClass(IntWritable.class);
        conf.setMapperClass(WordCounter.Map.class);
        conf.setCombinerClass(WordCounter.Reduce.class);
        conf.setReducerClass(WordCounter.Reduce.class);
        conf.setInputFormat(TextInputFormat.class);
        conf.setOutputFormat(TextOutputFormat.class);
        //  command line example:
        //  hadoop jar filename classpath inputfolder outputfolder
        FileInputFormat.setInputPaths(conf, new Path(args[0]));
        FileOutputFormat.setOutputPath(conf, new Path(args[1]));
        JobClient.runJob(conf);
    }

}
