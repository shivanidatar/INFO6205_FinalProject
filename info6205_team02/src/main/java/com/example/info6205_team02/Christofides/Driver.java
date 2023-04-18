package com.example.info6205_team02.Christofides;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Driver {
    public static void main(String[] args) throws IOException {
        String filenameThatWillBeParsed = "src/main/java/com/example/info6205_team02/Input/info6205.spring2023.teamproject.csv";
        int secondsToRunTwoOpt = args.length != 2 ? 0 : Integer.parseInt(args[1]);
        int secondsToRunThreeOpt = args.length != 2 ? 0 : Integer.parseInt(args[1]);


        christofidesAlgorithmTwoOpt(filenameThatWillBeParsed, secondsToRunTwoOpt);
        christofidesAlgorithmThreeOpt(filenameThatWillBeParsed, secondsToRunThreeOpt);


    }

    public static ChristofidesTour christofidesAlgorithmTwoOpt(String inputFilePath, int secondsToRunTwoOpt) throws IOException {
        Benchmark benchmark = new Benchmark();
        benchmark.startMark();


        List<Vertex> theGraph = parseGraph(inputFilePath);
        double[][] distances = getDistances(theGraph);
        List<Vertex> minimumSpanningTree = PrimsAlgorithm.run(theGraph, distances);
        minimumSpanningTree = createEvenlyVertexedEularianMultiGraphFromMST(minimumSpanningTree, distances);
        List<Vertex> eulerTour = HierholzerAlgorithm.run(minimumSpanningTree);
        List<Vertex> travelingSalesPath = RemoveDuplicate.run(eulerTour);
        travelingSalesPath = EulerToHamilton.eulerToHamilton(travelingSalesPath);
        TwoOpt twoOpt = new TwoOpt(travelingSalesPath, distances, secondsToRunTwoOpt);
        travelingSalesPath = twoOpt.run();
        ArrayList<String> idList= new ArrayList<String>();
        try {
            idList = idMap("src/main/java/com/example/info6205_team02/Input/info6205.spring2023.teamproject.csv");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ChristofidesTour finalAnswer = finalAnswer(travelingSalesPath, distances);
        benchmark.endMark();
        System.out.println("Program for 2opt took: " + benchmark.resultTime() + " ms");
        int[] bestTourOrder = finalAnswer.getFinalTour().stream().mapToInt(i -> i).toArray();
        System.out.println("Best tour order:");
        for(int j=0;j<bestTourOrder.length;j++) {
            if(j%25==0){
                System.out.println();
            }
            if(j!=bestTourOrder.length-1) {
                System.out.print(idList.get(bestTourOrder[j])+"->");
            }
            else {
                System.out.print(idList.get(bestTourOrder[j]));
            }
        }
        System.out.println();
        WindowTSP_Christofides windowTSP = new WindowTSP_Christofides(bestTourOrder, distances, true);
        windowTSP.draw(bestTourOrder, distances);
        System.out.println("The length of MST is :" + minimumSpanningTree.size());
        System.out.println("The length of the tour is :" + bestTourOrder.length);
        return finalAnswer;


    }

    public static ChristofidesTour christofidesAlgorithmThreeOpt(String inputFilePath, int secondsToRunThreeOpt) throws IOException {
        Benchmark benchmark = new Benchmark();
        benchmark.startMark();
        List<Vertex> theGraph = parseGraph(inputFilePath);
        double[][] distances = getDistances(theGraph);
        List<Vertex> minimumSpanningTree = PrimsAlgorithm.run(theGraph, distances);
        minimumSpanningTree = createEvenlyVertexedEularianMultiGraphFromMST(minimumSpanningTree, distances);
        List<Vertex> eulerTour = HierholzerAlgorithm.run(minimumSpanningTree);
        List<Vertex> travelingSalesPath = RemoveDuplicate.run(eulerTour);
        //List<Vertex> travelingSalesPath = eulerToHamilton(eulerCycle);
        ThreeOpt threeOpt = new ThreeOpt(travelingSalesPath, distances, secondsToRunThreeOpt);
        travelingSalesPath = threeOpt.run();
        ChristofidesTour finalAnswer = finalAnswer(travelingSalesPath, distances);
        benchmark.endMark();
        ArrayList<String> idList= new ArrayList<String>();
        try {
            idList = idMap("src/main/java/com/example/info6205_team02/Input/info6205.spring2023.teamproject.csv");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Program for 3opt took: " + benchmark.resultTime() + " ms");
        int[] bestTourOrder = finalAnswer.getFinalTour().stream().mapToInt(i -> i).toArray();
        System.out.println("Best tour order:");
        for(int j=0;j<bestTourOrder.length;j++) {
            if(j%25==0){
                System.out.println();
            }
            if(j!=bestTourOrder.length-1) {
                System.out.print(idList.get(bestTourOrder[j])+"->");
            }
            else {
                System.out.print(idList.get(bestTourOrder[j]));
            }
        }
        System.out.println();
        WindowTSP_Christofides windowTSP = new WindowTSP_Christofides(bestTourOrder, distances, false);
        windowTSP.draw(bestTourOrder, distances);
        System.out.println("The length of MST is :" + minimumSpanningTree.size());
        System.out.println("The length of the tour is :" + bestTourOrder.length);
        return finalAnswer;
    }

    private static List<Vertex> parseGraph(String fileName) throws IOException {
        ArrayList<Vertex> vertexArrayList = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        String line;
        int count = 0;
        while ((line = br.readLine()) != null) {
            if (line.contains("crimeID")) continue;
            String[] arr = line.split(",");
            int id = count++;
            double x = Double.parseDouble(arr[1]);
            double y = Double.parseDouble(arr[2]);
            vertexArrayList.add(new Vertex(id, x, y));
        }
        return vertexArrayList;
    }


    // function calculates distances between all points on the graph
    private static double[][] getDistances(List<Vertex> graph) {
        double[][] distanceGraph = new double[graph.size()][graph.size()];
        for (int i = 0; i < graph.size(); i++) {
            for (int j = 0; j < graph.size(); j++) {
                distanceGraph[i][j] = difference(graph.get(i), graph.get(j));
            }
        }
        return distanceGraph;
    }

    // function that calculates the difference in location using Haversine Formula
    private static double difference(Vertex a, Vertex b) {
        double radius = 6371;
        double x1 = Math.toRadians(a.getX());
        double y1 = Math.toRadians(a.getY());
        double x2 = Math.toRadians(b.getX());
        double y2 = Math.toRadians(b.getY());
        double deltaLat = x2 - x1;
        double deltaLon = y2 - y1;
        double e = Math.pow(Math.sin(deltaLat / 2), 2) + (Math.cos(x1) * Math.cos(x2)) * Math.pow(Math.sin(deltaLon / 2), 2);
        double c = 2 * Math.atan2(Math.sqrt(e), Math.sqrt(1 - e));
        double distance = (radius * c);
        return distance;
    }

    /*
    This splits even and odds from each other and then creates a "perfect matching" (it doesn't actually,
    but attempts something close to), and then reconnects the graph. Please note this creates a Eulerian Multigraph
    which means edges can be connected to each other twice. 2 edges "A-B" "A-B" can exist from the same main.java.Vertex.
     * */
    private static List<Vertex> createEvenlyVertexedEularianMultiGraphFromMST(List<Vertex> minimumSpanningTree, double[][] distances) {

        List<Vertex> oddNumbers = minimumSpanningTree
                .stream()
                .filter(vertex -> vertex.connectedVertices.size() % 2 == 1)
                .collect(Collectors.toCollection(ArrayList::new));

        while (!oddNumbers.isEmpty()) {
            int distance = Integer.MAX_VALUE;
            final Vertex parent = oddNumbers.get(0);

            //Compare pointers to not use root node.
            double minDistanceToNextNode = oddNumbers
                    .stream()
                    .mapToDouble(vertex -> vertex == parent ? Integer.MAX_VALUE : distances[parent.getID()][vertex.getID()])
                    .min()
                    .getAsDouble();

            Vertex child = oddNumbers
                    .stream()
                    .filter(vertex -> distances[parent.getID()][vertex.getID()] == minDistanceToNextNode && vertex != parent)
                    .findFirst()
                    .get();

            Edge fromParentToChildEdge = new Edge(parent, child, distance);
            Edge fromChildToParentEdge = new Edge(child, parent, distance);
            parent.connectedVertices.add(fromParentToChildEdge);
            child.connectedVertices.add(fromChildToParentEdge);
            oddNumbers.remove(parent);
            oddNumbers.remove(child);
        }
        return minimumSpanningTree;
    }

    private static ChristofidesTour finalAnswer(List<Vertex> TSP, double[][] distances) {
        System.out.println("Best tour order");
        for (Vertex vertex : TSP) {
            System.out.print(vertex.getID() + " ");


        }
        System.out.println();
        double totalDistance = TSP.stream()
                .mapToDouble
                        (vertex -> TSP.indexOf(vertex) == TSP.size() - 1 ?
                                distances[TSP.get(0).getID()][TSP.get(TSP.size() - 1).getID()] //logic to connect end node to start node.
                                : distances[vertex.getID()][TSP.get(TSP.indexOf(vertex) + 1).getID()])
                .sum();
        System.out.println("Total distance covered of the " + TSP.size() + " vertices is: " + totalDistance);
        List<Integer> finalTour = TSP
                .stream()
                .map(vertex -> {
                    return vertex.getID();
                }).collect(Collectors.toCollection(ArrayList::new));
        return new ChristofidesTour(finalTour, totalDistance);
        }

    private static ArrayList<String> idMap(String path) throws IOException {
        ArrayList<String> idMapList = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(path));
        String line;
        while ((line = br.readLine()) != null) {
            ArrayList<Double> tempList = new ArrayList<>();
            if (line.contains("crimeID")) continue;
            String[] arr = line.split(",");
            String name = arr[0].substring(arr[0].length()-5);
            idMapList.add(name);
        }
        return idMapList;
    }


    }





