package ro.bolyai.fivedice.logic;

import android.view.View;

import ro.bolyai.fivedice.R;
import ro.bolyai.fivedice.gui.GameActivity;
import ro.bolyai.fivedice.model.Dice;

public class GameActivityListener implements View.OnClickListener {

    //region 1. Declarations
    private GameActivity activity;
    //endregion

    //region 2. Constructors
    public GameActivityListener(GameActivity activity) {
        this.activity = activity;
    }
    //endregion

    //region 3. Getters and setters
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

        if (activity.isShowingDice()) {
            activity.endShowDice();
            return;
        }

        for (Dice die : activity.getDice()) {
            die.rollDice();
            die.enable();
        }

        activity.decrementRerolls();

        if (!activity.hasRerollsLeft() || activity.isAllDiceLocked()) {
            activity.endMatch();
        }
    }
    //endregion
}
