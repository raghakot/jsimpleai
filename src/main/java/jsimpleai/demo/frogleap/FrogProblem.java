package jsimpleai.demo.frogleap;

import jsimpleai.api.problem.UnidirectionalProblem;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Implements the frog leap problem.
 */
public class FrogProblem implements UnidirectionalProblem<GameState> {
    @Override
    @Nonnull
    public GameState getStartState() {
        return new GameState();
    }

    @Override
    @Nonnull
    public Collection<GameState> getSuccessors(@Nonnull final GameState state) {
        Collection<GameState> successors = new ArrayList<>();

        // Handling green frogs..
        for (int i = 0; i < 7; i++) {
            if (state.getFrogPos()[i] == GameState.GREEN_FROG) {
                if (((i + 1) <= 6 && state.getFrogPos()[i + 1] == GameState.BLANK)) {
                    GameState newState = new GameState(state);
                    newState.getFrogPos()[i] = GameState.BLANK;
                    newState.getFrogPos()[i + 1] = GameState.GREEN_FROG;
                    successors.add(newState);
                }
                if ((i + 2) <= 6 && state.getFrogPos()[i + 2] == GameState.BLANK) {
                    GameState newState = new GameState(state);
                    newState.getFrogPos()[i] = GameState.BLANK;
                    newState.getFrogPos()[i + 2] = GameState.GREEN_FROG;
                    successors.add(newState);
                }
            }
        }

        // Handling brown frogs..
        for (int i = 6; i >= 0; i--) {
            if (state.getFrogPos()[i] == GameState.BROWN_FROG) {
                if (((i - 1) >= 0 && state.getFrogPos()[i - 1] == GameState.BLANK)) {
                    GameState newState = new GameState(state);
                    newState.getFrogPos()[i] = GameState.BLANK;
                    newState.getFrogPos()[i - 1] = GameState.BROWN_FROG;
                    successors.add(newState);
                }
                if ((i - 2) >= 0 && state.getFrogPos()[i - 2] == GameState.BLANK) {
                    GameState newState = new GameState(state);
                    newState.getFrogPos()[i] = GameState.BLANK;
                    newState.getFrogPos()[i - 2] = GameState.BROWN_FROG;
                    successors.add(newState);
                }
            }
        }
        return successors;
    }

    @Override
    public double getCost(@Nonnull final GameState oldState,
                          @Nonnull final GameState newState) {
        return 1;
    }

    @Override
    public boolean isGoalState(GameState state) {
        return state.getFrogPos()[0] == GameState.BROWN_FROG &&
               state.getFrogPos()[1] == GameState.BROWN_FROG &&
               state.getFrogPos()[2] == GameState.BROWN_FROG &&
               state.getFrogPos()[3] == GameState.BLANK &&
               state.getFrogPos()[4] == GameState.GREEN_FROG &&
               state.getFrogPos()[5] == GameState.GREEN_FROG &&
               state.getFrogPos()[6] == GameState.GREEN_FROG;
    }
}
