package ro.bolyai.fivedice.logic.listener;

import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import ro.bolyai.fivedice.R;
import ro.bolyai.fivedice.gui.GameActivity;
import ro.bolyai.fivedice.gui.IntroActivity;

/**
 * Handles all of the click events of the IntroActivity
 */
public class IntroActivityListener implements View.OnClickListener {

    //region 0. Constants
    //endregion

    //region 1. Declarations
    /**
     * Working reference
     */
    private IntroActivity activity;
    //endregion

    //region 2. Constructors

    /**
     * Default Constructor setting the working reference
     *
     * @param activity : {@link IntroActivity} : Working reference
     */
    public IntroActivityListener(IntroActivity activity) {
        this.activity = activity;
        this.activity.settingTxtButton();
    }

    //endregion


    //region 3. Listener functions and methods

    /**
     * Called when a view has been clicked.
     *
     * @param v : {@link View} : The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnStartGame:
                buttonPressed();
                break;
        }
    }

    /**
     * Called when the button to start the game is pressed
     * It checks for the input errors and then starts the game
     */
    public void buttonPressed() {
        String error = activity.checkValidity();

        if (error.isEmpty()) {
            newActivity();
        } else {
            Toast.makeText(activity, error, Toast.LENGTH_SHORT).show();
        }
    }
    //endregion

    //region 4. Jumping to a new activity

    /**
     * Starts the {@link GameActivity} with playerOneName, playerTwoName, targetScore
     * attached as an extra
     */
    public void newActivity() {
        Intent intent = new Intent(activity, GameActivity.class);

        intent.putExtra("playerOneName", activity.getPlayerOneName());
        intent.putExtra("playerTwoName", activity.getPlayerTwoName());
        intent.putExtra("targetScore", activity.getTargetScore());

        activity.startActivity(intent);

    }
    //endregion
}
