package com.kmeans;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;
import java.io.IOException;

import org.apache.avro.generic.GenericData;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapred.*;

public class KMeans {
    public static int k;
    public static long dataFileLength;
    public static List<List<Double>> centroidList = new ArrayList<>();

    public static class Map extends MapReduceBase implements Mapper<LongWritable, Text, Text, DoubleWritable> {
        public void map(LongWritable key, Text value, OutputCollector<Text, DoubleWritable> output, Reporter reporter) throws IOException{
            // mapper code
            String line = value.toString();
            List<Double> point = Helpers.getPointFromString(line);
            double minDistance = -1f;
            int minIndex = -1;
            for (int i = 0; i < KMeans.k; i++){
                double distance = Helpers.getEuclideanDistance(point, KMeans.centroidList.get(i));
                if (minDistance == -1f || distance < minDistance){
                    minDistance = distance;
                    minIndex = i;
                }
            }
            output.collect(new Text(String.valueOf(minIndex) + "x"), new DoubleWritable(point.get(0)));
            output.collect(new Text(String.valueOf(minIndex) + "y"), new DoubleWritable(point.get(1)));
        }
    }

    public static class Reduce extends MapReduceBase implements Reducer<Text, DoubleWritable, Text, DoubleWritable> {
        public void reduce(Text key, Iterator<DoubleWritable> values, OutputCollector<Text, DoubleWritable> output, Reporter reporter) throws IOException {
            // reducer code
            // Mary: {1}, had: {1}, a: {1}, little: {1, 1}, lamb: {1, 1}
            int counter = 0;
            Double sum = 0d;

            while (values.hasNext()) {
                sum += values.next().get();
                counter += 1;
            }
            output.collect(key, new DoubleWritable(sum/counter));
        }
    }
}