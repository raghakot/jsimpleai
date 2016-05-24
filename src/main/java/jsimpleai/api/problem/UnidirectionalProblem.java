package jsimpleai.api.problem;

import javax.annotation.Nonnull;
import java.util.Collection;

/**
 * Defines a problem that can be solved using {@link jsimpleai.api.search.TreeSearch}
 */
public interface UnidirectionalProblem<T> {
    /**
     * Specifies the start state of this search problem.
     * @return T The start state
     */
    @Nonnull
    T getStartState();

    /**
     * Defines the "successor" function. It returns all the possible <i>valid</i>
     * states.
     * 
     * @param state The state to be expanded.
     * @return A collection of all the possible states.
     */
    @Nonnull
    Collection<T> getSuccessors(@Nonnull final T state);

    /**
     * Defines the cost incurred for transitioning from one state to another.
     *
     * @param oldState The old state
     * @param newState The new state
     * @return The cost incurred during transition.
     */
    double getCost(@Nonnull final T oldState,
                   @Nonnull final T newState);

    /**
     * Defines the conditions required to check for the goal state.
     *
     * @param state The state to be checked.
     * @return true, if it is a goal state.
     */
    boolean isGoalState(@Nonnull final T state);
}
