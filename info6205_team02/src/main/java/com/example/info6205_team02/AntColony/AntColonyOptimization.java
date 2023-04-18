package com.example.info6205_team02.AntColony;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.IntStream;

public class AntColonyOptimization {

    private double c = 1.0;
    private double alpha = 1;
    private double beta = 5;
    private double evaporation = 0.5;
    private double Q = 500;
    private double antFactor = 0.8;
    private double randomFactor = 0.01;

    private int maxIterations = 1000;

    private int numberOfCities;
    private int numberOfAnts;
    private double[][] graph;
    private double trails[][];
    private List<Ant> ants = new ArrayList<>();
    private Random random = new Random();
    private double probabilities[];

    private int evaluations=0;

    private int currentIndex;

    private int[] bestTourOrder;
    private double bestTourLength;


    public AntColonyOptimization(int noOfCities, ArrayList<ArrayList<Double>> ecuFinalList) {
        //graph = generateRandomMatrix(noOfCities);
        double[][] arr = ecuFinalList.stream()
                .map(l -> l.stream().mapToDouble(Double::doubleValue).toArray())
                .toArray(double[][]::new);
        graph = arr.clone();
        numberOfCities = graph.length;
        numberOfAnts = (int) (numberOfCities * antFactor);

        trails = new double[numberOfCities][numberOfCities];
        random.setSeed(18);
        probabilities = new double[numberOfCities];
        IntStream.range(0, numberOfAnts)
                .forEach(i -> ants.add(new Ant(numberOfCities)));
    }

    /**
     * Generate initial solution
     */
    public double[][] generateRandomMatrix(int n) {
        random.setSeed(18);
        double[][] randomMatrix = new double[n][n];
        IntStream.range(0, n)
                .forEach(i -> IntStream.range(0, n)
                        .forEach(j -> randomMatrix[i][j] = Math.abs(random.nextInt(100) + 1)));
        return randomMatrix;
    }

    /**
     * Perform ant optimization
     */
    public void startAntOptimization() {
        //WindowTSP windowTSP = new WindowTSP(bestTourOrder, graph);
        ArrayList<String> idList= new ArrayList<String>();
        try {
            idList = idMap("src/main/java/com/example/info6205_team02/Input/info6205.spring2023.teamproject.csv");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ArrayList<String> finalIdList = idList;
        IntStream.rangeClosed(1, 10)
                .forEach(i -> {
                    long startTime = System.currentTimeMillis();
                    System.out.println("Attempt #" + i);
                    solve();
                    WindowTSP windowTSP = new WindowTSP(bestTourOrder, graph);
                    windowTSP.draw(bestTourOrder, graph);
                    System.out.println("Best tour order: " + Arrays.toString(bestTourOrder));
                    System.out.println("Best tour order:");
                    for(int j=0;j<bestTourOrder.length;j++) {
                        if(j%25==0){
                            System.out.println();
                        }
                        if(j!=bestTourOrder.length-1) {
                            System.out.print(finalIdList.get(bestTourOrder[j])+"->");
                        }
                        else {
                            System.out.print(finalIdList.get(bestTourOrder[j]));
                        }
                    }
                    System.out.println();
                    long endTime = System.currentTimeMillis();
                    float runTime = (float)(endTime - startTime)/1000;
                    System.out.println("Takes time in seconds :"+ runTime);
                });
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


    /**
     * Use this method to run the main logic
     */
    public int[] solve() {
        setupAnts();
        clearTrails();
        IntStream.range(0, maxIterations)
                .forEach(i -> {
                    moveAnts();
                    updateTrails();
                    updateBest();
                });
        System.out.println("Best tour distance: " + ((bestTourLength)) +" km");
        return bestTourOrder.clone();

    }

    /**
     * Prepare ants for the simulation
     */
    private void setupAnts() {
        IntStream.range(0, numberOfAnts)
                .forEach(i -> {
                    ants.forEach(ant -> {
                        ant.clear();
                        ant.visitCity(-1, random.nextInt(numberOfCities));
                    });
                });
        currentIndex = 0;
    }

    /**
     * At each iteration, move ants
     */
    private void moveAnts() {
        IntStream.range(currentIndex, numberOfCities-1)
                .forEach(i -> {
                    ants.forEach(ant -> ant.visitCity(currentIndex, selectNextCity(ant)));
                    currentIndex++;
                });
    }

    /**
     * Select next city for each ant
     */
    private int selectNextCity(Ant ant) {
        int t = random.nextInt(numberOfCities - currentIndex);
        if (random.nextDouble() < randomFactor) {
            OptionalInt cityIndex = IntStream.range(0, numberOfCities)
                    .filter(i -> i == t && !ant.visited(i))
                    .findFirst();
            if (cityIndex.isPresent()) {
                return cityIndex.getAsInt();
            }
        }
        calculateProbabilities(ant);
        double r = random.nextDouble();
        double total = 0;
        for (int i = 0; i < numberOfCities; i++) {
            total += probabilities[i];
            if (total >= r) {
                return i;
            }
        }
        throw new RuntimeException("There are no other cities");
    }

    /**
     * Calculate the next city picks probabilites
     */
    public void calculateProbabilities(Ant ant) {
        int i = ant.trail[currentIndex];
        double pheromone = 0.0;
        for (int l = 0; l < numberOfCities; l++) {
            if (!ant.visited(l)) {
                pheromone += Math.pow(trails[i][l], alpha) * Math.pow(1.0 / graph[i][l], beta);
            }
        }
        for (int j = 0; j < numberOfCities; j++) {
            if (ant.visited(j)) {
                probabilities[j] = 0.0;
            } else {
                double numerator = Math.pow(trails[i][j], alpha) * Math.pow(1.0 / graph[i][j], beta);
                probabilities[j] = numerator / pheromone;
            }
        }
    }

    /**
     * Update trails that ants used
     */
    private void updateTrails() {
        for (int i = 0; i < numberOfCities; i++) {
            for (int j = 0; j < numberOfCities; j++) {
                trails[i][j] *= evaporation;
            }
        }
        for (Ant a : ants) {
            double contribution = Q / a.trailLength(graph);
            for (int i = 0; i < numberOfCities - 1; i++) {
                trails[a.trail[i]][a.trail[i + 1]] += contribution;
            }
            trails[a.trail[numberOfCities - 1]][a.trail[0]] += contribution;
        }
    }



    /**
     * Update the best solution
     */
    private void updateBest() {
        if (bestTourOrder == null) {
            bestTourOrder = ants.get(0).trail;
            bestTourLength = ants.get(0)
                    .trailLength(graph);
        }
        for (Ant a : ants) {
            if (a.trailLength(graph) < bestTourLength) {
                bestTourLength = a.trailLength(graph);
                bestTourOrder = a.trail.clone();
                evaluations = evaluations +1;
            }
        }
    }

    /**
     * Clear trails after simulation
     */
    private void clearTrails() {
        IntStream.range(0, numberOfCities)
                .forEach(i -> {
                    IntStream.range(0, numberOfCities)
                            .forEach(j -> trails[i][j] = c);
                });
    }

}
