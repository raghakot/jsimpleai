/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jsimpleai.search;

import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;

/**
 * This class can be used to fetch useful frontiers
 * @author Ragha
 */
public class FrontierFactory
{
    private FrontierFactory()
    {

    }

    public static Frontier createFrontierForUCS()
    {
        Frontier f = new Frontier(new PriorityQueue<SearchNode>());
        return f;
    }

    public static Frontier createFrontierForBFS()
    {
        Queue<SearchNode> q = new FIFOQueue<SearchNode>();
        Frontier f = new Frontier(q);
        return f;        
    }

    public static Frontier createFrontierForDFS()
    {
        Frontier f = new Frontier(new Stack<SearchNode>());
        return f;
    }

    public static Frontier createFrontierForAStar(final Heuristic heuristic)
    {
        /**
         * Sorts by distance, which is total of pathCost + heuristicCost
         */
        Comparator<SearchNode> c = new Comparator<SearchNode>()
        {
            public int compare(SearchNode o1, SearchNode o2)
            {
                double dist1 = o1.getTotalCost() + heuristic.getHeuristicCostEstimate(o1);
                double dist2 = o2.getTotalCost() + heuristic.getHeuristicCostEstimate(o2);
                if(dist1 == dist2)
                    return 0;
                else if(dist1 > dist2)
                    return 1;
                else
                    return -1;
            }
        };
        //This priority queue sorts elements using comparator created above..
        PriorityQueue<SearchNode> q = new PriorityQueue<SearchNode>(101, c);
        Frontier f = new Frontier(q);
        return f;
    }

    private static class FIFOQueue<E> extends LinkedList<E> implements Queue<E>
    {
        private static final long serialVersionUID = 1;

        public FIFOQueue()
        {
            super();
        }

        public FIFOQueue(Collection<? extends E> c)
        {
            super(c);
        }

        @Override
        public boolean isEmpty()
        {
            return 0 == size();
        }


        @Override
        public E pop()
        {
            return poll();
        }


        public Queue<E> insert(E element)
        {
            if (offer(element))
            {
                return this;
            }
            return null;
        }
    }
}
