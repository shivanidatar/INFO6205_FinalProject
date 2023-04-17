package com.example.info6205_team02.Christofides;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

// Using this class to obtain the hamilton cycle from euler cycle
public class EulerToHamilton {
    public static List<Vertex> eulerToHamilton(List<Vertex> eulerCycle) {
        // Creating an empty set to keep track of visited vertices
        Set<Vertex> visited = new HashSet<>();
        // Initializing the Hamiltonian cycle with the first vertex in the Euler cycle
        List<Vertex> hamiltonCycle = new ArrayList<>();
        Vertex start = eulerCycle.get(0);
        hamiltonCycle.add(start);
        visited.add(start);
        // Loop through the vertices in the Euler cycle
        for (int i = 1; i < eulerCycle.size(); i++) {
            Vertex vertex = eulerCycle.get(i);
            // If the current vertex has not been visited before, add it to the Hamiltonian cycle
            if (!visited.contains(vertex)) {
                // Find the index of the previous vertex in the Hamiltonian cycle
                int index = hamiltonCycle.indexOf(eulerCycle.get(i - 1));
                // Insert the current vertex into the Hamiltonian cycle after the previous vertex
                hamiltonCycle.add(index + 1, vertex);
                visited.add(vertex);
            }
        }
        return hamiltonCycle;
    }
}
