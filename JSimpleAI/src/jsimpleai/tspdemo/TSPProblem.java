/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jsimpleai.tspdemo;

import jsimpleai.problem.BidirectionalProblem;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents TSP.
 * @author Ragha
 */
public class TSPProblem implements BidirectionalProblem<TSPState>
{
    private CostMatrix cm;

    @Override
    public List<TSPState> getSuccessors(TSPState state)
    {
        ArrayList<TSPState> arrStates = new ArrayList<TSPState>();

        //Get the start city..
        int startCity = getStartState().city;

        //If only one transition is left, then, go back to start city..
        if(state.getNumVisitedCities() == cm.getLength())
        {
            TSPState newState = new TSPState();
            newState.city = startCity;
            newState.arrVisitedCities = new ArrayList<Integer>(state.arrVisitedCities);
            newState.arrVisitedCities.set(startCity, 2);
            arrStates.add(newState);
        }
        else
        {
            /**
             * Generate all possible states it can goto, obvisouly not the start
             * city and the cities it has already visited..
             */
            ArrayList<Integer> possibleCities = new ArrayList<Integer>();
            for(int i=0; i<cm.getLength(); i++)
            {
                if(i != startCity && state.arrVisitedCities.get(i)== 0)
                {
                    possibleCities.add(i);
                }
            }

            //Transition to possible cities..
            for(int newCity : possibleCities)
            {
                TSPState newState = new TSPState();
                newState.city = newCity;
                newState.arrVisitedCities = new ArrayList<Integer>(state.arrVisitedCities);
                newState.arrVisitedCities.set(newCity, 1);
                arrStates.add(newState);
            }
        }

        return arrStates;
    }

    @Override
    public double getCost(TSPState oldState, TSPState newState) {
        return cm.getCost(oldState.city, newState.city);
    }

    private static TSPState startState;
    @Override
    public TSPState getStartState()
    {
        if(startState == null)        
            startState = new TSPState(0);
        return startState;
    }

    public void loadCostMatrix(File f) throws Exception
    {
        cm = CostMatrix.getCostMatrix(f);
    }

    public CostMatrix getCostMatrix()
    {
        return cm;
    }

    public boolean isGoalState(TSPState state)
    {
        return state.getNumVisitedCities() == cm.getLength() + 1;
    }

    TSPState goal;
    public TSPState getGoalState()
    {
        if(goal == null)
        {
            goal = new TSPState(0);            
//            for(int i=0; i<cm.getLength();i++)
//                goal.arrVisitedCities.set(i, 1);
//            goal.arrVisitedCities.set(getStartState().city, 2);
        }
        return goal;
    }

    @Override
    public boolean isIntersecting(TSPState frontState, TSPState backState) {
        if(frontState.city == backState.city)
        {
            /**
             * Finding the visited cities after merging both states..
             */
            ArrayList<Integer> merged = new ArrayList<Integer>();
            for(int i=0; i<cm.getLength();i++)
            {
                merged.add(frontState.arrVisitedCities.get(i) 
                        + backState.arrVisitedCities.get(i));
            }
            //At the point of merger, the city visited is added twice.
            //removing that
            merged.set(frontState.city, merged.get(frontState.city) - 1);

            /**
             * Check if this merger forms a tour..
             */
            boolean isValidState = true;
            if(merged.get(startState.city) != 2)
                isValidState = false;
            for(int i=0; i<cm.getLength();i++)
            {
                if(i != startState.city)
                {
                    if(merged.get(i) != 1)
                        isValidState = false;                
                }
            }

            return isValidState;
        }
        else
            return false;
    }

    /**
     * This can be done as tsp is a symmetric problem.
     * @param state The state to be expanded
     * @return A list of possible predecessor states.
     */
    public List<TSPState> getPredecessors(TSPState state) {
        return getSuccessors(state);
    }    
}