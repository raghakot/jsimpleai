package jsimpleai.demo.frogleap;

/**
 * Represents the game state
 */
public class GameState {
    public static final int BROWN_FROG = 1;
    public static final int GREEN_FROG = -1;
    public static final int BLANK = 0;

    private int frogPos[] = new int[7];

    /**
     * Creates the initial game state
     */
    public GameState() {
        frogPos = new int[]{GREEN_FROG, GREEN_FROG, GREEN_FROG, BLANK,
                BROWN_FROG, BROWN_FROG, BROWN_FROG};
    }

    /**
     * Creates a game state form the given state
     */
    public GameState(final GameState state) {
        for (int i = 0; i < 7; i++) {
            this.frogPos[i] = state.frogPos[i];
        }
    }

    public int[] getFrogPos() {
        return frogPos;
    }

    @Override
    public String toString() {
        String ret = "";
        for (int i = 0; i < 7; i++) {
            if (frogPos[i] == GREEN_FROG)
                ret += "GREEN_FROG, ";
            else if (frogPos[i] == BROWN_FROG)
                ret += "BROWN_FROG, ";
            else
                ret += "BLANK, ";
        }

        //removing orphan ", "
        return ret.substring(0, ret.length() - 2);
    }
}
