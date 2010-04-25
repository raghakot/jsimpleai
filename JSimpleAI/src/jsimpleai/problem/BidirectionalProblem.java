/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jsimpleai.problem;

import java.util.List;

/**
 * Defines a bi-directional search problem.
 * @author Ragha
 */
public interface BidirectionalProblem<T extends AbstractState>
        extends UnidirectionalProblem<T>
{
    /**
     * Returns the final goal state to be reached.
     * @return The goal state to be reached.
     */
    public T getGoalState();

    /**
     * Defines the 'predecessor' function. It should return all the states that
     * can be traced backward from the current state.
     *
     * @param state The state to be back-traced.
     * @return A list of all the possible back-traceable states
     */
    public List<T> getPredecessors(T state);

    /**
     * Define if state s1 and s2 from front and back frontiers intersect.
     * If s1 and s2 intersect, then the paths from front and back frontiers
     * are conntected to determine the path trace.
     * <p>
     * Ex:
     * front = start ->....-> X
     * back = X ->....-> goal
     * return = goal state; updated with start->...->X->...->goal information.
     *
     * @param frontState  A state from 'front' frontier
     * @param backState  A state from 'back' frontier
     * @return true, if these nodes intersect.
     */
    public boolean isIntersecting(T frontState, T backState);
}
