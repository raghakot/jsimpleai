/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jsimpleai.search;

import jsimpleai.problem.AbstractState;
import jsimpleai.problem.UnidirectionalProblem;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Implements Iterative lengthening search..
 * @author Ragha
 */
public class IterativeLengtheningSearch <T extends AbstractState> extends TreeSearch<T>
{
    private Frontier f;

    /**
     * Create an appropriate frontier for iterative lengthening search..
     */
    public IterativeLengtheningSearch()
    {
        //create an appropriate comparator
        Comparator<SearchNode> c = new Comparator<SearchNode>()
        {
            public int compare(SearchNode o1, SearchNode o2) 
            {
                return -o1.compareTo(o2);
            }
        };
        f = new Frontier(new PriorityQueue<SearchNode>(101, c));
    }

    public void performIterativeLengtheningSearch(UnidirectionalProblem p)
    {
        seconds = System.nanoTime();
        double maxPathCost = 0, nextPathCost;
        boolean isGoalFound = false;

        while(true)
        {
            nextPathCost = Double.POSITIVE_INFINITY;
            SearchNode<T> root = new SearchNode<T>(null, (T) p.getStartState(), 0, 0);
            List<SearchNode> arrNodes = new ArrayList<SearchNode>();
            arrNodes.add(root);
            f.addSearchNodes(arrNodes);
           
            while(f.hasSearchNodes())
            {
                SearchNode<T> node = f.nextSearchNode();
                if(p.isGoalState(node.getState()))
                {
                    notifyGoal(node);
                    isGoalFound = true;
                    break;
                }
                arrNodes = node.getSuccessorSearchNodes(p);
                int size = arrNodes.size();
                
                for(int i=0; i < size; i++)
                {
                    SearchNode<T> sn = arrNodes.get(i);                    
                    if(sn.getTotalCost() > maxPathCost)
                    {                        
                        //update next path cost to be lowest of these..
                        if(nextPathCost >= sn.getTotalCost())
                            nextPathCost = sn.getTotalCost();                
                        arrNodes.remove(sn);
                        //To account for the shifting in the ArrayList
                        i--;
                        size--;
                    }                
                }                
                f.addSearchNodes(arrNodes);
            }

            if(isGoalFound)
                break;
            else
                maxPathCost = nextPathCost;            
        }
        seconds = (System.nanoTime() - seconds) / 1000000000;
    }
}
