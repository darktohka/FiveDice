package ro.bolyai.fivedice.gui.activity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ro.bolyai.fivedice.R;
import ro.bolyai.fivedice.gui.model.GUIDice;
import ro.bolyai.fivedice.gui.model.GUIPlayer;
import ro.bolyai.fivedice.logic.database.DbManager;
import ro.bolyai.fivedice.logic.listener.GameActivityListener;
import ro.bolyai.fivedice.model.Player;
import ro.bolyai.fivedice.model.PlayerScore;

public class GameActivity extends AppCompatActivity {

    //region 0. Constants
    /**
     * The default target score.
     * This is the target score if none is set.
     */
    private static final int DEFAULT_TARGET_SCORE = 300;

    /**
     * The identifier for the first player.
     */
    private static final int PLAYER_ONE = 0;

    /**
     * The identifier for the second player.
     */
    private static final int PLAYER_TWO = 1;

    /**
     * The re-roll count possible during a turn.
     */
    private static final int REROLL_COUNT = 3;

    /**
     * The score value of a hidden dice.
     */
    private static final int HIDDEN_DIE = 0;

    /**
     * A {@link String} containing a dice key for each dice.
     */
    private static final String[] diceKeys = new String[]{"diceOne", "diceTwo", "diceThree", "diceFour", "diceFive"};
    //endregion

    //region 1. Widget declarations
    /**
     * The {@link TextView} responsible for
     * showing the number of the current round.
     */
    private TextView txtRound;

    /**
     * The {@link TextView} responsible for
     * showing the name of the current turn's player.
     */
    private TextView txtRoundWhich;

    /**
     * The {@link TextView} responsible for
     * showing the number of re-rolls left.
     */
    private TextView txtRerolls;

    /**
     * The {@link Button} responsible for
     * rolling the dice.
     */
    private Button btnRoll;

    /**
     * The {@link GameActivityListener} responsible for
     * handling the dice roll button.
     */
    private GameActivityListener gameActivityListener;
    //endregion

    //region 2. Game state declarations
    /**
     * The first player.
     * This player has the first turn in a match.
     */
    private Player playerOne;

    /**
     * The second player.
     * This player has the second turn in a match.
     */
    private Player playerTwo;

    /**
     * The target score of the current game.
     * When this score is reached, the winner
     * is determined by the game logic.
     */
    private int targetScore;

    /**
     * A list holding all the dice of the game.
     */
    private List<GUIDice> dice;

    /**
     * The current round number.
     * This starts from 1, and is incremented
     * every time a new round starts.
     */
    private int iCurrentRound;

    /**
     * This variable determines whose turn it is.
     * Can be either {@link GameActivity#PLAYER_ONE} or {@link GameActivity#PLAYER_TWO}.
     */
    private int iCurrentPlayer = -1;

    /**
     * The number of re-rolls left.
     * If this number is 0, there are no re-rolls left.
     */
    private int iRerollsLeft;

    /**
     * This variable is set to true during intermissions,
     * when the re-roll count is 0.
     */
    private boolean showingDice;

    /**
     * This variable is set to true during the
     * dice rolling animation.
     */
    private boolean rollingDice;

    /**
     * This variable is set to true if this
     * match is a Player-vs-AI match.
     */
    private boolean computer;

    /**
     * The {@link Handler} responsible for handling
     * our timer tasks in an orderly fashion.
     */
    private Handler timerHandler;

    /**
     * A list containing the first player's dice scores.
     */
    private List<Integer> firstDice;

    /**
     * A map containing all of our game sounds.
     */
    private Map<String, MediaPlayer> sounds = new HashMap<String, MediaPlayer>();
    //endregion

    //region 2. Lifecycle

