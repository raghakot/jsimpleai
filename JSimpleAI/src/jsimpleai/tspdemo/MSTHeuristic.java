/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jsimpleai.tspdemo;

import jsimpleai.search.SearchNode;
import jsimpleai.search.Heuristic;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.PriorityQueue;

/**
 * Implements the Minimum spanning tree heuristic for TSP. It uses
 * Prim's algorithm to build a MST.
 * 
 * @author Ragha
 */
public class MSTHeuristic implements Heuristic<TSPState> {
    //The adjacency matric to be used.

    private double costMatrix[][];

    /**
     * Creates a MST heuristic using the given adjacency matrix.
     * @param costMatrix The adjacency matrix to be used in the calculations.
     */
    public MSTHeuristic(double costMatrix[][]) {
        this.costMatrix = costMatrix;
    }

    /**
     * Gives a heuristic estimate for the cost required to reach the goal node..
     * @param current The node whose heuristic cost is to be calculated.
     * @return The cost estomate to the goal node based on MST.
     */
    public double getHeuristicCostEstimate(SearchNode<TSPState> current)
    {
        double h = 0;
        //Check if this is the goal node..
        if(current.getState().getNumVisitedCities() == costMatrix.length + 1)
            h = 0;
        else
        {
            /**
             * Generate all possible states it can goto, obvisouly not the
             * cities it has already visited..
             */            
            ArrayList<Integer> mstVertices = new ArrayList<Integer>();
            for(int i=0; i<costMatrix.length; i++)
            {
                if(current.getState().arrVisitedCities.get(i)== 0)
                {
                    mstVertices.add(i);                 
                }
            }
            //Add start city..
            mstVertices.add(0);
            //Add the current city to the list
            mstVertices.add(current.getState().city);
            
            //Applying prim's algorithm to generate MST
            //Generate Vertices and add them to the priority queue..
            PriorityQueue<Vertex> q = new PriorityQueue<Vertex>();
            //Also, maintain a hash table of Vertex objects keyed by vertex index..
            Hashtable<Integer, Vertex> ht = new Hashtable<Integer, Vertex>();
            for(int i : mstVertices)
            {
                Vertex v = new Vertex();
                if(i == current.getState().city)
                    v.dv = 0;
                else
                    v.dv = Double.MAX_VALUE;    //Infinity..
                v.p = null;
                v.v = i;

                //Add to queue..
                q.add(v);
                //Update hash table..
                ht.put(v.v, v);
            }

            while(!q.isEmpty())
            {
                Vertex u = q.remove();
                //For all vertices adjacent to this vertex..
                for(int vertexIndex : mstVertices)
                {
                    if(vertexIndex != u.v)
                    {
                        Vertex v = ht.get(vertexIndex);
                        //q.contains(v) => v is unknown..
                        if(q.contains(v) && v.dv > costMatrix[u.v][v.v])
                        {
                            v.dv = costMatrix[u.v][v.v];
                            v.p = u;
                        }
                    }
                }
            }   //End of prim's algo..

            //Finding the summation of all the edges in MST..
            for(Vertex v : ht.values())
            {
                if(v.p != null)
                    h += costMatrix[v.v][v.p.v];                
            }
        }
        return h;
    }

    private class Vertex implements Comparable<Vertex>
    {
        //vertex index.
        public int v;
        //shortest distance.
        public double dv;
        //parent
        public Vertex p;

        public int compareTo(Vertex o)
        {
            if(this.dv == o.dv)
                return 0;
            else if(this.dv > o.dv)
                return 1;
            else
                return -1;
        }
    }
}
