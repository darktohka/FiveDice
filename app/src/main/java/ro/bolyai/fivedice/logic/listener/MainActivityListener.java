package ro.bolyai.fivedice.logic.listener;

import android.content.Intent;
import android.view.View;

import ro.bolyai.fivedice.R;
import ro.bolyai.fivedice.gui.activity.IntroActivity;
import ro.bolyai.fivedice.gui.activity.LeaderboardActivity;
import ro.bolyai.fivedice.gui.activity.MainActivity;

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
     * @param view : {@link View} : Represents the clicked view.
     */
    @Override
    public void onClick(View view) {
        switch(view.getId()){

            case R.id.btnPlay:
                playButtonClicked();
                break;

            case R.id.btnLeaderboard:
                leaderboardButtonClicked();
                break;

        }
    }

    //endregion

    //region 5. Methods called when a view is clicked

    /**
     * Called when the play button is clicked
     */
    private void playButtonClicked(){
        //1. EXPLICIT INTENT to start IntroActivity
        Intent intentToStartIntroActivity = new Intent(mainActivity, IntroActivity.class);

        //2. Place to put Extras

        //3. Start the activity
        mainActivity.startActivity(intentToStartIntroActivity);
    }

    /**
     * Called when the leader board button is clicked
     */
    private void leaderboardButtonClicked(){
        //1. EXPLICIT INTENT to start the LeaderboardActivity
        Intent intentToStartLeaderboardActivity = new Intent(mainActivity, LeaderboardActivity.class);

        //2. Place to put Extras

        //3. Start the activity
        mainActivity.startActivity(intentToStartLeaderboardActivity);
    }

    //endregion

    //region last Helperfunctions and methods
    //endregion

}
