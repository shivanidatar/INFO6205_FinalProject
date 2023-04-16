package com.example.info6205_team02.Christofides;

import java.util.Comparator;

// This class is to implement the compare method, and check the weights of the corresponding weights of the edges.
public class VertexComparator implements Comparator<Vertex> {

    public int compare(Vertex vertexOne, Vertex vertexTwo) {
        if (vertexOne.edge.weight > vertexTwo.edge.weight) {
            return 1;
        } else if (vertexOne.edge.weight < vertexTwo.edge.weight) {
            return -1;
        } else {
            return 0;
        }
    }

}
