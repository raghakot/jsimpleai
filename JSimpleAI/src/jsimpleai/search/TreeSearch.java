/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jsimpleai.search;

import jsimpleai.problem.AbstractState;
import jsimpleai.problem.BidirectionalProblem;
import jsimpleai.problem.UnidirectionalProblem;
import java.util.ArrayList;
import java.util.List;

/**
 * A generic search class. It can be used to search for a goal node in the
 * state space.
 * <p>
 * <T> Is the class representing the state.
 * 
 * @author Ragha
 */
public class TreeSearch<T extends AbstractState>
{
    /**
     * Logs the no. of iterations performed in the last search..
     */
    protected long seconds = 0;
    /**
     * A list of all the registered event handlers..
     */
    private List<SearchEventHandler> arrHandlers;

    /**
     * Initiates a unidirectional search for all the goal nodes, defined by
     * {@link UnidirectionalProblem} in the search space.
     * <p>
     * Stopping conditions are determined by the numGoals and
     * depthLimit parameters.
     * 
     * @param p The unidirectional search problem.
     * @param f The frontier to be used.
     * @param numGoals number of goals to be searched before stopping.
     * If the given frontier is known to yeild optimal goal, one could use
     * numGoals = 1
     * @param depthLimit The max depth to be searched.
     */
    public void search(UnidirectionalProblem p, Frontier f, long numGoals, long depthLimit)
    {
        long goalCount = 0;
        seconds = System.nanoTime();
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
                goalCount++;
                if(goalCount == numGoals)
                    break;
            }
            if(node.getDepth() <= depthLimit)
            {
                arrNodes = node.getSuccessorSearchNodes(p);
                f.addSearchNodes(arrNodes);
            }
        }
        seconds = (System.nanoTime() - seconds) / 1000000000;
    }

    /**
     * Initiates a bidirectional search for all the goal nodes, defined by
     * {@link BidirectionalProblem} in the search space.
     * <p>
     * Stopping conditions are determined by the numGoals and
     * depthLimit parameters.
     *
     * @param p The Bidirectional search problem.
     * @param front The frontier to be used for the forward search.
     * @param back The frontier to be used for the backward search.
     * @param numGoals number of goals to be searched before stopping.
     * If the given frontier is known to yeild optimal goal, one could use
     * numGoals = 1
     * @param depthLimit The max depth to be searched.
     */
    public void search(BidirectionalProblem p, Frontier front, Frontier back,
            long numGoals, long depthLimit)
    {
        long goalCount = 0;
        seconds = System.nanoTime();

        SearchNode<T> start = new SearchNode<T>(null, (T) p.getStartState(), 0, 0);
        front.addSearchNode(start);
        SearchNode<T> goal = new SearchNode<T>(null, (T) p.getGoalState(), 0, 0);
        back.addSearchNode(goal);

        while(front.hasSearchNodes() && back.hasSearchNodes())
        {
            if(front.hasSearchNodes() && goalCount != numGoals)
            {
                SearchNode frontNode = front.nextSearchNode();

                /**
                 * Check if we reached goal node without intersection..
                 */
                if(p.isGoalState(frontNode.getState()))
                {
                    notifyGoal(frontNode);
                    goalCount++;
                    if(goalCount == numGoals)
                        break;
                }
                
                /**
                 * Check for intersections..
                 */
                List<SearchNode> arrBackNodes = back.getAllNodesInFrontier();
                for(SearchNode sn : arrBackNodes)
                {                    
                    if(p.isIntersecting(frontNode.getState(), sn.getState()))
                    {
                        notifyGoal(merge(p, frontNode, sn));                    
                        goalCount++;
                        if(goalCount == numGoals)
                            break;
                    }
                }

                //Do expansion..
                if(frontNode.getDepth() <= depthLimit)
                    front.addSearchNodes(frontNode.getSuccessorSearchNodes(p));                
            }

            if(back.hasSearchNodes() && goalCount != numGoals)
            {
                SearchNode backNode = back.nextSearchNode();

                /**
                 * Check if we reached goal node without intersection..
                 */
                if(p.isGoalState(backNode.getState()))
                {
                    notifyGoal(backNode);
                    goalCount++;
                    if(goalCount == numGoals)
                        break;
                }

                /**
                 * Check for intersections..
                 */
                List<SearchNode> arrFrontNodes = front.getAllNodesInFrontier();
                for(SearchNode sn : arrFrontNodes)
                {
                    if(p.isIntersecting(sn.getState(), backNode.getState()))
                    {
                        notifyGoal(merge(p, sn, backNode));
                        goalCount++;
                        if(goalCount >= numGoals)
                            break;
                    }
                }

                //Do expansion..
                if(backNode.getDepth() <= depthLimit)
                    back.addSearchNodes(backNode.getPredecessorSearchNodes(p));
            }
            if(goalCount >= numGoals)
                break;
        }
        seconds = (System.nanoTime() - seconds) / 1000000000;
    }

    /**
     * Returns a merger of nodes from front and back frontiers.
     * @param p The problem.
     * @param front A node from front frontier.
     * @param back A node form back frontier.
     * @return A goal node after merging intersecting nodes 'front' and 'back'
     */
    private SearchNode<T> merge(UnidirectionalProblem p, SearchNode front, SearchNode back)
    {        
        while(back.getParent() != null)
        {
            back = back.getParent();
            SearchNode sn = new SearchNode(front, back.getState(), 
                    front.getTotalCost() + p.getCost(front.getState(), back.getState()),
                    front.getDepth() + 1);
            front = sn;            
        }
        return front;
    }

    protected void notifyGoal(SearchNode goal)
    {
        for(SearchEventHandler handler : arrHandlers)
            handler.onGoalNode(goal);
    }

    /**
     * Fetches the no. seconds elapsed in performing the last search operation.
     * @return The no. of seconds elapsed during the last search.
     */
    public long getSecsElapsedInLastSearch()
    {
        return seconds;
    }

    /**
     * Adds the specified SearchEventHandler to receive notifications
     * from the search algo. If handler is null, no exception is thrown
     * and no action is performed.
     *
     * @param handler The listener to be used for notifications.
     * @see SearchEventHandler
     */
    public void addSearchEventHandler(SearchEventHandler handler)
    {
        if(arrHandlers == null)
            arrHandlers = new ArrayList<SearchEventHandler>();
        if(handler != null)
            arrHandlers.add(handler);
    }

    /**
     * Removes a pre-registered handler
     * @param handler The listener object to be removed,
     * @return true, if the listener is removed.
     */
    public boolean removeSearchEventHandler(SearchEventHandler handler)
    {
        return arrHandlers.remove(handler);
    }

    /**
     * Removes the handler at 'index'. If index is out of bounds
     * then this method simply returns.
     * @param index The index at which listener is to be removed.
     */
    public void removeSearchEventHandler(int index)
    {
        if(index >= 0 && index < arrHandlers.size())
            arrHandlers.remove(index);
    }

    /**
     * Checks if a handler is registered.
     * @param handler The listener instance to be checked.
     * @return true, if the listener is registered.
     */
    public boolean containsSearchEventHandler(SearchEventHandler handler)
    {
        return arrHandlers.contains(handler);
    }

    /**
     * Removes all the pre-registered handlers..
     */
    public void removeAllSearchEventHandlers()
    {
        arrHandlers.clear();
    }
}
