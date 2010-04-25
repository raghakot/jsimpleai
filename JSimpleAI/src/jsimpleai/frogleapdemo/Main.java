/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jsimpleai.frogleapdemo;

import jsimpleai.search.FrontierFactory;
import jsimpleai.search.SearchEventHandler;
import jsimpleai.search.SearchNode;
import jsimpleai.search.TreeSearch;
import java.util.List;

/**
 *
 * @author Ragha
 */
public class Main
{
    public static void main(String[] args)
    {
        FrogProblem p = new FrogProblem();
        TreeSearch<FrogState> search = new TreeSearch<FrogState>();
        search.addSearchEventHandler(new SearchEventHandler()
        {
            public void onGoalNode(SearchNode goalNode)
            {
               List<SearchNode> arrStates = goalNode.getTraceFromRoot();
                System.out.println("Tot Moves: " + arrStates.size());
               for(SearchNode node : arrStates)
               {
                   System.out.println( ((FrogState)node.getState()) );
               }
                System.out.println("---------------------------------------" +
                        "--------------------------------------------------");
            }
        });
        search.search(p, FrontierFactory.createFrontierForUCS(), 10, Long.MAX_VALUE);
        System.out.println("Time taken = " +search.getSecsElapsedInLastSearch() + " secs");
    }
}
