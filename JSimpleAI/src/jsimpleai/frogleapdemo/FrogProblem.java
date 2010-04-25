/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jsimpleai.frogleapdemo;

import jsimpleai.problem.UnidirectionalProblem;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Ragha
 */
public class FrogProblem implements UnidirectionalProblem<FrogState>
{
    private static FrogState start;
    public FrogState getStartState()
    {
        if(start == null)
            start = new FrogState();
        return start;
    }

    public List<FrogState> getSuccessors(FrogState state)
    {
        List<FrogState> arrStates = new ArrayList<FrogState>();

        //handling green frogs..
        for(int i=0;i<7;i++)
        {
            if(state.frogPos[i] == FrogState.GREEN_FROG)
            {
                if( ((i+1) <= 6 && state.frogPos[i+1] == FrogState.BLANK) )
                {
                    FrogState newState = new FrogState(state);
                    newState.frogPos[i] = FrogState.BLANK;
                    newState.frogPos[i+1] = FrogState.GREEN_FROG;
                    arrStates.add(newState);
                }
                if( (i+2) <= 6 && state.frogPos[i+2] == FrogState.BLANK )
                {
                    FrogState newState = new FrogState(state);
                    newState.frogPos[i] = FrogState.BLANK;
                    newState.frogPos[i+2] = FrogState.GREEN_FROG;
                    arrStates.add(newState);
                }
            }            
        }

        //Handling brown frogs..
        for(int i=6;i>=0;i--)
        {
            if(state.frogPos[i] == FrogState.BROWN_FROG)
            {
                if( ((i-1) >= 0 && state.frogPos[i-1] == FrogState.BLANK) )
                {
                    FrogState newState = new FrogState(state);
                    newState.frogPos[i] = FrogState.BLANK;
                    newState.frogPos[i-1] = FrogState.BROWN_FROG;
                    arrStates.add(newState);
                }
                if( (i-2) >= 0 && state.frogPos[i-2] == FrogState.BLANK )
                {
                    FrogState newState = new FrogState(state);
                    newState.frogPos[i] = FrogState.BLANK;
                    newState.frogPos[i-2] = FrogState.BROWN_FROG;
                    arrStates.add(newState);
                }
            }
        }
        return arrStates;
    }

    public double getCost(FrogState oldState, FrogState newState)
    {
        return 1;
    }

    public boolean isGoalState(FrogState state)
    {
        if(state.frogPos[0] == FrogState.BROWN_FROG &&
                state.frogPos[1] == FrogState.BROWN_FROG &&
                state.frogPos[2] == FrogState.BROWN_FROG &&
                state.frogPos[3] == FrogState.BLANK &&
                state.frogPos[4] == FrogState.GREEN_FROG &&
                state.frogPos[5] == FrogState.GREEN_FROG &&
                state.frogPos[6] == FrogState.GREEN_FROG)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}
