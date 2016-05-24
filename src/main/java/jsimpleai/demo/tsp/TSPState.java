package jsimpleai.demo.tsp;

import java.util.Arrays;

/**
 * Represents a state for TSP. It consists of
 * <ul>
 * <li>The current city</li>
 * <li>A list of previously travelled cities.</li>
 * </ul>
 */
public class TSPState {
    /**
     * The current city..
     */
    private final int city;

    /**
     * Keeps track of visited cities.
     * The integer at index i indicates the no. of times ith city is visited.
     */
    private final int[] visitedCities;

    /**
     * Creates a new TSPState
     */
    public TSPState(final int city, final int numCities) {
        visitedCities = new int[numCities];
        this.city = city;
        visitedCities[city] = 1;
    }

    public void setVisitedCities(final int[] visitedCities) {
        for (int i = 0; i < visitedCities.length; i++) {
            this.visitedCities[i] = visitedCities[i];
        }
    }

    public int[] getVisitedCities() {
        return visitedCities;
    }

    public int getNumVisitedCities() {
        return Arrays.stream(visitedCities).sum();
    }

    public int getCurrentCity() {
        return city;
    }

    @Override
    public String toString() {
        return "city = " + city;
    }
}
