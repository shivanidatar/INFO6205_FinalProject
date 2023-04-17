package com.example.info6205_team02.Christofides;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

//This class helps to implement threeOpt.
public class ThreeOpt {

    private double[][] distances;
    private List<Vertex> graph;
    private int secondsToRun;
    private LocalDateTime start;

    ThreeOpt(List<Vertex> g, double[][] d, int t) {
        distances = d;
        graph = g;
        secondsToRun = t/2;
    }


    private List<Vertex> runThreeOpt() {
        double bestDistance = calculateTotalDistance(graph);
        // We pick 3 indexes from the current route and send it to threeOptSwap method
        for (int i = 1; i < graph.size() - 3; i++) {
            for (int j = i + 1; j < graph.size() - 2; j++) {
                for(int k=j+1; k< graph.size()-1; k++) {

                    ArrayList<Vertex> newRoute = ThreeOptSwap(graph, i, k);
                    double newDistance = calculateTotalDistance(newRoute);
                    if (newDistance < bestDistance) {
                        graph = newRoute;
                        bestDistance = newDistance;

                    }

                    ArrayList<Vertex> newRoute2 = ThreeOptSwap(graph, j, k);
                    double newDistance2 = calculateTotalDistance(newRoute);
                    if (newDistance2 < bestDistance) {
                        graph = newRoute2;
                        bestDistance = newDistance2;

                    }
                }
            }
            System.out.println("Iteration "+i+" distance is "+bestDistance);
        }
        System.out.println("newDistance is "+bestDistance);
        return graph;
    }

    

    public List<Vertex> run() {
        graph = runThreeOpt();
        return graph;
    }

    private ArrayList<Vertex> ThreeOptSwap(List<Vertex> route, int i, int k) {

        ArrayList<Vertex> newRoute = new ArrayList<>();

        for (int first = 0; first <= i - 1; first++) {
            newRoute.add(route.get(first));
        }

        for (int last = k; last >= i; last--) {
            newRoute.add(route.get(last));
        }

        for (int end = k + 1; end <= route.size() - 1; end++) {
            newRoute.add(route.get(end));
        }

        return newRoute;
    }



    private double calculateTotalDistance(List<Vertex> path) {
        double totalDistance = 0;
        for (int i = 0; i < path.size() - 1; i++) {
            totalDistance += distances[path.get(i).getID()][path.get(i + 1).getID()];
        }
        totalDistance += distances[path.get(0).getID()][path.get(path.size() - 1).getID()];

        return totalDistance;
    }


}
