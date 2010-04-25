/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jsimpleai.tspdemo;

import java.util.ArrayList;
import jsimpleai.problem.AbstractState;

/**
 * Represents a state for TSP. It consists of
 * <ul>
 * <li>The current city</li>
 * <li>A list of previously travelled cities.</li>
 * </ul>
 * 
 * @author Ragha
 */
public class TSPState implements AbstractState
{
    /**
     * The current city..
     */
    int city;
    /**
     * Keeps track of visited cities.
     * The integer at index i indicates the no. of times ith city is visited.
     */
    ArrayList<Integer> arrVisitedCities = new ArrayList<Integer>();

    public TSPState()
    {
        for(int i=0;i<Main.numCities;i++)
            arrVisitedCities.add(0);
    }

    public TSPState(int city)
    {
        this();
        this.city = city;
        arrVisitedCities.set(city, 1);
    }

    public int getNumVisitedCities()
    {
        int visitedCities = 0;
        for(int i : arrVisitedCities)
            visitedCities += i;
        return visitedCities;
    }

    public int getCurrentCity()
    {
        return city;
    }

    @Override
    public String toString()
    {
        StringBuffer sb = new StringBuffer();
        sb.append("city = " + city);
        return sb.toString();
    }
}
