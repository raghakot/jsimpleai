package jsimpleai.demo.frogleap;

import jsimpleai.api.search.FrontierFactory;
import jsimpleai.api.search.SearchNode;
import jsimpleai.api.search.TreeSearch;

import java.util.Collection;
import java.util.concurrent.TimeUnit;

/**
 * Runs the frog leap demo
 */
public class FrogLeapDemo {

    public static void main(String[] args) {

        final FrogProblem problem = new FrogProblem();

        final TreeSearch<GameState> search = new TreeSearch<>((goalNode) -> {
            // Print info about goal state and the transition trace to get there.
            final Collection<SearchNode<GameState>> trace = goalNode.getTraceFromRoot();
            System.out.println("Tot Moves: " + trace.size());
            for (final SearchNode node : trace) {
                System.out.println(node.getState());
            }
            System.out.println("---------------------------------------");
        });

        long start = System.nanoTime();
        search.search(problem, FrontierFactory.createUniformCostSearchFrontier(), 10, Long.MAX_VALUE);
        System.out.println("Search completed in " + TimeUnit.NANOSECONDS.toSeconds(System.nanoTime() - start)
                + " secs");
    }
}
