package jsimpleai.api.search;

import com.google.common.base.Preconditions;

import javax.annotation.Nonnull;
import java.util.*;

/**
 * Expands the search tree using the provided collection implementation.
 */
class Frontier<T> {

    private final Collection<SearchNode<T>> collection;

    /**
     * Initializes the frontier with the provided collection implementation. The collection must be a
     * {@link Queue} or {@link List}
     *
     * @param collection The collection data structure to use as a frontier.
     */
    Frontier(@Nonnull final Collection<SearchNode<T>> collection) {
        Preconditions.checkArgument(collection instanceof List || collection instanceof Queue,
                "Collection must be a list or queue");
        this.collection = Preconditions.checkNotNull(collection);
    }

    /**
     * Adds a collection of {@link SearchNode} to the frontier.
     */
    void addSearchNodes(@Nonnull final Collection<SearchNode<T>> nodes) {
        Preconditions.checkNotNull(nodes);
        collection.addAll(nodes);
    }

    /**
     * Adds the given {@link SearchNode} to the frontier.
     */
    void addSearchNode(@Nonnull final SearchNode<T> node) {
        Preconditions.checkNotNull(node);
        addSearchNodes(Collections.singleton(node));
    }

    /**
     * Checks if the frontier is empty.
     */
    boolean isEmpty() {
        return collection.isEmpty();
    }

    /**
     * Retrieves and deletes the next search node to be expanded from the frontier.
     *
     * @return A search node from the frontier.
     */
    SearchNode<T> nextSearchNode() {
        if (collection instanceof Queue) {
            return ((Queue<SearchNode<T>>) collection).remove();
        } else {
            return ((List<SearchNode<T>>) collection).remove(0);
        }
    }

    /**
     * Retrieves all the nodes from the frontier without removing it.
     *
     * @return A list of all the available nodes..
     */
    Iterator<SearchNode<T>> getAllNodesInFrontier() {
        return collection.iterator();
    }
}
