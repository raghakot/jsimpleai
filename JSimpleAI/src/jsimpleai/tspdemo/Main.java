/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jsimpleai.tspdemo;

import jsimpleai.problem.UnidirectionalProblem;
import jsimpleai.search.Frontier;
import jsimpleai.search.FrontierFactory;
import java.io.File;
import jsimpleai.search.SearchEventHandler;
import jsimpleai.search.SearchNode;
import jsimpleai.search.TreeSearch;

/**
 * TSP solver; prints all possible tours..
 * @author Ragha
 */
public class Main
{
    public static int numCities;

    /**
     * Generates all possible goal states (need not be optimal)
     * @param args Pass the file name as the parameter
     */
    public static void main(String[] args) throws Exception
    {
        if(args.length != 1)
        {
            System.out.println("Usage: java -jar tsp.jar tspfile.txt");
            System.exit(0);
        }

        TSPProblem p = new TSPProblem();
        p.loadCostMatrix(new File(args[0]));
        numCities = p.getCostMatrix().getLength();
        SearchEventHandler<TSPState> handler = new SearchEventHandler<TSPState>()
        {
            public void onGoalNode(SearchNode<TSPState> goalNode)
            {                
                System.out.println("Goal node:\n" + goalNode);                
            }
        };

        TreeSearch<TSPState> s = new TreeSearch<TSPState>();
        s.addSearchEventHandler(handler);        
        
        System.out.println("Processing...Please wait!!!");

        Frontier front = FrontierFactory.createFrontierForAStar(
                    new MSTHeuristic(p.getCostMatrix().getCostMatrix()));
        s.search((UnidirectionalProblem) p, front, 1, Long.MAX_VALUE);
        
        System.out.println("Search completed in " + s.getSecsElapsedInLastSearch() + " secs");
    }
}
