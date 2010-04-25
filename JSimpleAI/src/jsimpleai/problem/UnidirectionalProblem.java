/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jsimpleai.problem;

import java.util.List;

/**
 * This class defines all the necessary functions needed to represent a search
 * problem. {@link ai.search.TreeSearch} uses this class to perform all the operations.
 * <p>
 * <T> Is the class representing the state.
 *
 * @author Ragha
 */
public interface UnidirectionalProblem<T extends AbstractState>
{
    /**
     * Define this function to specify the start state of this search problem.
     * @return T The start state
     */
    public T getStartState();

    /**
     * Defines the "successor" function. It returns all the possible <i>valid</i>
     * states.
     * 
     * @param state The state to be expanded.
     * @return A list of all the possible states.
     */
    public List<T> getSuccessors(T state);

    /**
     * Defines the cost incurred for transitioning from one state to another.
     * @param oldState The old state
     * @param newState The new state
     * @return The cost incurred during transition.
     */
    public double getCost(T oldState, T newState);

    /**
     * Defines the conditions required to check for the goal state.
     * @param state The state to be checked.
     * @return true, if it is a goal state.
     */
    public boolean isGoalState(T state);
}
