package com.example.info6205_team02.SimulatedAnnealing;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

//
public class SimulatedAnnealing {
    private  Travel travel;


// code starts by initializing the SimulatedAnnealing class, where the Travel object contains a list of cities with their coordinates.
// The SimulatedAnnealing object generates an initial solution by calling the generateInitialTravel() method,
// which shuffles the cities randomly.
    public double simulateAnnealing(double startingTemperature, int numberOfIterations, double coolingRate, double[][] distances) {
        System.out.println("Starting SA with temperature: " + startingTemperature + ", # of iterations: " + numberOfIterations + " and colling rate: " + coolingRate);
        double t = startingTemperature;
        //travel.generateInitialTravel();
        double bestDistance = travel.getDistance();
        System.out.println("Initial distance of travel: " + bestDistance);
        Travel bestSolution = travel;
        Travel currentSolution = bestSolution;

     //the code calculates the distance between every pair of cities using the Haversine formula,
     // and creates an adjacency matrix representing the distances between every pair of cities.

        double[][] distanceGraph = new double[travel.getTravel().size()][travel.getTravel().size()];
        double distance= 0;
        for (int i = 0; i < travel.getTravel().size(); i++) {
            for (int j = 0; j < travel.getTravel().size(); j++) {
                City starting = travel.getCity(i);
                City destination=travel.getCity(j);
                double radius = 6371;
                double x1 = starting.getX()*Math.PI/180;
                double y1=starting.getY()*Math.PI/180;
                double x2 = destination.getX()*Math.PI/180;
                double y2=destination.getY()*Math.PI/180;
                double deltaLat = x2-x1;
                double deltaLon = y2-y1;
                double e = Math.pow(Math.sin(deltaLat/2),2)+(Math.cos(x1)*Math.cos(x2)) * Math.pow(Math.sin(deltaLon/2),2);
                double c = 2 *Math.atan2(Math.sqrt(e),Math.sqrt(1-e));
                double distance3 = (radius * c);
                distance = distance +distance3;
                distanceGraph[i][j]=distance3;
            }
        }
        int[] node = new int[bestSolution.getTravel().size()];
        for(int j=0;j<bestSolution.getTravel().size();j++){
            node[j]=bestSolution.getTravel().get(j).getC();
        }
        WindowTSP_SA windowTSP_sa = new WindowTSP_SA(node,distances);
        //The main loop of the Simulated Annealing algorithm is implemented using a for-loop that runs for
        // a specified number of iterations. In each iteration, the current solution is swapped with a
        // neighboring solution, and the new solution is accepted with a certain probability, based
        // on the Metropolis criterion. The temperature decreases at each iteration, according to a
        // specified cooling schedule.

        for (int i = 0; i < numberOfIterations; i++) {
            long startTime = System.nanoTime();

            if (t > 0.1) {
                currentSolution.swapCities();
                double currentDistance = currentSolution.getDistance();
                if (currentDistance < bestDistance) {
                    bestDistance = currentDistance;
                } else if (Math.exp((bestDistance - currentDistance) / t) < Math.random()) {
                    currentSolution.revertSwap();
                }
                t *= coolingRate;
            } else {
                continue;
            }

            System.out.println("Iteration #" + i);
            System.out.println("Best Distance is :" + bestDistance+" km");
            long endTime = System.nanoTime();
            float runTime = (float)(endTime - startTime);
            System.out.println("Takes time in nanoseconds :"+ runTime);

            System.out.println();



        }
        int[] node2 = new int[bestSolution.getTravel().size()];
        System.out.println("The tour length is: "+bestSolution.getTravel().size());
        System.out.println("Best Tour is :");
        for(int j=0;j<bestSolution.getTravel().size();j++){
            System.out.print(bestSolution.getTravel().get(j).getC() + " ");
            node2[j]=bestSolution.getTravel().get(j).getC();
        }

        ArrayList<String> idList= new ArrayList<String>();
        try {
            idList = idMap("src/main/java/com/example/info6205_team02/Input/info6205.spring2023.teamproject.csv");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

//The best solution found during the algorithm is stored in the bestSolution object, and its distance is returned at the end of the algorithm.
// Finally, the code outputs the best tour path and draws it using a graphical user interface.
        System.out.println("Best tour is: ");
        for(int j=0;j<bestSolution.getTravel().size();j++) {
            if(j%25==0){
                System.out.println();
            }
            if(j!=bestSolution.getTravel().size()-1) {
                System.out.print(idList.get(bestSolution.getTravel().get(j).getC())+"->");
            }
            else {
                System.out.print(idList.get(bestSolution.getTravel().get(j).getC()));
            }
        }
        System.out.println();

        windowTSP_sa.draw(node2, distances);

        return bestDistance;
    }

    public Travel getTravel() {
        return travel;
    }

    public void setTravel(Travel travel) {
        this.travel = travel;
    }

    private ArrayList<String> idMap(String path) throws IOException {
        ArrayList<String> idMapList = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(path));
        String line;
        while ((line = br.readLine()) != null) {
            if (line.contains("crimeID")) continue;
            String[] arr = line.split(",");
            String name = arr[0].substring(arr[0].length()-5);
            idMapList.add(name);
        }
        return idMapList;
    }

}



