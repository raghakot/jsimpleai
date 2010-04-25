/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jsimpleai.search;

import jsimpleai.problem.AbstractState;
import jsimpleai.search.SearchNode;

/**
 *
 * @author Ragha
 */
public interface Heuristic<T extends AbstractState>
{
    public double getHeuristicCostEstimate(SearchNode<T> current);
}