    /**
     * This is ran when the activity is first created.
     * We will initialize our game here.
     *
     * @param savedInstanceState : {@link Bundle} : The saved state of the game.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 1. Set content view
        setContentView(R.layout.game_activity_layout);

        // 2. Initialize widgets and timer
        txtRound = findViewById(R.id.txtRound);
        txtRoundWhich = findViewById(R.id.txtRoundWhich);
        txtRerolls = findViewById(R.id.txtRerolls);
        btnRoll = findViewById(R.id.btnRoll);

        timerHandler = new Handler();

        sounds.clear();
        sounds.put("rollDiceOne", MediaPlayer.create(this, R.raw.rolldice_one));

        // 3. Initialize game
        if (savedInstanceState == null) {
            initializeGame();
        }

        // 4. Initialize listener
        gameActivityListener = new GameActivityListener(this);
        btnRoll.setOnClickListener(gameActivityListener);
    }

    /**
     * This is ran when the activity is destroyed.
     *
     * We need to release our MediaPlayer sounds!
     */
    @Override
    public void onDestroy() {
        super.onDestroy();

        for (MediaPlayer sound : sounds.values()) {
            sound.stop();
            sound.release();
        }
    }

    /**
     * This is ran when the activity is paused.
     */
    @Override
    public void onPause() {
        super.onPause();

        // Remove all timer tasks.
        timerHandler.removeCallbacksAndMessages(null);
    }
    //endregion

    //region 3. Initialization

    /**
     * Initializes all players and all dice
     * using the Intent that created this activity.
     */
    private void initializeGame() {
        Intent intent = getIntent();

        // Set default values
        String playerOneName = getString(R.string.default_player_one_name);
        String playerTwoName = getString(R.string.default_player_two_name);
        iCurrentPlayer = -1;
        targetScore = DEFAULT_TARGET_SCORE;

        // Read the new values from the Intent
        computer = intent.getBooleanExtra("computer", false);

        if (intent.hasExtra("playerOneName")) {
            playerOneName = intent.getStringExtra("playerOneName");
        }

        if (intent.hasExtra("playerTwoName")) {
            playerTwoName = intent.getStringExtra("playerTwoName");
        }

        if (intent.hasExtra("targetScore")) {
            targetScore = intent.getIntExtra("targetScore", DEFAULT_TARGET_SCORE);
        }

        // Create our players
        playerOne = new GUIPlayer(
                (TextView) findViewById(R.id.txtPlayerOneName),
                (TextView) findViewById(R.id.txtPlayerOneScore),
                (ProgressBar) findViewById(R.id.barPlayerOne),
                playerOneName, 0);
        playerTwo = new GUIPlayer(
                (TextView) findViewById(R.id.txtPlayerTwoName),
                (TextView) findViewById(R.id.txtPlayerTwoScore),
                (ProgressBar) findViewById(R.id.barPlayerTwo),
                playerTwoName, 0);
        playerOne.initialize(targetScore);
        playerTwo.initialize(targetScore);

        // Create our dice
        dice = new ArrayList<>();
        dice.add(new GUIDice((ImageView) findViewById(R.id.imgDice1), (Button) findViewById(R.id.btnKeep1)));
        dice.add(new GUIDice((ImageView) findViewById(R.id.imgDice2), (Button) findViewById(R.id.btnKeep2)));
        dice.add(new GUIDice((ImageView) findViewById(R.id.imgDice3), (Button) findViewById(R.id.btnKeep3)));
        dice.add(new GUIDice((ImageView) findViewById(R.id.imgDice4), (Button) findViewById(R.id.btnKeep4)));
        dice.add(new GUIDice((ImageView) findViewById(R.id.imgDice5), (Button) findViewById(R.id.btnKeep5)));

        // Initialize all dice
        for (GUIDice die : dice) {
            die.initialize();
        }

        // Initialize the game state now!
        setCurrentRound(1);
        setCurrentPlayer(PLAYER_ONE);
        hideDiceKeep();

        showingDice = false;
        rollingDice = false;
        firstDice = new ArrayList<>();
    }
    //endregion

    //region 4. Getters and setters

