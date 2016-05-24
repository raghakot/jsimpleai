package jsimpleai.api.search;

import com.google.common.base.Preconditions;
import jsimpleai.api.problem.BidirectionalProblem;
import jsimpleai.api.problem.UnidirectionalProblem;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collection;
import java.util.function.Consumer;

/**
 * A generic search class that can be used to find a path from start state to goal state.
 */
public class TreeSearch<T> {
    private final Consumer<SearchNode<T>> goalStateConsumer;

    /**
     * Creates an instance of @{link TreeSearch} which notifies a found goal using the consumer.
     */
    public TreeSearch(@Nonnull final Consumer<SearchNode<T>> goalStateConsumer) {
        this.goalStateConsumer = Preconditions.checkNotNull(goalStateConsumer);
    }

    /**
     * Initiates a unidirectional search for all the goal nodes, defined by
     * {@link UnidirectionalProblem} in the search space.
     * <p>
     * Stopping conditions are determined by the numGoals and
     * depthLimit parameters.
     *
     * @param problem    The unidirectional search problem.
     * @param frontier   The frontier to be used.
     * @param numGoals   number of goals to be searched before stopping.
     *                   If the given frontier is known to yield optimal goal, one could use
     *                   numGoals = 1
     * @param depthLimit The max depth to be searched.
     */
    public void search(@Nonnull final UnidirectionalProblem<T> problem,
                       @Nonnull final Frontier<T> frontier,
                       @Nonnegative final long numGoals,
                       @Nonnegative final long depthLimit) {
        Preconditions.checkNotNull(problem);
        Preconditions.checkNotNull(frontier);
        Preconditions.checkArgument(numGoals >= 1, "numGoals must be at least 1");
        Preconditions.checkArgument(depthLimit > 0, "depthLimit must be positive");

        searchWithNotify(problem, frontier, numGoals, depthLimit, null);
    }

    /**
     * Performs one step of search and notifies the state of the consumer via consumer
     */
    private void searchWithNotify(final UnidirectionalProblem<T> problem,
                                  final Frontier<T> frontier,
                                  final long numGoals,
                                  final long depthLimit,
                                  @Nullable final Consumer<Frontier<T>> stepNotifier) {
        final SearchNode<T> root = new SearchNode<>(null, problem.getStartState(), 0, 0);
        frontier.addSearchNode(root);

        long goalCount = 0;
        while (!frontier.isEmpty()) {
            final SearchNode<T> node = frontier.nextSearchNode();
            if (problem.isGoalState(node.getState())) {
                goalStateConsumer.accept(node);
                goalCount++;
                if (goalCount == numGoals) {
                    break;
                }
            }

            if (node.getDepth() <= depthLimit) {
                frontier.addSearchNodes(node.getSuccessorSearchNodes(problem));
            }

            if (stepNotifier != null) {
                stepNotifier.accept(frontier);
            }
        }
    }

    /**
     * Checks if there are any paths from one frontier to another.
     */
    private boolean isIntersecting(final Frontier<T> front, final Frontier<T> back) {
        // Implement intersection check here. Notify on goalStateConsumer.
        throw new UnsupportedOperationException("NOt yet implemented. IKts on my todo");
    }

    /**
     * Initiates a bidirectional search for all the goal nodes, defined by
     * {@link BidirectionalProblem} in the search space.
     * <p>
     * Stopping conditions are determined by the numGoals and
     * depthLimit parameters.
     *
     * @param problem    The Bidirectional search problem.
     * @param front      The frontier to be used for the forward search.
     * @param back       The frontier to be used for the backward search.
     * @param numGoals   number of goals to be searched before stopping.
     *                   If the given frontier is known to yield optimal goal, one could use
     *                   numGoals = 1
     * @param depthLimit The max depth to be searched.
     */
    public void search(@Nonnull final BidirectionalProblem<T> problem,
                       @Nonnull final Frontier<T> front,
                       @Nonnull final Frontier<T> back,
                       @Nonnegative long numGoals,
                       @Nonnegative long depthLimit) {
        Preconditions.checkNotNull(problem);
        Preconditions.checkNotNull(front);
        Preconditions.checkNotNull(back);
        Preconditions.checkArgument(numGoals >= 1, "numGoals must be at least 1");
        Preconditions.checkArgument(depthLimit > 0, "depthLimit must be positive");

        // Posing goal -> start search as a unidirectional problem.
        final UnidirectionalProblem<T> goalToStartProblem = new UnidirectionalProblem<T>() {
            @Nonnull
            @Override
            public T getStartState() {
                return problem.getGoalState();
            }

            @Nonnull
            @Override
            public Collection<T> getSuccessors(@Nonnull final T state) {
                return problem.getPredecessors(state);
            }

            @Override
            public double getCost(@Nonnull final T oldState, @Nonnull final T newState) {
                return problem.getCost(oldState, newState);
            }

            @Override
            public boolean isGoalState(@Nonnull final T state) {
                return state.equals(problem.getStartState());
            }
        };

        // Search forwards and backwards with unidirectional search and check for merges.
        final Frontier[] newFronts = new Frontier[2];
        searchWithNotify(problem, front, numGoals, depthLimit, (updatedFront) -> {
            newFronts[0] = updatedFront;
            isIntersecting(newFronts[0], newFronts[1]);
        });
        searchWithNotify(goalToStartProblem, back, numGoals, depthLimit, (updatedBack) -> {
            newFronts[0] = updatedBack;
            isIntersecting(newFronts[0], newFronts[1]);
        });
    }
}
