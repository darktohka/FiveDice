package ro.bolyai.fivedice.gui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import ro.bolyai.fivedice.R;
import ro.bolyai.fivedice.logic.listener.IntroActivityListener;

/**
 * This activity shows up when you select play in the MainActivity
 * Here you can select the gamemode, type in the username(s) and
 * proceed to the GameActivity
 */
public class IntroActivity extends AppCompatActivity {

    //region 0. Constants
    private static final int MAXIMUM_CHARACTER = 16;

    private static final int MINIMUM_TARGET_SCORE = 40;
    private static final int MAXIMUM_TARGET_SCORE = 1500;

    public static final int GAMEMODE_PVP = 0;
    public static final int GAMEMODE_WITH_AI = 1;
    //endregion

    //region 1. Declarations
    /**
     * Handles all of the click events of this class
     */
    private IntroActivityListener introActivityListener;

    /**
     * Input fields to type in the player(s) name
     * and the target score
     * Player one name: {@link EditText}
     * Player two name: {@link EditText}
     * Target Score: {@link EditText}
     */
    private EditText txtPlayerOneName;
    private EditText txtPlayerTwoName;
    private EditText txtTargetScore;

    /**
     * {@link Button} to start the game.
     */
    private Button btnStartGame;

    /**
     * {@link Spinner} to select the gamemode
     */
    private Spinner spGamemode;

    private String strPlayerOneName;
    private String strPlayerTwoName;

    private int iTargetScore = 0;

    private int iGamemode;
    //endregion

    //region 2. Lifecycle
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
        initGamemodeSpinner();
        //5. Set the listeners
        btnStartGame.setOnClickListener(introActivityListener);
        spGamemode.setOnItemSelectedListener(introActivityListener);
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
        this.txtTargetScore = findViewById(R.id.txtTargetScore);

        this.btnStartGame = findViewById(R.id.btnStartGame);
        this.spGamemode = findViewById(R.id.spGamemode);
    }

    /**
     * It checks the validity of the inputted data.
     * If there's any problem, returns the problem message as a {@link String}
     *
     * @return : {@link String}: problem
     */
    public String checkValidity() {
        String strProblem = "";

        this.strPlayerOneName = this.txtPlayerOneName.getText().toString();
        this.strPlayerTwoName = this.txtPlayerTwoName.getText().toString();
        this.iTargetScore = 0;

        if(strPlayerOneName.equals(strPlayerTwoName))
            strProblem+="Change the name!";

        if (txtTargetScore.getText().toString().isEmpty()) {
            strProblem += "The target score is empty!\n";
        } else {
            try {
                this.iTargetScore = Integer.valueOf(this.txtTargetScore.getText().toString());
            } catch (Exception e) {
                strProblem += "The target score is invalid!\n";
            }
        }

        if (strPlayerOneName.isEmpty() || (strPlayerTwoName.isEmpty() && iGamemode == GAMEMODE_PVP)) {
            strProblem += "The name is empty!\n";
        }

        if (strPlayerOneName.length() > MAXIMUM_CHARACTER || strPlayerTwoName.length() > MAXIMUM_CHARACTER) {
            strProblem += "The name is too long!\n";
        }

        if (iTargetScore < MINIMUM_TARGET_SCORE) {
            strProblem += "The target score is too low!\n";
        } else if (iTargetScore > MAXIMUM_TARGET_SCORE) {
            strProblem += "The target score is too high!\n";
        }

        return strProblem;
    }

    /**
     * Initializes the items in the gamemode selection {@link Spinner}
     */
    private void initGamemodeSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.gamemodes, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spGamemode.setAdapter(adapter);

    }

    /**
     * Enables and sets visible txtPlayerTwoName {@link EditText} when Player versus Player
     * gamemode is selected
     */
    public void enableTxtPlayerTwoName(){
        txtPlayerTwoName.setEnabled(true);
        txtPlayerTwoName.setVisibility(View.VISIBLE);
    }

    /**
     * Disables and sets invisible txtPlayerTwoName {@link EditText} when Player versus AI
     * gamemode is selected
     */
    public void disableTxtPlayerTwoName(){
        txtPlayerTwoName.setEnabled(false);
        txtPlayerTwoName.setVisibility(View.GONE);
    }

    //endregion

    //region 5. Getters
    public String getPlayerOneName() {
        return strPlayerOneName;
    }

    public String getPlayerTwoName() {
        if (iGamemode == GAMEMODE_WITH_AI) {
            return getString(R.string.computer_player_name);
        } else {
            return strPlayerTwoName;
        }
    }

    public int getTargetScore() {
        return iTargetScore;
    }

    public int getGamemode() {
        return iGamemode;
    }

    //endregion

    //region 6. Setters

    public void setGamemode(int iGamemode) {
        this.iGamemode = iGamemode;
    }

    //endregion
}