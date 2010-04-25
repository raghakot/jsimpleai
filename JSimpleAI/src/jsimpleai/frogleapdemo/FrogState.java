/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jsimpleai.frogleapdemo;

import jsimpleai.problem.AbstractState;

/**
 *
 * @author Ragha
 */
public class FrogState implements AbstractState
{
    public static final int BROWN_FROG = 1;
    public static final int GREEN_FROG = -1;
    public static final int BLANK = 0;

    public int frogPos[] = new int[7];

    public FrogState()
    {
        frogPos = new int[]{GREEN_FROG, GREEN_FROG, GREEN_FROG, BLANK,
        BROWN_FROG, BROWN_FROG, BROWN_FROG};
    }

    public FrogState(FrogState state)
    {
        for(int i=0;i<7;i++)
            this.frogPos[i] = state.frogPos[i];
    }

    @Override
    public String toString()
    {
        String ret = "";
        for(int i=0;i<7;i++)
        {
            if(frogPos[i] == GREEN_FROG)
                ret += "GREEN_FROG, ";
            else if(frogPos[i] == BROWN_FROG)
                ret += "BROWN_FROG, ";
            else
                ret += "BLANK, ";
        }

        //removing orphan ", "
        return ret.substring(0, ret.length() - 2);
    }
}