    /**
     * Returns the {@link TextView} responsible
     * for showing the number of the current round.
     *
     * @return txtRound : {@link TextView} : The round text.
     */
    public TextView getTxtRound() {
        return txtRound;
    }

    /**
     * Returns the {@link TextView} responsible
     * for showing the name of the current turn's player.
     *
     * @return txtRoundWhich : {@link TextView} : The turn player name text.
     */
    public TextView getTxtRoundWhich() {
        return txtRoundWhich;
    }

    /**
     * Returns the {@link TextView} responsible
     * for showing the number of re-rolls left.
     *
     * @return txtRerolls : {@link TextView} : The re-roll text.
     */
    public TextView getTxtRerolls() {
        return txtRerolls;
    }

    /**
     * Returns the {@link Button} responsible
     * for rolling the dice.
     *
     * @return btnRoll : {@link Button} : The roll button.
     */
    public Button getBtnRoll() {
        return btnRoll;
    }

    /**
     * Returns the first player.
     *
     * @return playerOne : {@link Player} : The first player.
     */
    public Player getPlayerOne() {
        return playerOne;
    }

    /**
     * Returns the second player.
     *
     * @return playerTwo : {@link Player} : The second player.
     */
    public Player getPlayerTwo() {
        return playerTwo;
    }

    /**
     * Returns the target score of the match.
     *
     * @return targetScore : int : The target score of the match.
     */
    public int getTargetScore() {
        return targetScore;
    }

    /**
     * Returns a list which contains all of the dice.
     *
     * @return dice : {@link List} - {@link GUIDice} : The list containing all dice.
     */
    public List<GUIDice> getDice() {
        return dice;
    }

    /**
     * Returns the number of the current round.
     *
     * @return iCurrentRound : int : The current round.
     */
    public int getCurrentRound() {
        return iCurrentRound;
    }

    /**
     * Sets the current round.
     * This also updates the GUI automatically.
     *
     * @param currentRound : int : The new round number.
     */
    public void setCurrentRound(int currentRound) {
        this.iCurrentRound = currentRound;

        if (txtRound != null) {
            txtRound.setText(String.format(getString(R.string.round_text), currentRound));
        }
    }

    /**
     * Increases the current round number by one.
     * This also updates the GUI automatically.
     */
    public void incrementRound() {
        this.setCurrentRound(iCurrentRound + 1);
    }

    /**
     * Returns which player's turn it is in the current match.
     * Will return either {@link GameActivity#PLAYER_ONE} or {@link GameActivity#PLAYER_TWO}.
     *
     * @return iCurrentPlayer : int : Either {@link GameActivity#PLAYER_ONE} or {@link GameActivity#PLAYER_TWO}.
     */
    public int getCurrentPlayer() {
        return iCurrentPlayer;
    }

    /**
     * Returns the current turn's player.
     *
     * @return player : {@link Player} : The current turn's player.
     */
    public Player getPlayer() {
        if (iCurrentPlayer == PLAYER_ONE) {
            return playerOne;
        } else {
            return playerTwo;
        }
    }

    /**
     * Sets the current turn's player.
     * Should be either {@link GameActivity#PLAYER_ONE} or {@link GameActivity#PLAYER_TWO}.
     * This also updates the GUI automatically.
     *
     * @param currentPlayer : int : Either {@link GameActivity#PLAYER_ONE} or {@link GameActivity#PLAYER_TWO}.
     */
    public void setCurrentPlayer(int currentPlayer) {
        // If the current player is already OK,
        // we don't need to change anything.
        if (this.iCurrentPlayer == currentPlayer) {
            return;
        }

        this.iCurrentPlayer = currentPlayer;

        // Update the turn name text.
        if (txtRoundWhich != null) {
            txtRoundWhich.setText(String.format(getString(R.string.turn_text), getPlayer().getName()));
        }

        // Update both player's name labels,
        // indicating their active status.
        if (currentPlayer == PLAYER_ONE) {
            playerOne.setActivePlayer();
            playerTwo.setInactivePlayer();
        } else {
            playerOne.setInactivePlayer();
            playerTwo.setActivePlayer();
        }

        // Reset the re-roll count.
        setRerollsLeft(REROLL_COUNT);
    }

