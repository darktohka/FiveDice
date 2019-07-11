package ro.bolyai.fivedice.gui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

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

    private String strPlayerOneName;
    private String strPlayerTwoName;

    private int iTargetScore = 0;
    //endregion

    //region 2. Lifecycle
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.intro_activity_layout);

        this.introActivityListener = new IntroActivityListener(this);

        settingTxtButton();

        btnStartGame.setOnClickListener(introActivityListener);
    }
    //endregion

    //region 4. Helper functions and methods

    /**
     * It generates the Widgets:
     * {@link EditText}s and the start {@link Button}
     */

    public void settingTxtButton() {
        this.txtPlayerOneName = findViewById(R.id.txtPlayerOneName);
        this.txtPlayerTwoName = findViewById(R.id.txtPlayerTwoName);
        this.txtTargetScore = findViewById(R.id.txtTargetScore);

        this.btnStartGame = findViewById(R.id.btnStartGame);
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

        if (strPlayerOneName.isEmpty() || strPlayerTwoName.isEmpty()) {
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
    //endregion

    //region 5. Getters
    public String getPlayerOneName() {
        return strPlayerOneName;
    }

    public String getPlayerTwoName() {
        return strPlayerTwoName;
    }

    public int getTargetScore() {
        return iTargetScore;
    }
    //endregion
}