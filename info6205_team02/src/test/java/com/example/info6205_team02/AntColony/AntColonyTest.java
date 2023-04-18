package com.example.info6205_team02.AntColony;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AntColonyTest {

    @Test
    void testStartAntOptimization() {
        ArrayList<ArrayList<Double>> distanceArray = new ArrayList<ArrayList<Double>>();
        ArrayList<Double> l1 = new ArrayList<>();
        l1.add(0.01);
        l1.add(1.2);
        l1.add(2.4);
        ArrayList<Double> l2 = new ArrayList<>();
        l2.add(5.6);
        l2.add(0.01);
        l2.add(3.1);
        ArrayList<Double> l3 = new ArrayList<>();
        l3.add(2.5);
        l3.add(4.1);
        l3.add(0.01);
        distanceArray.add(l1);
        distanceArray.add(l2);
        distanceArray.add(l3);
        AntColonyOptimization antTSP = new AntColonyOptimization(3,distanceArray);
        antTSP.startAntOptimization();
        assertNotNull(antTSP.solve());
    }

    @Test
    void testStartAntOptimization2() {
        ArrayList<ArrayList<Double>> distanceArray = new ArrayList<ArrayList<Double>>();
        ArrayList<Double> l1 = new ArrayList<>();
        l1.add(0.01);
        l1.add(1.2);
        l1.add(2.4);
        l1.add(4.7);
        l1.add(6.8);
        ArrayList<Double> l2 = new ArrayList<>();
        l2.add(5.6);
        l2.add(0.01);
        l2.add(3.1);
        l2.add(7.1);
        l2.add(1.5);
        ArrayList<Double> l3 = new ArrayList<>();
        l3.add(2.5);
        l3.add(4.1);
        l3.add(0.01);
        l3.add(2.2);
        l3.add(1.8);
        ArrayList<Double> l4 = new ArrayList<>();
        l4.add(1.1);
        l4.add(3.1);
        l4.add(2.3);
        l4.add(0.01);
        l4.add(2.6);
        ArrayList<Double> l5 = new ArrayList<>();
        l5.add(1.6);
        l5.add(2.2);
        l5.add(3.5);
        l5.add(2.7);
        l5.add(0.01);
        distanceArray.add(l1);
        distanceArray.add(l2);
        distanceArray.add(l3);
        distanceArray.add(l4);
        distanceArray.add(l4);
        AntColonyOptimization antTSP = new AntColonyOptimization(5,distanceArray);
        antTSP.startAntOptimization();
        assertNotNull(antTSP.solve());
    }
}
