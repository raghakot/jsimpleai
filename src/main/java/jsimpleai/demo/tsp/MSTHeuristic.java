package jsimpleai.demo.tsp;

import jsimpleai.api.search.Heuristic;
import jsimpleai.api.search.SearchNode;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.PriorityQueue;

/**
 * Implements the Minimum spanning tree heuristic for TSP.
 * Uses Prim's algorithm to build a MST.
 */
public class MSTHeuristic implements Heuristic<TSPState> {

    // The adjacency matrix
    private final double costMatrix[][];

    /**
     * Creates a MST heuristic using the given adjacency matrix.
     */
    public MSTHeuristic(final double costMatrix[][]) {
        this.costMatrix = costMatrix;
    }

    @Override
    public double getHeuristicCostEstimate(final SearchNode<TSPState> node) {
        double h = 0;

        //Check if this is the goal node..
        if (node.getState().getNumVisitedCities() == costMatrix.length + 1) {
            h = 0;
        } else {
            // Generate all possible states it can goto, obviously not the cities it has already visited.
            final ArrayList<Integer> mstVertices = new ArrayList<Integer>();
            for (int i = 0; i < costMatrix.length; i++) {
                if (node.getState().getVisitedCities()[i] == 0) {
                    mstVertices.add(i);
                }
            }

            // Add start city..
            mstVertices.add(0);
            // Add the current city to the list
            mstVertices.add(node.getState().getCurrentCity());

            // Applying prim's algorithm to generate MST
            // Generate Vertices and add them to the priority queue..
            final PriorityQueue<Vertex> q = new PriorityQueue<>();

            // Maintain a hash table of Vertex objects keyed by vertex index..
            final Hashtable<Integer, Vertex> ht = new Hashtable<>();
            for (final int i : mstVertices) {
                final Vertex v = new Vertex();
                if (i == node.getState().getCurrentCity()) {
                    v.dv = 0;
                }
                else {
                    v.dv = Double.MAX_VALUE;    //Infinity..
                }
                v.p = null;
                v.v = i;

                // Add to queue..
                q.add(v);
                // Update hash table..
                ht.put(v.v, v);
            }

            while (!q.isEmpty()) {
                final Vertex u = q.remove();

                // For all vertices adjacent to this vertex..
                for (final int vertexIndex : mstVertices) {
                    if (vertexIndex != u.v) {
                        final Vertex v = ht.get(vertexIndex);
                        // q.contains(v) => v is unknown..
                        if (q.contains(v) && v.dv > costMatrix[u.v][v.v]) {
                            v.dv = costMatrix[u.v][v.v];
                            v.p = u;
                        }
                    }
                }
            }
            // End of prim's algorithm.

            // Finding the summation of all the edges in MST..
            for (final Vertex v : ht.values()) {
                if (v.p != null) {
                    h += costMatrix[v.v][v.p.v];
                }
            }
        }
        return h;
    }

    private class Vertex implements Comparable<Vertex> {
        // vertex index.
        int v;
        // shortest distance.
        double dv;
        // parent
        Vertex p;

        public int compareTo(Vertex o) {
            if (this.dv == o.dv)
                return 0;
            else if (this.dv > o.dv)
                return 1;
            else
                return -1;
        }
    }
}
