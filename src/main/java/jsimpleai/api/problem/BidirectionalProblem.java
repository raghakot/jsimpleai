package jsimpleai.api.problem;

import javax.annotation.Nonnull;
import java.util.Collection;

/**
 * Defines a bi-directional search problem which can be solved by iterating the search tree forwards from
 * start state and backwards from goal state simultaneously.
 */
public interface BidirectionalProblem<T> extends UnidirectionalProblem<T>
{
    /**
     * Returns the final goal state to be reached.
     * @return The goal state to be reached.
     */
    @Nonnull
    T getGoalState();

    /**
     * Defines the 'predecessor' function. It should return all the states that
     * can be traced backward from the current state.
     *
     * @param state The state to be back-traced.
     * @return A collection of all the possible back-traceable states
     */
    @Nonnull
    Collection<T> getPredecessors(@Nonnull final T state);

    /**
     * Define if state s1 and s2 from front and back frontiers intersect.
     * If s1 and s2 intersect, then the paths from front and back frontiers
     * are connected.
     * <p>
     * Ex:
     * front = start ->....-> X
     * back = X ->....-> goal
     * return true since there is a path from start -> goal via X.
     *
     * @param frontState  A state from 'front' frontier
     * @param backState  A state from 'back' frontier
     * @return true, if these nodes intersect.
     */
    boolean isIntersecting(@Nonnull final T frontState,
                           @Nonnull final T backState);
}
