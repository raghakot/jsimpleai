package jsimpleai.api.search;

import com.google.common.base.Preconditions;
import jsimpleai.api.problem.BidirectionalProblem;
import jsimpleai.api.problem.UnidirectionalProblem;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Represents a search node within the search tree of problem state T
 */
public class SearchNode<T> implements Comparable {
    /**
     * Represents the parent node from which the current node has originated.
     */
    private final SearchNode<T> parent;

    /**
     * Represents the state of the current node.
     */
    private final T state;

    /**
     * Represents the total path cost incurred for transitioning form the root.
     */
    private final double totalCost;

    /**
     * Represents the depth of this node in the search tree.
     */
    private final long depth;

    /**
     * Constructs a new instance of {@link SearchNode}
     */
    public SearchNode(@Nullable final SearchNode<T> parent,
                      @Nonnull final T state,
                      double totalCost,
                      long depth) {
        this.parent = parent;
        this.state = Preconditions.checkNotNull(state);
        this.totalCost = totalCost;
        this.depth = depth;
    }

    long getDepth() {
        return depth;
    }

    @Nullable
    SearchNode getParent() {
        return parent;
    }

    @Nonnull
    public T getState() {
        return state;
    }

    double getTotalCost() {
        return totalCost;
    }

    /**
     * Lists all the possible successor {@code SearchNode} from the given node.
     */
    @Nonnull
    Collection<SearchNode<T>> getSuccessorSearchNodes(@Nonnull final UnidirectionalProblem<T> problem) {
        Preconditions.checkNotNull(problem);

        final Collection<SearchNode<T>> expanded = new ArrayList<SearchNode<T>>();
        for (final T newState : problem.getSuccessors(state)) {
            expanded.add(new SearchNode<T>(this, newState, totalCost + problem.getCost(state, newState), depth + 1));
        }
        return expanded;
    }

    /**
     * Lists all the possible predecessor {@code SearchNode} from the given node.
     */
    @Nonnull
    Collection<SearchNode<T>>  getPredecessorSearchNodes(@Nonnull final BidirectionalProblem<T> problem) {
        Preconditions.checkNotNull(problem);

        final Collection<SearchNode<T>> expanded = new ArrayList<SearchNode<T>>();
        for (final T newState : problem.getPredecessors(state)) {
            expanded.add(new SearchNode<T>(this, newState, totalCost + problem.getCost(state, newState), depth + 1));
        }
        return expanded;
    }

    /**
     * Finds the trace of nodes from the root state.
     *
     * @return Ordered trace of nodes starting with the root node.
     */
    @Nonnull
    public Collection<SearchNode<T>> getTraceFromRoot() {
        final List<SearchNode<T>> trace = new ArrayList<SearchNode<T>>();

        SearchNode<T> current = this;
        while (current.parent != null) {
            trace.add(current);
            current = current.parent;
        }
        trace.add(current);

        // Reverse to get trace from root.
        Collections.reverse(trace);
        return trace;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SearchNode)) return false;

        SearchNode<?> that = (SearchNode<?>) o;

        if (Double.compare(that.totalCost, totalCost) != 0) return false;
        if (depth != that.depth) return false;
        if (!parent.equals(that.parent)) return false;
        return state.equals(that.state);
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = parent.hashCode();
        result = 31 * result + state.hashCode();
        temp = Double.doubleToLongBits(totalCost);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (int) (depth ^ (depth >>> 32));
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("------------Node Info------------\n");

        sb.append("State trace from root: ");
        for (final SearchNode<T> node : getTraceFromRoot()) {
            sb.append(node.getState()).append(" -> ");
        }

        String ret = sb.substring(0, sb.length() - 3) + "\n";
        ret += "totPathCost: " + totalCost + "\n";
        ret += "depth = " + depth + "\n";
        return ret;
    }

    /**
     * Compares the search nodes based on the total cost incurred.
     */
    public int compareTo(Object o) {
        if (this.totalCost == ((SearchNode) o).totalCost) {
            return 0;
        }
        else if (this.totalCost > ((SearchNode) o).totalCost) {
            return 1;
        }
        else {
            return -1;
        }
    }
}
