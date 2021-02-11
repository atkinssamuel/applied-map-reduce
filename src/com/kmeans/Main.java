package com.kmeans;

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
        JobConf conf = new JobConf(KMeans.class);
        conf.setJobName("wordcounter");
        conf.setOutputKeyClass(Text.class);
        conf.setOutputValueClass(IntWritable.class);
        conf.setMapperClass(KMeans.Map.class);
        conf.setCombinerClass(KMeans.Reduce.class);
        conf.setReducerClass(KMeans.Reduce.class);
        conf.setInputFormat(TextInputFormat.class);
        conf.setOutputFormat(TextOutputFormat.class);
        //  command line example:
        //  hadoop jar filename classpath inputfolder outputfolder
        FileInputFormat.setInputPaths(conf, new Path(args[0]));
        FileOutputFormat.setOutputPath(conf, new Path(args[1]));
        JobClient.runJob(conf);
    }

}