    /**
     * Returns the number of re-rolls left in the turn.
     *
     * @return iRerollsLeft : int : The number of re-rolls left.
     */
    public int getRerollsLeft() {
        return iRerollsLeft;
    }

    /**
     * Sets the number of re-rolls left in the turn.
     * This also updates the GUI automatically.
     *
     * @param iRerollsLeft : int : The new number of re-rolls left.
     */
    public void setRerollsLeft(int iRerollsLeft) {
        this.iRerollsLeft = iRerollsLeft;

        // Update the re-roll count in the GUI.
        if (txtRerolls != null) {
            txtRerolls.setText(String.format(getString(R.string.rerolls_left_text), iRerollsLeft));
        }
    }

    /**
     * Decreases the number of re-rolls by one.
     * This is ran at the end of each roll.
     */
    public void decrementRerolls() {
        if (iRerollsLeft > 0) {
            this.setRerollsLeft(iRerollsLeft - 1);
        }
    }

    /**
     * Checks whether the current player has any
     * re-rolls left or not.
     *
     * @return iRerollsLeft : boolean : Returns true if the player has re-rolls left.
     */
    public boolean hasRerollsLeft() {
        return this.iRerollsLeft > 0;
    }

    /**
     * Checks whether the game is currently
     * in the intermission stage.
     *
     * @return showingDice : boolean : Returns true if the game is in intermission.
     */
    public boolean isShowingDice() {
        return showingDice;
    }

    /**
     * Sets the game's intermission state.
     * Also updates the GUI automatically.
     *
     * @param showingDice : boolean : True if the game is in intermission, false if not.
     */
    public void setShowingDice(boolean showingDice) {
        this.showingDice = showingDice;

        // Update the GUI accordingly.
        if (showingDice) {
            hideDiceKeep();
            btnRoll.setText(getString(R.string.btn_roll_next));
        } else {
            showDiceKeep();
            btnRoll.setText(getString(R.string.btn_roll));
        }
    }

    /**
     * Checks whether the game is
     * currently rolling dice randomly.
     *
     * @return rollingDice : boolean : Returns true if the game is currently rolling dice.
     */
    public boolean isRollingDice() {
        return rollingDice;
    }

    /**
     * Checks whether the second player
     * is a computer (AI) player or not.
     *
     * @return computer : boolean : Returns true if the second player is a computer.
     */
    public boolean isComputer() {
        return computer;
    }

    /**
     * Sets the second player's computer state.
     * If set to true, the second player's rolls are
     * automatically completed by the AI game logic.
     *
     * @param computer : boolean : True if the second player should be a computer.
     */
    public void setComputer(boolean computer) {
        this.computer = computer;
    }

    /**
     * Checks whether it's the computer's turn.
     *
     * @return aiTurn : boolean : True if the computer should roll dice automatically.
     */
    public boolean isAITurn() {
        return computer && iCurrentPlayer == PLAYER_TWO;
    }
    //endregion

    //region 5. Game logic related getters

    /**
     * Checks whether all dice is locked.
     * If all dice is locked during a roll,
     * the round should end immediately.
     *
     * @return allLocked : boolean : True if all dice is locked.
     */
    public boolean isAllDiceLocked() {
        for (GUIDice die : dice) {
            if (!die.isLocked()) {
                return false;
            }
        }

        return true;
    }

    /**
     * Calculates the total value of all dice.
     *
     * @return value : int : The total value of all dice.
     */
    public int getTotalDiceValue() {
        int value = 0;

        for (GUIDice die : dice) {
            value += die.getValue();
        }

        return value;
    }

