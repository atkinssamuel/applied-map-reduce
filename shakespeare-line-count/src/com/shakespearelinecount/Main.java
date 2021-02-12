package com.shakespearelinecount;

import java.io.*;

import org.apache.commons.io.FileUtils;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapred.*;

public class Main {

    public static void main(String[] args) throws IOException {
	    // write your code here
        File directory = new File(args[1]);
        FileUtils.deleteDirectory(directory);
        // driver code
        JobConf conf = new JobConf(LineCounter.class);
        conf.setJobName("linecounter");
        conf.setOutputKeyClass(Text.class);
        conf.setOutputValueClass(IntWritable.class);
        conf.setMapperClass(LineCounter.Map.class);
        conf.setCombinerClass(LineCounter.Reduce.class);
        conf.setReducerClass(LineCounter.Reduce.class);
        conf.setInputFormat(TextInputFormat.class);
        conf.setOutputFormat(TextOutputFormat.class);
        //  command line example:
        //  hadoop jar filename classpath inputfolder outputfolder
        FileInputFormat.setInputPaths(conf, new Path(args[0]));
        FileOutputFormat.setOutputPath(conf, new Path(args[1]));
        long startTime = System.currentTimeMillis();
        JobClient.runJob(conf);
        long executionTime = System.currentTimeMillis() - startTime;
        Main.summarizeResults(args[1] + "/part-00000", "results/results.txt", executionTime);
    }

    private static void summarizeResults(String outputFile, String resultsFile, long executionTime) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(outputFile));
        BufferedWriter outStream = new BufferedWriter(new FileWriter(resultsFile, true));
        String outputLine = bufferedReader.readLine();
        Integer lineCount = Integer.valueOf(outputLine.split("\t")[1]);
        record(String.format("shakespeare.txt contains a total of %d lines\n", lineCount), outStream);
        record("The MapReduce job took " + executionTime * 0.001 + "s to execute", outStream);
        outStream.close();
    }

    private static void record(String outputString, BufferedWriter outStream) throws IOException {
        System.out.print(outputString);
        outStream.write(outputString);
    }
}
