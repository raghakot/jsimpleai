/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jsimpleai.search;

import jsimpleai.problem.AbstractState;

/**
 * This class can registered with {@link TreeSearch} for listening to events
 * throughout the search process.
 * <p>
 * <T> Is the class representing the state.
 *
 * @author Ragha
 */
public interface SearchEventHandler<T extends AbstractState>
{
    /**
     * When registered, this function is automatically invoked by the
     * {@link TreeSearch#search(ai.problem.UnidirectionalProblem, ai.search.Frontier, long, long)  }
     * and {@link TreeSearch#search(ai.problem.BidirectionalProblem, ai.search.Frontier, ai.search.Frontier, long, long)}
     * methods to notify goal nodes.
     *
     * @param goalNode A goal node.
     */
    public void onGoalNode(SearchNode<T> goalNode);
}
