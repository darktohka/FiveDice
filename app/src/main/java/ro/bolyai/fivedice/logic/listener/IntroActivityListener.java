package ro.bolyai.fivedice.logic.listener;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import ro.bolyai.fivedice.R;
import ro.bolyai.fivedice.gui.activity.GameActivity;
import ro.bolyai.fivedice.gui.activity.IntroActivity;

/**
 * Handles all of the click events of the IntroActivity
 */
public class IntroActivityListener implements View.OnClickListener,
        AdapterView.OnItemSelectedListener {

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
        this.activity.generateWidgets();
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

    /**
     * Called when an item is selected in the dropdown list
     * Enables or disables the txtSecondPlayerName in function to the selected gamemode
     *
     * @param parentSpinner       : {@link AdapterView} The AdapterView (Spinner) where the item is selected
     * @param currentSelectedItem : {@link View} The selected viwe within the AdapterView that was clicked
     *                            (this wil be a view provided by the adapter)
     * @param index               : int : The index of the clicked SpinnerItem which the gamemode is selected
     * @param id                  : long : The id of the clicked SpinnerItem
     */
    @Override
    public void onItemSelected(AdapterView<?> parentSpinner, View currentSelectedItem, int index, long id) {
        switch (parentSpinner.getId()) {
            case R.id.spGamemode:
                activity.setGameMode(index);

                if (index == activity.GAMEMODE_WITH_AI) {
                    activity.disableTxtPlayerTwoName();
                } else {
                    activity.enableTxtPlayerTwoName();
                }

                break;
            case R.id.spTargetScore:
                activity.updateTargetScore();
                break;
        }
    }

    /**
     * Called when Nothing is selected
     *
     * @param parentSpinner : {@link AdapterView} The AdapterView (Spinner) where the item is selected
     */
    @Override
    public void onNothingSelected(AdapterView<?> parentSpinner) {
        //Toast.makeText(activity, "Nothing is selected", Toast.LENGTH_LONG).show();
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
        intent.putExtra("computer", activity.getGameMode() == IntroActivity.GAMEMODE_WITH_AI);
        activity.startActivity(intent);

    }
    //endregion
}
