package com.example.info6205_team02.SimulatedAnnealing;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
//This line defines a public class called "Travel" which contains two private variables: "travel" and "previousTravel".
// "travel" is an ArrayList of objects of type "City", which represents the order in which the cities are visited.
// "previousTravel" is another ArrayList of "City" objects that keeps track of the previous order of cities.
public class Travel {
    private ArrayList<City> travel = new ArrayList<>();
    private ArrayList<City> previousTravel = new ArrayList<>();

//This code defines a constructor for the "Travel" class that takes a file path as an argument.
// It reads in the data from the file and creates a new "City" object for each line of data.
// It then adds each "City" object to the "travel" ArrayList.
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
//This code randomly shuffles the order of the cities in the "travel" ArrayList.
    public void generateInitialTravel() {
        Collections.shuffle(travel);
    }

//This code swaps the position of two random cities in the "travel" ArrayList.
// It does this by generating two random indexes and swapping the "City" objects at those indexes.
// It also saves the previous order of cities in the "previousTravel" ArrayList.
    public void swapCities() {
        int a = generateRandomIndex();
        int b = generateRandomIndex();
        previousTravel = new ArrayList<>(travel);
        City x = travel.get(a);
        City y = travel.get(b);
        travel.set(a, y);
        travel.set(b, x);
    }
//This code reverts the last swap by restoring the order of cities in the "travel" ArrayList
// to the previous order stored in the "previousTravel" ArrayList.
    public void revertSwap() {
        travel = previousTravel;
    }
//This code generates a random index within the bounds of the "travel" ArrayList.
    private int generateRandomIndex() {
        return (int) (Math.random() * travel.size());
    }
//This code returns the "City" object at a specific index in the "travel" ArrayList.
    public City getCity(int index) {
        return travel.get(index);
    }
//This method calculates the total distance of the current travel plan.
// It initializes the distance variable to 0 and then iterates over each city in the travel list.
// For each city, it gets the starting city and determines the destination city.
// If the current city is not the last city in the travel list, then the destination city is the next
// city in the list. Otherwise, the destination city is the first city in the list.
// It then adds the distance between the starting and destination cities to the distance variable.
// Finally, it returns the total distance of the travel plan.
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
    public Travel(ArrayList<City> travel) {
        this.travel = travel;
    }
    //This method returns the list of cities in the current travel plan.
    public ArrayList<City> getTravel() {
        return travel;
    }
    //This method sets the list of cities in the current travel plan to the given travel list.
    public void setTravel(ArrayList<City> travel) {
        this.travel = travel;
    }
}
