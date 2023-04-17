package com.example.info6205_team02.Christofides;

import java.util.LinkedList;
import java.util.List;

// Using Hierholzer Algorithm to make the euler cycle when provided with a minimal spanning tree.
/* Algorithm steps :-
1. Choose any vertex in the graph and add it to a stack.

2. While the stack is not empty, choose any vertex from the top of the stack and
follow a non-visited edge from that vertex to a new vertex. If there are no non-visited edges,
remove the vertex from the stack and add it to a circuit list.

3. If there are non-visited edges, add the new vertex to the stack and mark the edge as visited.

4. When you reach a vertex with no non-visited edges, add it to the circuit list and remove it from the stack.

5. Repeat steps 2-4 until all vertices have been visited.

6. Reverse the order of the vertices in the circuit list to obtain the Eulerian circuit.


 */
public class HierholzerAlgorithm {
    private HierholzerAlgorithm() {
    }

    public static List<Vertex> run(List<Vertex> vertexGraph) {
        List<Vertex> firstPath;
        List<Vertex> currentTour = new LinkedList<>(vertexGraph);
        firstPath = new LinkedList<>();
        while (currentTour.get(0).connectedVertices.size() != 0) {
            List<Vertex> returnedPath = returnAPath(currentTour.get(0));
            firstPath = runHelper(returnedPath);
        }
        return firstPath;
    }

    private static List<Vertex> runHelper(List<Vertex> firstPath) {
        for (int i = 0; i < firstPath.size(); i++) {
            if (firstPath.get(i).connectedVertices.size() > 0) {
                List<Vertex> addToPath;
                addToPath = returnAPath(firstPath.get(i));
                int indexSaved = i + 1;
                int addPathSize = addToPath.size();
                for (int j = 1; j < addPathSize; j++) {
                    firstPath.add(indexSaved, addToPath.get(0));
                    addToPath.remove(0);
                }
            }
        }

        return firstPath;
    }


    private static List<Vertex> returnAPath(Vertex pathStart) {
        List<Vertex> returnValue = new LinkedList<>();
        Vertex pathFinish = null;
        Vertex firstNode = pathStart;
        while (firstNode != pathFinish) {
            pathFinish = pathStart.connectedVertices.get(0).Child;
            pathStart.connectedVertices.remove(0);
            //remove the parent edge from child node
            for (int i = 0; i < pathFinish.connectedVertices.size(); i++) {
                if (pathFinish.connectedVertices.get(i).child == pathStart.getID()) {
                    pathFinish.connectedVertices.remove(i);
                    break;
                }
            }
            returnValue.add(pathStart);
            pathStart = pathFinish;
        }
        //This needs to be added to "complete" the loop.
        returnValue.add(pathFinish);
        return returnValue;
    }

}
