package ro.bolyai.fivedice.testdata;

import java.util.ArrayList;
import java.util.List;

import ro.bolyai.fivedice.model.PlayerScore;

public class TestData {

    //region 0. Constants
    /**
     * The number of test players.
     */
    private static final int MAX_PLAYERS = 50;
    //endregion

    //region 1. Constructors
    private TestData() {
        // Nothing to do.
    }
    //endregion

    //2. Test data

    /**
     * Returns a {@link List} of test {@link PlayerScore}s.
     *
     * @return testPlayerScores : {@link List} - {@link PlayerScore} : A list of test player scores.
     */
    public static synchronized List<PlayerScore> getTestPlayerScores() {
        List<PlayerScore> testPlayerScores = new ArrayList<>();

        for (int i = 0; i < MAX_PLAYERS; i++) {
            PlayerScore playerScore = new PlayerScore();
            playerScore.setName("Player " + String.valueOf(i));
            playerScore.setId(i);
            playerScore.setWinsPvP(i);
            playerScore.setWinsPvE(i);

            testPlayerScores.add(playerScore);
        }

        return testPlayerScores;
    }
    //endregion
}