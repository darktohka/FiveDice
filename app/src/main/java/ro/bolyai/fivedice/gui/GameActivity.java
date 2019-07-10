package ro.bolyai.fivedice.gui;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Dimension;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ro.bolyai.fivedice.R;
import ro.bolyai.fivedice.logic.GameActivityListener;
import ro.bolyai.fivedice.model.Dice;
import ro.bolyai.fivedice.model.Player;

public class GameActivity extends AppCompatActivity {

    //region 0. Constants
    private static final int DEFAULT_TARGET_SCORE = 300;

    private static final int PLAYER_ONE = 0;
    private static final int PLAYER_TWO = 1;

    private static final int REROLL_COUNT = 3;

    private static final int HIDDEN_DIE = 0;
    //endregion

    //region 1. Declarations
    private TextView txtRound;
    private TextView txtRoundWhich;
    private TextView txtRerolls;
    private Button btnRoll;

    private GameActivityListener gameActivityListener;

    private Player playerOne;
    private Player playerTwo;
    private int targetScore;
    private List<Dice> dice;

    private int currentRound;
    private int currentPlayer = -1;
    private int rerollsLeft;

    private boolean showingDice;
    private boolean rollingDice;

    private Handler timerHandler;
    //endregion

    //region 2. Lifecycle
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_activity_layout);

        txtRound = findViewById(R.id.txtRound);
        txtRoundWhich = findViewById(R.id.txtRoundWhich);
        txtRerolls = findViewById(R.id.txtRerolls);
        btnRoll = findViewById(R.id.btnRoll);

        timerHandler = new Handler();

        if (savedInstanceState == null) {
            initializeGame();
        }

        gameActivityListener = new GameActivityListener(this);
        btnRoll.setOnClickListener(gameActivityListener);
    }

    @Override
    public void onPause() {
        super.onPause();
        timerHandler.removeCallbacksAndMessages(null);
    }

    private void initializeGame() {
        Intent intent = getIntent();
        String playerOneName = getString(R.string.default_player_one_name);
        String playerTwoName = getString(R.string.default_player_two_name);
        targetScore = DEFAULT_TARGET_SCORE;
        currentPlayer = -1;

        if (intent.hasExtra("playerOneName")) {
            playerOneName = intent.getStringExtra("playerOneName");
        }

        if (intent.hasExtra("playerTwoName")) {
            playerTwoName = intent.getStringExtra("playerTwoName");
        }

        if (intent.hasExtra("targetScore")) {
            targetScore = intent.getIntExtra("targetScore", DEFAULT_TARGET_SCORE);
        }

        playerOne = new Player(
                (TextView) findViewById(R.id.txtPlayerOneName),
                (TextView) findViewById(R.id.txtPlayerOneScore),
                (ProgressBar) findViewById(R.id.barPlayerOne),
                playerOneName, 0);
        playerTwo = new Player(
                (TextView) findViewById(R.id.txtPlayerTwoName),
                (TextView) findViewById(R.id.txtPlayerTwoScore),
                (ProgressBar) findViewById(R.id.barPlayerTwo),
                playerTwoName, 0);
        playerOne.initialize(targetScore);
        playerTwo.initialize(targetScore);

        dice = new ArrayList<>();
        dice.add(new Dice((ImageView) findViewById(R.id.imgDice1), (Button) findViewById(R.id.btnKeep1)));
        dice.add(new Dice((ImageView) findViewById(R.id.imgDice2), (Button) findViewById(R.id.btnKeep2)));
        dice.add(new Dice((ImageView) findViewById(R.id.imgDice3), (Button) findViewById(R.id.btnKeep3)));
        dice.add(new Dice((ImageView) findViewById(R.id.imgDice4), (Button) findViewById(R.id.btnKeep4)));
        dice.add(new Dice((ImageView) findViewById(R.id.imgDice5), (Button) findViewById(R.id.btnKeep5)));

        for (Dice die : dice) {
            die.initialize();
        }

        setCurrentRound(1);
        setCurrentPlayer(PLAYER_ONE);
        hideDiceKeep();

        showingDice = false;
        rollingDice = false;
    }

    public int getCurrentRound() {
        return currentRound;
    }

    public void setCurrentRound(int currentRound) {
        this.currentRound = currentRound;

        if (txtRound != null) {
            txtRound.setText(String.format(getString(R.string.round_text), currentRound));
        }
    }

    public void incrementRound() {
        this.setCurrentRound(currentRound + 1);
    }

    public int getCurrentPlayer() {
        return currentPlayer;
    }

    public Player getPlayer() {
        if (currentPlayer == PLAYER_ONE) {
            return playerOne;
        } else {
            return playerTwo;
        }
    }

    public void setCurrentPlayer(int currentPlayer) {
        if (this.currentPlayer == currentPlayer) {
            return;
        }

        this.currentPlayer = currentPlayer;

        if (txtRoundWhich != null) {
            txtRoundWhich.setText(String.format(getString(R.string.turn_text), getPlayer().getName()));
        }

        if (currentPlayer == PLAYER_ONE) {
            playerOne.getTxtPlayerName().setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            playerTwo.getTxtPlayerName().setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            playerOne.getTxtPlayerName().setTextColor(ContextCompat.getColor(this, R.color.colorKeepInactive));
            playerTwo.getTxtPlayerName().setTextColor(ContextCompat.getColor(this, android.R.color.black));
        } else {
            playerOne.getTxtPlayerName().setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            playerTwo.getTxtPlayerName().setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            playerOne.getTxtPlayerName().setTextColor(ContextCompat.getColor(this, android.R.color.black));
            playerTwo.getTxtPlayerName().setTextColor(ContextCompat.getColor(this, R.color.colorKeepInactive));
        }

        setRerollsLeft(REROLL_COUNT);
    }

    public int getRerollsLeft() {
        return rerollsLeft;
    }

    public void setRerollsLeft(int rerollsLeft) {
        this.rerollsLeft = rerollsLeft;

        if (txtRerolls != null) {
            txtRerolls.setText(String.format(getString(R.string.rerolls_left_text), rerollsLeft));
        }
    }

    public void decrementRerolls() {
        if (rerollsLeft > 0) {
            this.setRerollsLeft(rerollsLeft - 1);
        }
    }

    public boolean hasRerollsLeft() {
        return this.rerollsLeft > 0;
    }

    public boolean isShowingDice() {
        return showingDice;
    }

    public void setShowingDice(boolean showingDice) {
        this.showingDice = showingDice;

        if (showingDice) {
            hideDiceKeep();
            btnRoll.setText(getString(R.string.btn_roll_next));
        } else {
            showDiceKeep();
            btnRoll.setText(getString(R.string.btn_roll));
        }
    }

    public boolean isRollingDice() {
        return rollingDice;
    }

    public void setRollingDice(boolean rollingDice) {
        this.rollingDice = rollingDice;
    }

    public void hideDiceKeep() {
        for (Dice die : dice) {
            die.hideKeepButton();
        }
    }

    public void showDiceKeep() {
        for (Dice die : dice) {
            die.showKeepButton();
        }
    }

    public boolean isAllDiceLocked() {
        for (Dice die : dice) {
            if (!die.isLocked()) {
                return false;
            }
        }

        return true;
    }

    public int getTotalDiceValue() {
        int value = 0;

        for (Dice die : dice) {
            value += die.getValue();
        }

        return value;
    }

    public Player getWinner() {
        if (playerOne.getScore() < targetScore && playerTwo.getScore() < targetScore) {
            return null;
        }

        if (playerOne.getScore() == playerTwo.getScore()) {
            return null;
        }

        if (playerOne.getScore() > playerTwo.getScore()) {
            return playerOne;
        } else {
            return playerTwo;
        }
    }

    public void resetDice() {
        for (Dice die : dice) {
            die.setValue(HIDDEN_DIE);
            die.setLocked(false);
            die.hideKeepButton();
        }
    }

    public void rollDice() {
        if (isRollingDice()) {
            return;
        }

        rollingDice = true;
        btnRoll.setEnabled(false);

        for (Dice die : dice) {
            die.setEnabled(false);
        }

        timerHandler.postDelayed(new Runnable() {
            private int count = 10;

            @Override
            public void run() {
                count--;

                for (Dice die : dice) {
                    die.rollDice();
                }

                if (count > 0) {
                    timerHandler.postDelayed(this, 75);
                } else {
                    rollingDice = false;
                    btnRoll.setEnabled(true);

                    for (Dice die : dice) {
                        die.showKeepButton();
                        die.setEnabled(true);
                    }

                    decrementRerolls();

                    if (!hasRerollsLeft() || isAllDiceLocked()) {
                        endMatch();
                    }
                }
            }
        }, 0);
    }

    public void endMatch() {
        Player player = getPlayer();
        player.addScore(getTotalDiceValue());

        if (currentPlayer == PLAYER_ONE) {
            setShowingDice(true);
        } else {
            Player winner = getWinner();

            if (winner == null) {
                setShowingDice(true);
            } else {
                gameWon(winner);
            }
        }
    }

    public void gameWon(Player winner) {
        Toast.makeText(this, winner.getName() + " has won !!! ", Toast.LENGTH_SHORT).show();
    }

    public void endShowDice() {
        setShowingDice(false);
        resetDice();

        if (currentPlayer == PLAYER_ONE) {
            // Player one
            setCurrentPlayer(PLAYER_TWO);
        } else {
            // Player two
            incrementRound();
            setCurrentPlayer(PLAYER_ONE);
        }
    }

    public TextView getTxtRound() {
        return txtRound;
    }

    public TextView getTxtRoundWhich() {
        return txtRoundWhich;
    }

    public TextView getTxtRerolls() {
        return txtRerolls;
    }

    public Player getPlayerOne() {
        return playerOne;
    }

    public Player getPlayerTwo() {
        return playerTwo;
    }

    public int getTargetScore() {
        return targetScore;
    }

    public List<Dice> getDice() {
        return dice;
    }
    //endregion
}
