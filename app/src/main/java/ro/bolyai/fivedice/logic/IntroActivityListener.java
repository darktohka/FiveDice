package ro.bolyai.fivedice.logic;

import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import ro.bolyai.fivedice.R;
import ro.bolyai.fivedice.gui.GameActivity;
import ro.bolyai.fivedice.gui.IntroActivity;

public class IntroActivityListener implements View.OnClickListener {

    //region 0. Constants
    //endregion

    //region 1. Declarations
    private IntroActivity activity;
    //endregion

    //region 2. Constructors
    public IntroActivityListener(IntroActivity activity) {
        this.activity = activity;
        this.activity.settingTxtButton();
    }

    //endregion


    //region 3. Listener functions and methods

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnStartGame:
                buttonPressed();
                break;
        }
    }


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
    public void newActivity() {
        Intent intent = new Intent(activity, GameActivity.class);

        intent.putExtra("playerOneName", activity.getPlayerOneName());
        intent.putExtra("playerTwoName", activity.getPlayerTwoName());
        intent.putExtra("targetScore", activity.getTargetScore());

        activity.startActivity(intent);

    }
    //endregion
}
