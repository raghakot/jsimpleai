package jsimpleai.api.search;

import javax.annotation.Nonnull;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Stack;

/**
 * Factory for creating various types of {@link Frontier} instances.
 */
public final class FrontierFactory {
    private FrontierFactory() {
    }

    /**
     * Creates a frontier that explores search tree using uniform cost search
     */
    @Nonnull
    public static <T> Frontier<T> createUniformCostSearchFrontier() {
        return new Frontier<>(new PriorityQueue<>());
    }

    /**
     * Creates a frontier that explores search tree using breadth first search
     */
    @Nonnull
    public static <T> Frontier<T> createBFSFrontier() {
        return new Frontier<>(new LinkedList<>());
    }

    /**
     * Creates a frontier that explores search tree using depth first search
     */
    public static <T> Frontier<T> createDFSFrontier() {
        return new Frontier<>(new Stack<>());
    }

    /**
     * Creates a frontier that explores search tree using A* search using the provided heuristic
     */
    public static <T> Frontier<T> createAStarFrontier(@Nonnull final Heuristic<T> heuristic) {
        // Sorts by distance, which is total of pathCost + heuristicCost
        final Comparator<SearchNode<T>> comparator = (o1, o2) -> {
            double dist1 = o1.getTotalCost() + heuristic.getHeuristicCostEstimate(o1);
            double dist2 = o2.getTotalCost() + heuristic.getHeuristicCostEstimate(o2);
            if (dist1 == dist2)
                return 0;
            else if (dist1 > dist2)
                return 1;
            else
                return -1;
        };

        // 100 is an arbitrary initial capacity.
        // There isn't a constructor that directly takes a comparator.
        return new Frontier<>(new PriorityQueue<>(100, comparator));
    }
}
