package jsimpleai.demo.tsp;

import jsimpleai.api.search.FrontierFactory;
import jsimpleai.api.search.SearchNode;
import jsimpleai.api.search.TreeSearch;

import java.io.IOException;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

/**
 * Demo Traveling salesman solver that solvbes for all possible tours.
 */
public class TSPDemo {
    /**
     * Generates all possible goal states (need not be optimal)
     * @param args Pass the file name as the parameter
     */
    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            System.out.println("Problem filename required");
            System.exit(0);
        }

        final CostMatrix costMatrix = CostMatrix.getCostMatrix(args[0]);
        final TSPProblem problem = new TSPProblem(costMatrix);

        final TreeSearch<TSPState> search = new TreeSearch<>(goalState -> {
            // Print info about goal state and the transition trace to get there.
            final Collection<SearchNode<TSPState>> trace = goalState.getTraceFromRoot();
            System.out.println("Tot Moves: " + trace.size());
            for (final SearchNode node : trace) {
                System.out.println(node.getState());
            }
            System.out.println("---------------------------------------");
        });

        final long start = System.nanoTime();
        search.search(problem, FrontierFactory.createAStarFrontier(new MSTHeuristic(costMatrix.getCostMatrix())),
                1, Long.MAX_VALUE);
        System.out.println("Search completed in " + TimeUnit.NANOSECONDS.toSeconds(System.nanoTime() - start)
                + " secs");
    }
}
