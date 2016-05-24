package jsimpleai.demo.tsp;

import com.google.common.base.Preconditions;
import jsimpleai.api.problem.UnidirectionalProblem;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Represents a TSP problem
 */
public class TSPProblem implements UnidirectionalProblem<TSPState> {
    private CostMatrix costMatrix;

    public TSPProblem(@Nonnull final CostMatrix costMatrix) {
        this.costMatrix = Preconditions.checkNotNull(costMatrix);
    }

    @Override
    @Nonnull
    public TSPState getStartState() {
        return new TSPState(0, costMatrix.getLength());
    }

    @Override
    @Nonnull
    public Collection<TSPState> getSuccessors(@Nonnull final TSPState state) {
        final Collection<TSPState> successors = new ArrayList<>();

        // Get the start city..
        final int startCity = getStartState().getCurrentCity();

        // If only one transition is left, then, go back to start city..
        if (state.getNumVisitedCities() == costMatrix.getLength()) {
            final int[] visited = state.getVisitedCities().clone();
            visited[startCity] = 2;

            final TSPState newState = new TSPState(startCity, costMatrix.getLength());
            newState.setVisitedCities(visited);
            successors.add(newState);
        } else {
            // Generate all possible states it can goto, obviously not the start
            // city and the cities it has already visited..
            final List<Integer> possibleCities = new ArrayList<>();
            for (int i = 0; i < costMatrix.getLength(); i++) {
                if (i != startCity && state.getVisitedCities()[i] == 0) {
                    possibleCities.add(i);
                }
            }

            // Transition to possible cities..
            for (int newCity : possibleCities) {
                final TSPState newState = new TSPState(newCity, costMatrix.getLength());
                newState.setVisitedCities(state.getVisitedCities().clone());
                successors.add(newState);
            }
        }

        return successors;
    }

    @Override
    public double getCost(@Nonnull final TSPState oldState,
                          @Nonnull final TSPState newState) {
        return costMatrix.getCost(oldState.getCurrentCity(), newState.getCurrentCity());
    }

    @Override
    public boolean isGoalState(final TSPState state) {
        return state.getNumVisitedCities() == costMatrix.getLength() + 1;
    }
}