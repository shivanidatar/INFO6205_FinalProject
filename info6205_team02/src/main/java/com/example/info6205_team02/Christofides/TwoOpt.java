package com.example.info6205_team02.Christofides;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

//This class helps to implement twoOpt.
public class TwoOpt {
  private double[][] distances;
  private List<Vertex> graph;
  private int secondsToRun;
  private LocalDateTime start;

    TwoOpt(List<Vertex> g, double[][] d, int t) {
        distances = d;
        graph = g;
        secondsToRun = t/2;
    }

   private List<Vertex> runTwoOpt() {
            double bestDistance = calculateTotalDistance(graph);
            // We pick 2 indexes from the current route and send it to twoOptSwap method
            for (int i = 1; i < graph.size() - 1; i++) {
                for (int k = i + 1; k < graph.size() - 2; k++) {
                    ArrayList<Vertex> newRoute = TwoOptSwap(graph, i, k);
                    double newDistance = calculateTotalDistance(newRoute);
                    // We change the route if the new route distance is shorter than the previous route distance
                    if (newDistance < bestDistance) {
                        graph = newRoute;
                        bestDistance = newDistance;


                    }

                }


            }
       System.out.println("The best new distance is :"+bestDistance +" m");

        return graph;
    }


    public List<Vertex> run() {
        graph = runTwoOpt();
        return graph;
    }

   private ArrayList<Vertex> TwoOptSwap(List<Vertex> route, int i, int k) {

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


    // Calculating total distance for a path
   private double calculateTotalDistance(List<Vertex> path) {
        double totalDistance = 0;
        for (int i = 0; i < path.size() - 1; i++) {
            totalDistance += distances[path.get(i).getID()][path.get(i + 1).getID()];
        }
        totalDistance += distances[path.get(0).getID()][path.get(path.size() - 1).getID()];

        return totalDistance;
    }
}
