package com.example.info6205_team02.Christofides;

import java.util.List;

// This class contains the final tour and its associated cost
public class ChristofidesTour {
    private List<Integer> finalTour;
    private double tourCost;

    public ChristofidesTour(List<Integer> finalTour, double tourCost ) {
        this.finalTour = finalTour;
        this.tourCost = tourCost;
    }

    public ChristofidesTour() {
    }
    public List<Integer> getFinalTour() {
        return finalTour;
    }

    public void setFinalTour(List<Integer> finalTour) {
        this.finalTour = finalTour;
    }

    public double getTourCost() {
        return tourCost;
    }

    public void setTourCost(int tourCost) {
        this.tourCost = tourCost;
    }
}
