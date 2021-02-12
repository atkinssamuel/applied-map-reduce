package com.kmeans;


import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Helpers {
    public static long getFileLength(String directory) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(directory));
        long lines = 0;
        while (reader.readLine() != null) lines++;
        reader.close();
        return lines;
    }

    public static List<List<Double>> sampleCentroids(int k, long fileLength, String filePath) throws IOException {
        List<List<Double>> centroidList = new ArrayList<>();
        for (int i = 0; i < k; i++){
            int randomLineIndex = ThreadLocalRandom.current().nextInt(0, (int) (fileLength-1));
            BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
            for (int j = 0; j < randomLineIndex; j++)
                bufferedReader.readLine();
            String randomLine = bufferedReader.readLine();
            centroidList.add(getPointFromString(randomLine));
        }
        return centroidList;
    }

    public static List<Double> getPointFromString(String pointString){
        String pointStringList[] = pointString.split(",");
        List<Double> point = new ArrayList<>();
        point.add(Double.valueOf(pointStringList[0]));
        point.add(Double.valueOf(pointStringList[1]));
        return point;
    }

    public static double getEuclideanDistance(List<Double> first, List<Double> second) {
        return Math.sqrt(Math.pow((first.get(0) - second.get(0)), 2) + Math.pow((first.get(1) - second.get(1)), 2));
    }

    public static boolean centroidsMoving(String filePath, float movementThreshold) throws IOException{
        BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
        double centroidMovement = 0d;
        for (int i = 0; i < KMeans.k; i++){
            String xLine = bufferedReader.readLine();
            String yLine = bufferedReader.readLine();
            Double xPoint = Double.valueOf(xLine.split("\t")[1]);
            Double yPoint = Double.valueOf(yLine.split("\t")[1]);
            List<Double> newCentroid = Arrays.asList(xPoint, yPoint);
            List<Double> previousCentroid = KMeans.centroidList.get(i);
            centroidMovement += Helpers.getEuclideanDistance(previousCentroid, newCentroid);
            KMeans.centroidList.set(i, newCentroid);
        }
        return centroidMovement > movementThreshold;
    }

    public static void printCentroids(String filePath, String resultsFile) throws IOException{
        BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
        BufferedWriter outStream = new BufferedWriter(new FileWriter(resultsFile, true));
        for (int i = 0; i < KMeans.k; i++){
            String xLine = bufferedReader.readLine();
            String yLine = bufferedReader.readLine();
            double xPoint = Double.parseDouble(xLine.split("\t")[1]);
            double yPoint = Double.parseDouble(yLine.split("\t")[1]);
            System.out.format("Centroid %d: (%f, %f)\n", i+1, xPoint, yPoint);
            outStream.write(String.format("Centroid %d: (%f, %f)\n", i+1, xPoint, yPoint));
        }
        outStream.close();
    }
}
