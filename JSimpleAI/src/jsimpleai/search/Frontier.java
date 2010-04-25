/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jsimpleai.search;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;

/**
 * This class determines the node to be expanded from the frontier.
 * Appropriate data structure needs to be implemented.
 * <p>
 * <T> Is the class representing the state.
 * 
 * @author Ragha
 */
public class Frontier
{
    protected Queue<SearchNode> queue;
    protected List<SearchNode> list;

    /**
     * Initializes the frontier with the given queue data structure.
     * @param queue The queue data structure to be used as the frontier.
     */
    public Frontier(Queue<SearchNode> queue)
    {
        this.queue = queue;
    }

    /**
     * Initializes the frontier with the given list data structure.
     * @param list The list data structure to be used as the frontier.
     */
    public Frontier(List<SearchNode> list)
    {
        this.list = list;
    }

    /**
     * Adds a list of {@link SearchNode} to the frontier.
     * @param arrNodes The list of nodes to be added.
     */
    public void addSearchNodes(List<SearchNode> arrNodes)
    {
       if(queue != null)
           queue.addAll(arrNodes);
       if(list != null)
           list.addAll(arrNodes);
    }

    /**
     * Adds the given {@link SearchNode} to the frontier.
     * @param sn The search node to be added.
     */
    public void addSearchNode(SearchNode sn)
    {
        if(queue != null)
           queue.add(sn);
        if(list != null)
           list.add(sn);        
    }

    /**
     * Checks if the frontier is empty.
     * @return true, if the frontier is non-empty.
     */
    public boolean hasSearchNodes()
    {
        if(queue != null)
            return !queue.isEmpty();
        else
            return !list.isEmpty();
    }

    /**
     * Retreives and deletes the next search node to be expanded from
     * the frontier.
     * @return A search node from the frontier.
     */
    public SearchNode nextSearchNode()
    {
        if(queue != null)
            return queue.remove();
        else
            return list.remove(0);
    }

    /**
     * Retreives all the nodes from the frontier without removing it.
     * @return A list of all the available nodes..
     */
    public List<SearchNode> getAllNodesInFrontier()
    {
        int size = queue != null ? queue.size() : list.size();
        ArrayList<SearchNode> arrNodes = new ArrayList<SearchNode>(size);        
        Iterator<SearchNode> it = queue != null ? queue.iterator() : list.iterator();
        while(it.hasNext())
            arrNodes.add(it.next());
        return arrNodes;
    }
}