    /**
     * Gets the current winner of the entire game.
     * Returns null if there is no discernible winner
     * at the moment.
     *
     * @return player : {@link Player} : The winner of the entire game.
     */
    public Player getWinner() {
        if (playerOne.getScore() < targetScore && playerTwo.getScore() < targetScore) {
            // The target score has not been reached yet.
            return null;
        }

        if (playerOne.getScore() == playerTwo.getScore()) {
            // It's a tie! We cannot find the winner yet.
            // A new round will be started.
            return null;
        }

        if (playerOne.getScore() > playerTwo.getScore()) {
            // The first player is the winner!
            return playerOne;
        } else {
            // The second player is the winner!
            return playerTwo;
        }
    }
    //endregion

    //region 6. Dice game logic functions

    /**
     * Hide all keep buttons for all dice.
     * This is necessary before the first roll.
     */
    public void hideDiceKeep() {
        for (GUIDice die : dice) {
            die.hideKeepButton();
        }
    }

    /**
     * Shows all keep buttons for all dice.
     * These buttons are shown after the first roll is over.
     */
    public void showDiceKeep() {
        for (GUIDice die : dice) {
            die.showKeepButton();
        }
    }

    /**
     * Reset all dice to their hidden state.
     * This is done when the player's turn changes.
     */
    public void resetDice() {
        for (GUIDice die : dice) {
            die.setValue(HIDDEN_DIE);
            die.setLocked(false);
            die.hideKeepButton();
        }
    }

    /**
     * Roll all the dice!
     */
    public void rollDice() {
        if (isRollingDice()) {
            // We are currently rolling dice.
            // We can't do anything at the moment.
            return;
        }

        sounds.get("rollDiceOne").stop();

        // Update the GUI accordingly.
        rollingDice = true;
        btnRoll.setEnabled(false);

        // Disable all dice.
        for (GUIDice die : dice) {
            die.setEnabled(false);
        }

        // Run our dice roll animation!
        timerHandler.removeCallbacksAndMessages(null);
        timerHandler.postDelayed(new Runnable() {
            // Our dice will be rolled 10 times in a row.
            private int count = 10;

            @Override
            public void run() {
                // Decrement the dice roll count.
                count--;

                // Roll all the dice!
                for (GUIDice die : dice) {
                    die.rollDice();
                }

                if (count < 0) {
                    // We've reached a negative count.
                    return;
                }

                if (count > 0) {
                    // We need to roll again!
                    timerHandler.postDelayed(this, 75);
                } else {
                    // This is the last roll.
                    // Let's update the GUI properly.
                    rollingDice = false;
                    btnRoll.setEnabled(true);

                    // Decrement the re-roll count.
                    decrementRerolls();

                    if (!hasRerollsLeft() || isAllDiceLocked()) {
                        // Oops! Looks like we either have no re-rolls left,
                        // or all our dice are locked. We need to end the match.
                        endMatch();
                    } else {
                        // We still have more things to do!
                        boolean aiTurn = isAITurn();

                        // Update the GUI.
                        for (GUIDice die : dice) {
                            die.showKeepButton();
                            die.setEnabled(!aiTurn);
                        }

                        if (aiTurn) {
                            // Looks like it's the AI rolling dice this time around.
                            // We need to start locking their dice, now that
                            // they've all been revealed.
                            btnRoll.setEnabled(false);
                            aiStartLocking();
                        }
                    }
                }
            }
        }, 0);
    }

    /**
     * This method is ran when the
     * intermission is over.
     * This updates the GUI accordingly.
     */
    public void endShowDice() {
        // Reset all our dice.

        if (iCurrentPlayer == PLAYER_ONE) {
            // Add the current dice state to the first dice list.
            firstDice.clear();

            for (GUIDice die : dice) {
                firstDice.add(die.getValue());
            }
        }

        setShowingDice(false);
        resetDice();

        if (iCurrentPlayer == PLAYER_ONE) {
            // Player one's turn has ended.
            // Player two's turn has begun.
            setCurrentPlayer(PLAYER_TWO);

            if (isAITurn()) {
                // Looks like it's the AI's turn.
                // Let's roll the dice as the AI.
                aiRollDice();
            }
        } else {
            // Player two's turn has ended.
            // Player one's turn has begun, the previous round is over.
            incrementRound();
            setCurrentPlayer(PLAYER_ONE);
        }
    }
    //endregion

