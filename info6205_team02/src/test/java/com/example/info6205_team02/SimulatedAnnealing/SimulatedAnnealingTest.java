package com.example.info6205_team02.SimulatedAnnealing;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertNotNull;


public class SimulatedAnnealingTest {

    @Test
    void testSA1() throws IOException {
        SimulatedAnnealing sa = new SimulatedAnnealing();
        Travel travel = new Travel("src/test/java/com/example/info6205_team02/SimulatedAnnealing/test1.csv");
        sa.setTravel(travel);
        double [][]distances = new double[10][10];
        assertNotNull(sa.simulateAnnealing(10, 10000, 0.9995, distances));
    }

    @Test
    void testSA2() throws IOException {
        SimulatedAnnealing sa = new SimulatedAnnealing();
        Travel travel = new Travel("src/test/java/com/example/info6205_team02/SimulatedAnnealing/test2.csv");
        sa.setTravel(travel);
        double [][]distances = new double[10][10];
        assertNotNull(sa.simulateAnnealing(10, 10000, 0.9995, distances));
    }

}
