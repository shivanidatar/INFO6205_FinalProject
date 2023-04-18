package com.example.info6205_team02.AntColony;

//import com.example.info6205_team02.SimulatedAnnealing.SimulatedAnnealing;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Driver {
    public static void main(String[] args) {
        ArrayList<ArrayList<Double>> ecuFinalList= new ArrayList<ArrayList<Double>>();
        try {
            ecuFinalList = distanceFromFile("src/main/java/com/example/info6205_team02/Input/info6205.spring2023.teamproject.csv");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        AntColonyOptimization antColony = new AntColonyOptimization(ecuFinalList.size(), ecuFinalList);
        antColony.startAntOptimization();
    }

    public static ArrayList<ArrayList<Double>> distanceFromFile(String path) throws IOException {
        ArrayList<ArrayList<Double>> distanceArray = new ArrayList<ArrayList<Double>>();
        ArrayList<Double> tempDistList = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(path));
        String line;
        while ((line = br.readLine()) != null) {
            ArrayList<Double> tempList = new ArrayList<>();
            if (line.contains("crimeID")) continue;
            String[] arr = line.split(",");
            String name = arr[0];
            double p = Double.parseDouble(arr[1]);
            double q = Double.parseDouble(arr[2]);
            tempList.add(p);
            tempList.add(q);
            distanceArray.add(tempList);

        }
        ArrayList<ArrayList<Double>> ecuDistanceArray = new ArrayList<ArrayList<Double>>();
        double radius = 6371;
        for(int i=0;i<distanceArray.size();i++) {
            for(int j=0;j<distanceArray.size();j++) {
                double x1 = distanceArray.get(i).get(0)*Math.PI/180;
                double y1=distanceArray.get(i).get(1)*Math.PI/180;
                double x2 = distanceArray.get(j).get(0)*Math.PI/180;
                double y2=distanceArray.get(j).get(1)*Math.PI/180;
                double deltaLat = x2-x1;
                double deltaLon = y2-y1;
                double a = Math.pow(Math.sin(deltaLat/2),2)+(Math.cos(x1)*Math.cos(x2)) * Math.pow(Math.sin(deltaLon/2),2);
                double c = 2 *Math.atan2(Math.sqrt(a),Math.sqrt(1-a));
                double res = (radius * c);
                if(res==0) {
                    res=0.000000001;
                }
                tempDistList.add(res);
            }
            ecuDistanceArray.add(tempDistList);
            tempDistList=new ArrayList<>();
        }
        return ecuDistanceArray;
    }

}
