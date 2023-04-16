package com.example.info6205_team02.SimulatedAnnealing;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class Travel {
    private ArrayList<City> travel = new ArrayList<>();
    private ArrayList<City> previousTravel = new ArrayList<>();


    public Travel(String filePath) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(filePath));
        String line;
        int count = 0;
        while ((line = br.readLine()) != null) {
            ArrayList<Double> tempList = new ArrayList<>();
            if (line.contains("crimeID")) continue;
            String[] arr = line.split(",");
            String name = arr[0];
            double p = Double.parseDouble(arr[1]);
            double q = Double.parseDouble(arr[2]);
            City c = new City(count,p, q);
            travel.add(c);
            count ++;
        }
    }

    public void generateInitialTravel() {
        Collections.shuffle(travel);
    }


    public void swapCities() {
        int a = generateRandomIndex();
        int b = generateRandomIndex();
        previousTravel = new ArrayList<>(travel);
        City x = travel.get(a);
        City y = travel.get(b);
        travel.set(a, y);
        travel.set(b, x);
    }

    public void revertSwap() {
        travel = previousTravel;
    }

    private int generateRandomIndex() {
        return (int) (Math.random() * travel.size());
    }

    public City getCity(int index) {
        return travel.get(index);
    }

    public double getDistance() {
        int distance = 0;
        for (int index = 0; index < travel.size(); index++) {
            City starting = getCity(index);
            City destination;
            if (index + 1 < travel.size()) {
                destination = getCity(index + 1);
            } else {
                destination = getCity(0);
            }
            distance += starting.distanceToCity(starting,destination);
        }

        return distance;
    }

    public ArrayList<City> getTravel() {
        return travel;
    }

    public void setTravel(ArrayList<City> travel) {
        this.travel = travel;
    }
}
