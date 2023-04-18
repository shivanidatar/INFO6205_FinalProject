package com.example.info6205_team02.SimulatedAnnealing;

import com.example.info6205_team02.Christofides.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Driver {
    public static void main(String[] args) throws IOException {
        SimulatedAnnealing sa = new SimulatedAnnealing();
        List<City> theGraph = parseGraph("src/main/java/com/example/info6205_team02/Input/info6205.spring2023.teamproject.csv");
        double[][] distances = getDistances(theGraph);
        List<Vertex> graph = new ArrayList<>();
        for(int i=0;i<theGraph.size();i++){
            graph.add(new Vertex(theGraph.get(i).getC(),theGraph.get(i).getX(),theGraph.get(i).getY()));
        }
        List<Vertex> minimumSpanningTree = PrimsAlgorithm.run(graph, distances);
        minimumSpanningTree = createEvenlyVertexedEularianMultiGraphFromMST(minimumSpanningTree, distances);
        List<Vertex> eulerTour = HierholzerAlgorithm.run(minimumSpanningTree);
        List<Vertex> travelingSalesPath = RemoveDuplicate.run(eulerTour);
        travelingSalesPath = EulerToHamilton.eulerToHamilton(travelingSalesPath);
        ArrayList<City> travelingPath = new ArrayList<>();
        for(int i=0;i<travelingSalesPath.size();i++){
            travelingPath.add(new City(travelingSalesPath.get(i).getID(),travelingSalesPath.get(i).getX(),travelingSalesPath.get(i).getY()));
        }
        Travel t = new Travel(travelingPath);
        sa.setTravel(t);
        sa.simulateAnnealing(10, 10000, 0.9997, distances);

    }
    private static List<City> parseGraph(String fileName) throws IOException {
        ArrayList<City> cityArrayList = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        String line;
        int count = 0;
        while ((line = br.readLine()) != null) {
            if (line.contains("crimeID")) continue;
            String[] arr = line.split(",");
            int id = count++;
            double x = Double.parseDouble(arr[1]);
            double y = Double.parseDouble(arr[2]);
            cityArrayList.add(new City(id, x, y));
        }
        return  cityArrayList;
    }
    // function calculates distances between all points on the graph
    private static double[][] getDistances(List<City> graph) {
        double[][] distanceGraph = new double[graph.size()][graph.size()];
        for (int i = 0; i < graph.size(); i++) {
            for (int j = 0; j < graph.size(); j++) {
                distanceGraph[i][j] = difference(graph.get(i), graph.get(j));
            }
        }
        return distanceGraph;
    }
    // function that calculates the difference in location using A^2 + B^2 = C^2
    private static double difference(City a, City b) {
//        double res = Math.sqrt(((b.getX() - a.getX()) * (b.getX() - a.getX())) + ((b.getY()-a.getY())*(b.getY()-a.getY())));
//        return res;
        double radius = 6371;
        //double x1 = a.getX()*Math.PI/180;
        double x1 = Math.toRadians(a.getX());
        //double y1=a.getY()*Math.PI/180;
        double y1 = Math.toRadians(a.getY());
        //double x2 = b.getX()*Math.PI/180;
        double x2 = Math.toRadians(b.getX());
        //double y2=b.getY()*Math.PI/180;
        double y2 = Math.toRadians(b.getY());
        double deltaLat = x2-x1;
        double deltaLon = y2-y1;
        double e = Math.pow(Math.sin(deltaLat/2),2)+(Math.cos(x1)*Math.cos(x2)) * Math.pow(Math.sin(deltaLon/2),2);
        double c = 2 *Math.atan2(Math.sqrt(e),Math.sqrt(1-e));
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


}
