package ro.bolyai.fivedice.gui.activity;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import ro.bolyai.fivedice.R;
import ro.bolyai.fivedice.logic.listener.IntroActivityListener;

/**
 * This activity shows up when you select play in the MainActivity.
 * <p>
 * Here you can select the game mode, type in the player names and
 * proceed to the GameActivity
 */
public class IntroActivity extends AppCompatActivity {

    //region 0. Constants
    /**
     * The maximum amount of characters in a player name.
     */
    private static final int MAXIMUM_CHARACTER = 24;

    /**
     * A constant representing the PvP game mode.
     */
    public static final int GAMEMODE_PVP = 0;

    /**
     * A constant representing the AI game mode.
     */
    public static final int GAMEMODE_WITH_AI = 1;
    //endregion

    //region 1. Declarations
    /**
     * Handles all of the click events of this class
     */
    private IntroActivityListener introActivityListener;

    /**
     * The input field responsible for holding
     * the first player's name.
     */
    private EditText txtPlayerOneName;

    /**
     * The input field responsible for holding
     * the second player's name.
     */
    private EditText txtPlayerTwoName;

    /**
     * {@link Button} to start the game.
     */
    private Button btnStartGame;

    /**
     * {@link Spinner} to select the game mode
     */
    private Spinner spGamemode;

    /**
     * {@link Spinner} to select the target points
     */
    private Spinner spTargetScore;

    /**
     * The target score of the next round.
     */
    private int iTargetScore;

    /**
     * The game mode of the next round.
     * Can be either {@link IntroActivity#GAMEMODE_PVP} or {@link IntroActivity#GAMEMODE_WITH_AI}.
     */
    private int iGameMode;
    //endregion

    //region 2. Lifecycle

    /**
     * This is the first method ran after the constructor.
     *
     * @param savedInstanceState : {@link Bundle} : Our saved instance state.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //1. Set the layout
        this.setContentView(R.layout.intro_activity_layout);

        //2. Generate Listener
        this.introActivityListener = new IntroActivityListener(this);

        //3. Generate Widgets
        generateWidgets();

        //4. Initialize spinner items
        initSpinners();

        //5. Set the listeners
        btnStartGame.setOnClickListener(introActivityListener);
        spGamemode.setOnItemSelectedListener(introActivityListener);
        spTargetScore.setOnItemSelectedListener(introActivityListener);
    }
    //endregion

    //region 4. Helper functions and methods

    /**
     * It generates the Widgets:
     * {@link EditText}s and the start {@link Button}
     */
    public void generateWidgets() {
        this.txtPlayerOneName = findViewById(R.id.txtPlayerOneName);
        this.txtPlayerTwoName = findViewById(R.id.txtPlayerTwoName);
        this.spTargetScore = findViewById(R.id.spTargetScore);

        this.btnStartGame = findViewById(R.id.btnStartGame);
        this.spGamemode = findViewById(R.id.spGamemode);
    }

    /**
     * It checks the validity of the inputted data.
     * If there's any problem, returns the problem message as a {@link String}.
     *
     * @return strProblem : {@link String} : the problem
     */
    public String checkValidity() {
        String strProblem = "";
        String strPlayerOneName = getPlayerOneName();
        String strPlayerTwoName = getPlayerTwoName();

        if (strPlayerOneName.isEmpty()) {
            strProblem += "The first player's name is empty!\n";
        } else if (strPlayerOneName.length() > MAXIMUM_CHARACTER) {
            strProblem += "The first player's name is too long!\n";
        }

        if (iGameMode == GAMEMODE_PVP) {
            if (strPlayerTwoName.isEmpty()) {
                strProblem += "The second player's name is empty!\n";
            } else if (strPlayerOneName.equals(strPlayerTwoName)) {
                strProblem += "The two players must have different names!\n";
            }

            if (strPlayerTwoName.length() > MAXIMUM_CHARACTER) {
                strProblem += "The second player's name is too long!\n";
            }
        }

        return strProblem.trim();
    }

    /**
     * Initializes the {@link Spinner}s of this activity,
     * namely the game mode spinner and the target score spinner.
     */
    private void initSpinners() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.gamemodes, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spGamemode.setAdapter(adapter);

        adapter = ArrayAdapter.createFromResource(this, R.array.scores, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spTargetScore.setAdapter(adapter);

        updateTargetScore();
    }

    /**
     * Updates the target score of the next game
     * based on the {@link IntroActivity#spTargetScore}'s value.
     */
    public void updateTargetScore() {
        String strValue = spTargetScore.getSelectedItem().toString();
        iTargetScore = Integer.valueOf(strValue.replaceAll("[^0-9]", ""));
    }

    /**
     * Enables and shows {@link IntroActivity#txtPlayerOneName} when
     * the Player versus Player game mode is selected
     */
    public void enableTxtPlayerTwoName() {
        txtPlayerTwoName.setEnabled(true);
        txtPlayerTwoName.setVisibility(View.VISIBLE);
    }

    /**
     * Disables and hides {@link IntroActivity#txtPlayerTwoName} when
     * the Player versus AI game mode is selected
     */
    public void disableTxtPlayerTwoName() {
        txtPlayerTwoName.setEnabled(false);
        txtPlayerTwoName.setVisibility(View.GONE);
    }
    //endregion

    //region 5. Getters

    /**
     * Returns the {@link Spinner} responsible for
     * holding the next round's target score.
     *
     * @return spTargetScore : {@link Spinner} : Target score spinner
     */
    public Spinner getSpTargetScore() {
        return spTargetScore;
    }

    /**
     * Returns the first player's name.
     *
     * @return strPlayerOneName : {@link String} : First player name
     */
    public String getPlayerOneName() {
        return txtPlayerOneName.getText().toString().trim();
    }

    /**
     * Returns the second player's name.
     *
     * @return strPlayerTwoName : {@link String} : Second player name
     */
    public String getPlayerTwoName() {
        if (iGameMode == GAMEMODE_WITH_AI) {
            return getString(R.string.computer_player_name);
        } else {
            return txtPlayerTwoName.getText().toString().trim();
        }
    }

    /**
     * Returns the next game's target score.
     *
     * @return iTargetScore : int : Target score
     */
    public int getTargetScore() {
        return iTargetScore;
    }

    /**
     * Returns the next game's game mode.
     *
     * @return iGameMode : int : Either {@link IntroActivity#GAMEMODE_PVP} or {@link IntroActivity#GAMEMODE_WITH_AI}
     */
    public int getGameMode() {
        return iGameMode;
    }
    //endregion

    //region 6. Setters

    /**
     * Sets the next game's game mode.
     *
     * @param iGameMode : int : Either {@link IntroActivity#GAMEMODE_PVP} or {@link IntroActivity#GAMEMODE_WITH_AI}
     */
    public void setGameMode(int iGameMode) {
        this.iGameMode = iGameMode;
    }
    //endregion
}