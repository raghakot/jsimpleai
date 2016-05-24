package jsimpleai.api.search;

import javax.annotation.Nonnull;

/**
 * Defined a heuristic cost function for the problem state T
 */
public interface Heuristic<T> {
    /**
     * Returns a heuristic cost estimate for reaching the goal state.
     */
    double getHeuristicCostEstimate(@Nonnull final SearchNode<T> node);
}
