package com.kmeans;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.commons.io.FileUtils;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapred.*;
public class Main {
    public static void main(String[] args) throws IOException {
        // driver code
        JobConf conf = new JobConf(KMeans.class);
        conf.setJobName("wordcounter");
        conf.setOutputKeyClass(Text.class);
        conf.setOutputValueClass(DoubleWritable.class);
        conf.setMapperClass(KMeans.Map.class);
        conf.setCombinerClass(KMeans.Reduce.class);
        conf.setReducerClass(KMeans.Reduce.class);
        conf.setInputFormat(TextInputFormat.class);
        conf.setOutputFormat(TextOutputFormat.class);
        //  command line example:
        //  hadoop jar filename classpath inputfolder outputfolder
        FileInputFormat.setInputPaths(conf, new Path(args[0]));
        FileOutputFormat.setOutputPath(conf, new Path(args[1]));
        String inputFile = "data/data_points.txt";
        String outputDirectory = "output";


        float movementThreshold = 0.01f;
        int iterationCount;
        KMeans.dataFileLength = Helpers.getFileLength(inputFile);
        FileUtils.deleteDirectory(new File("results"));
        String resultsFile = "results/results.txt";

        File resultsDir = new File("results");
        if(!resultsDir.exists()){
            resultsDir.mkdirs();
        }

        BufferedWriter outStream = new BufferedWriter(new FileWriter(resultsFile, true));

        // kMeans with k = 4
        long startTime = System.currentTimeMillis();
        KMeans.k = 4;
        KMeans.centroidList = Helpers.sampleCentroids(KMeans.k, KMeans.dataFileLength, inputFile);
        iterationCount = runKMeans(movementThreshold, conf, outputDirectory, "part-00000");

        // printing and streaming to output
        System.out.format("kMeans algorithm with k = %d complete\n", KMeans.k);
        outStream.write(String.format("\nkMeans algorithm with k = %d complete.\n", KMeans.k));
        System.out.format("Iteration count = %d\n", iterationCount);
        outStream.write(String.format("Iteration count = %d\n", iterationCount));
        System.out.format("Movement threshold = %f\n", movementThreshold);
        outStream.write(String.format("Movement threshold = %f\n\n", movementThreshold));
        long endTime = System.currentTimeMillis();
        System.out.format("Execution time = " + (endTime - startTime) + "\n");
        outStream.write(String.format("Execution time = " + (endTime - startTime) * 0.001 + "s\n"));
        outStream.close();
        Helpers.printCentroids(outputDirectory + "/" + "part-00000", resultsFile);
        outStream = new BufferedWriter(new FileWriter(resultsFile, true));

        System.out.println("\n\nIntermission between k = 4 and k = 8\n\n");
        outStream.write(String.format("\n\nIntermission between k = 4 and k = 8\n\n"));

        // kMeans with k = 8
        startTime = System.currentTimeMillis();
        KMeans.k = 8;
        KMeans.centroidList = Helpers.sampleCentroids(KMeans.k, KMeans.dataFileLength, inputFile);
        iterationCount = runKMeans(movementThreshold, conf, outputDirectory, "part-00000");

        // printing and streaming to output
        System.out.format("\nkMeans algorithm with k = %d complete.\n", KMeans.k);
        outStream.write(String.format("\nkMeans algorithm with k = %d complete.\n", KMeans.k));
        System.out.format("Iteration count = %d\n", iterationCount);
        outStream.write(String.format("Iteration count = %d\n", iterationCount));
        System.out.format("Movement threshold = %f\n", movementThreshold);
        outStream.write(String.format("Movement threshold = %f\n", movementThreshold));
        endTime = System.currentTimeMillis();
        System.out.format("Execution time = " + (endTime - startTime) + "\n");
        outStream.write(String.format("Execution time = " + (endTime - startTime) * 0.001 + "s\n"));
        outStream.close();
        Helpers.printCentroids(outputDirectory + "/" + "part-00000", resultsFile);
        outStream = new BufferedWriter(new FileWriter(resultsFile, true));


        System.out.println("\n\nFinished running kMeans...");
        outStream.write(String.format("\n\nFinished running kMeans..."));
        outStream.close();
    }

    public static int runKMeans(float movementThreshold, JobConf conf, String outputDirectory, String resultsFile) throws IOException {
        int iterationCount = 0;
        do {
            FileUtils.deleteDirectory(new File(outputDirectory));
            JobClient.runJob(conf);
            iterationCount += 1;
        } while (Helpers.centroidsMoving(outputDirectory + "/" + resultsFile, movementThreshold));
        return iterationCount;
    }
}
