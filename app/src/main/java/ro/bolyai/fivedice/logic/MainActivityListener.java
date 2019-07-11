package ro.bolyai.fivedice.logic;

import android.content.Context;
import android.view.View;

import ro.bolyai.fivedice.gui.MainActivity;

/**
 * Listener class of the MainActivity
 * This class handles all the click events of the {@link View}s
 * of the MainActivity
 * It opens up the IntroActivity or the LeaderBoard
 */
public class MainActivityListener implements View.OnClickListener {

    //region 0. Constants
    //endregion

    //region 1. Decl. and init Variables
    /**
     * Working reference {@link MainActivity}
     */
    private MainActivity mainActivity;

    //endregion

    //region 2. Constructor

    /**
     * Default Constructor sets the working
     * reference of the current activity
     * @param mainActivity : {@link MainActivity} : Working reference
     */
    public MainActivityListener(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    //endregion

    //region 3. Getters and Setters
    //endregion

    //region 4. OnClick Handling Buttons and Views

    /**
     * This function is called when a view is clicked
     * in the MainActivity layout
     * @param v : {@link View} : Represents the clicked view.
     */
    @Override
    public void onClick(View v) {

    }

    //endregion

    //region 5. Helperfunctions and methods
    //endregion

}
