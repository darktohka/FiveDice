package ro.bolyai.fivedice.logic.listener;

import android.view.View;

import ro.bolyai.fivedice.R;
import ro.bolyai.fivedice.gui.activity.GameActivity;

/**
 * Handles all of the click events of the GameActivity
 */
public class GameActivityListener implements View.OnClickListener {

    //region 1. Declarations
    /**
     * Working reference of the current {@link GameActivity}
     */
    private GameActivity activity;
    //endregion

    //region 2. Constructors

    /**
     * Default constructor, setting the working reference of the
     * current activity
     *
     * @param activity : {@link GameActivity} : Working reference
     */
    public GameActivityListener(GameActivity activity) {
        this.activity = activity;
    }
    //endregion

    //region 3. Getters and setters

    /**
     * Returns the working reference of our {@link GameActivity}.
     *
     * @return activity : {@link GameActivity} : Working reference
     */
    public GameActivity getActivity() {
        return activity;
    }
    //endregion

    //region 4. View functions

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        if (v.getId() != R.id.btnRoll) {
            return;
        }

        // Roll the dice
        if (activity.isShowingDice()) {
            activity.endShowDice();
        } else {
            activity.rollDice();
        }
    }
    //endregion
}
