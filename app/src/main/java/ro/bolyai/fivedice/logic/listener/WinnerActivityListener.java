package ro.bolyai.fivedice.logic.listener;

import android.content.Intent;
import android.view.View;

import ro.bolyai.fivedice.R;
import ro.bolyai.fivedice.gui.activity.IntroActivity;
import ro.bolyai.fivedice.gui.activity.MainActivity;
import ro.bolyai.fivedice.gui.activity.WinnerActivity;

public class WinnerActivityListener implements View.OnClickListener {

    //region 1. Declarations
    /**
     * The working reference to our {@link WinnerActivity}.
     */
    private WinnerActivity activity;
    //endregion

    //region 2. Constructors

    /**
     * Creates a new {@link WinnerActivityListener},
     * linking it to a {@link WinnerActivity}.
     *
     * @param activity
     */
    public WinnerActivityListener(WinnerActivity activity) {
        this.activity = activity;
    }
    //endregion

    //region 3. Getters and setters

    /**
     * Returns the working reference to our activity.
     *
     * @return activity : {@link WinnerActivity} : Our winner activity.
     */
    public WinnerActivity getActivity() {
        return activity;
    }

    //endregion

    //region 4. Event handling

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        // Choose the next activity to open.
        Class nextActivity = null;

        switch (v.getId()) {
            case R.id.btnPlayAgain:
                nextActivity = IntroActivity.class;
                break;
            case R.id.btnMainMenu:
                nextActivity = MainActivity.class;
                break;
        }

        // The user clicked another button,
        // therefore there is no activity to open.
        if (nextActivity == null) {
            return;
        }

        // Open our new activity!
        activity.finish();
        activity.startActivity(new Intent(activity, nextActivity));
    }

    //endregion
}