    //region 7. Match game logic functions

    /**
     * This method is ran when the match has ended.
     */
    public void endMatch() {
        // Add the player's current score to its overall score.
        Player player = getPlayer();
        player.addScore(getTotalDiceValue());

        if (iCurrentPlayer == PLAYER_ONE) {
            // Intermission time! The first player's turn is over.
            setShowingDice(true);
        } else {
            Player winner = getWinner();

            if (winner == null) {
                // Intermission time! The second player's turn is over,
                // and there is no winner.
                setShowingDice(true);
            } else {
                // The second player's turn is over,
                // but we have a winner. Let's end the game.
                gameWon(winner);
            }
        }
    }

    /**
     * This method is ran when a winner has been selected.
     *
     * @param winner : {@link Player} : The overall winner of the game.
     */
    public void gameWon(Player winner) {
        // Update the database first.
        String winnerName = winner.getName();
        DbManager db = DbManager.getInstance(this);
        PlayerScore score = db.getPlayerScoreByName(winnerName);
        boolean updateScore = true;

        if (score == null) {
            // This score does not exist in the database yet, we have to add it first.
            score = new PlayerScore(-1, winnerName, 0, 0);
            updateScore = false;
        }

        // Update the win count.
        if (computer) {
            score.setWinsPvE(score.getWinsPvE() + 1);
        } else {
            score.setWinsPvP(score.getWinsPvP() + 1);
        }

        if (updateScore) {
            // Update the score.
            db.updatePlayerScore(score);
        } else {
            // Insert a new score.
            db.insertPlayerScore(score);
        }

        // Start the WinnerActivity now!
        Intent intent = new Intent(this, WinnerActivity.class);
        intent.putExtra("winnerName", winnerName);

        // Save the winner's dice to the new activity.
        if (winner.getName() == playerOne.getName()) {
            for (int i = 0; i < diceKeys.length; i++) {
                intent.putExtra(diceKeys[i], firstDice.get(i));
            }
        } else {
            for (int i = 0; i < diceKeys.length; i++) {
                intent.putExtra(diceKeys[i], dice.get(i).getValue());
            }
        }

        startActivity(intent);
        finish();
    }
    //endregion

    //region 8. Computer AI functions

    /**
     * Rolls the dice automatically as an AI player.
     * This will disable every button, so that the
     * human player cannot interfere with the AI's turn.
     */
    public void aiRollDice() {
        // Disable all buttons.
        for (GUIDice die : dice) {
            die.setEnabled(false);
        }

        btnRoll.setEnabled(false);

        // Roll dice in 0.5 seconds.
        timerHandler.removeCallbacksAndMessages(null);
        timerHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                rollDice();
            }
        }, 500);
    }

    /**
     * Locks all required dice as an AI player.
     * The AI will run its greedy algorithm
     * to decide which dice should be locked.
     */
    public void aiStartLocking() {
        // We will lock one dice every 0.5 seconds.

        timerHandler.removeCallbacksAndMessages(null);
        timerHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Run through all dice.
                for (GUIDice die : dice) {
                    if (die.aiShouldLock(iRerollsLeft) && !die.isLocked()) {
                        // Looks like there is a dice we need to lock!
                        // Lock it and run the task again.
                        timerHandler.postDelayed(this, 500);
                        die.setLocked(true);
                        return;
                    }
                }

                // There are no more dice that needs to be locked.
                // Let's roll the dice again.
                aiRollDice();
            }
        }, 500);
    }
    //endregion
}