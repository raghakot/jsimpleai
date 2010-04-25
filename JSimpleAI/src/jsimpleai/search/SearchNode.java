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
 * Represents the search node of a search tree.
 * <p>
 * <T> Is the class representing the state.
 *
 * @author Ragha
 */
public class SearchNode<T extends AbstractState> implements Comparable
{
    /**
     * Represents the parent node from which the current node has originated.
     */
    private SearchNode parent;

    /**
     * Represents the state of the current node.
     */
    private T state;

    /**
     * Represents the total path cost incurred for transitioning formt he root.
     */
    private double totalCost;

    /**
     * Represents the depth of this node in the search tree.
     */
    private long depth;

    public SearchNode(SearchNode parent, T state, double totalCost, long depth) {
        this.parent = parent;
        this.state = state;
        this.totalCost = totalCost;
        this.depth = depth;
    }

    public SearchNode() {
    }

    public long getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public SearchNode getParent() {
        return parent;
    }

    public void setParent(SearchNode parent) {
        this.parent = parent;
    }

    public T getState() {
        return state;
    }

    public void setState(T state) {
        this.state = state;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    /**
     * Lists all the possible {@code SearchNode} objects from the
     * given node.
     *
     * @param p The search problem.
     * @return A list of all the possible {@code SearchNode} objects.
     */
    List<SearchNode> getSuccessorSearchNodes(UnidirectionalProblem<T> p)
    {
        List<SearchNode> arrNodes = new ArrayList<SearchNode>();
        List<T> states = p.getSuccessors(this.state);
        for(T o : states)
        {
            SearchNode<T> sn = new SearchNode<T>();
            sn.parent = this;
            sn.state = o;
            sn.totalCost = this.totalCost + p.getCost(this.state, o);
            sn.depth = this.depth + 1;         
            arrNodes.add(sn);
        }        
        return arrNodes;
    }

    List<SearchNode> getPredecessorSearchNodes(BidirectionalProblem<T> p)
    {
        List<SearchNode> arrNodes = new ArrayList<SearchNode>();
        List<T> states = p.getPredecessors(this.state);        
        for(T o : states)
        {
            SearchNode<T> sn = new SearchNode<T>();
            sn.parent = this;
            sn.state = o;
            sn.totalCost = this.totalCost + p.getCost(o, this.state);
            sn.depth = this.depth + 1;
            arrNodes.add(sn);
        }
        return arrNodes;
    }

    /**
     * Finds the trace of nodes from the root state.
     * @return Ordered trace of nodes starting with the root node.
     */
    public List<SearchNode> getTraceFromRoot()
    {
        ArrayList<SearchNode> arrNodes = new ArrayList<SearchNode>();
        SearchNode current = this;

        while(current.parent != null)
        {
            arrNodes.add(0, current);
            current = current.parent;
        }
        arrNodes.add(0, current);

        return arrNodes;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final SearchNode<T> other = (SearchNode<T>) obj;
        if (this.parent != other.parent && (this.parent == null || !this.parent.equals(other.parent))) {
            return false;
        }
        if (this.state != other.state && (this.state == null || !this.state.equals(other.state))) {
            return false;
        }
        if (this.totalCost != other.totalCost) {
            return false;
        }
        if (this.depth != other.depth) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + (this.parent != null ? this.parent.hashCode() : 0);
        hash = 79 * hash + (this.state != null ? this.state.hashCode() : 0);
        hash = 79 * hash + (int) (Double.doubleToLongBits(this.totalCost) ^ (Double.doubleToLongBits(this.totalCost) >>> 32));
        hash = 79 * hash + (int) (this.depth ^ (this.depth >>> 32));
        return hash;
    }

    @Override
    public String toString()
    {
        StringBuffer sb = new StringBuffer();
        sb.append("------------Node Info------------\n");
        sb.append("State trace: ");
        List<SearchNode> arrNodes = getTraceFromRoot();
        for(SearchNode sn : arrNodes)
            sb.append(sn.getState() + " -> ");
        String ret = sb.substring(0, sb.length() - 3) + "\n";
        ret += "totPathCost: "+totalCost + "; depth = " + depth;
        return ret;
    }

    /**
     * Compares the search nodes based on the total cost incurred.
     * @param o The object to be compared
     * @return 0 if equal; 1 if this > o; -1 if this < o
     *
     */
    public int compareTo(Object o)
    {
        if(this.totalCost == ((SearchNode) o).totalCost)
            return 0;
        else if(this.totalCost > ((SearchNode) o).totalCost)
            return 1;
        else
            return -1;
    }
}
