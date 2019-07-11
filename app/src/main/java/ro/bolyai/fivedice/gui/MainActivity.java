package ro.bolyai.fivedice.gui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import ro.bolyai.fivedice.R;
import ro.bolyai.fivedice.logic.MainActivityListener;

/**
 * Entrypoint in the Application
 * Here you can choose if you want to
 * play the game of want to see the
 * leaderboard of the game.
 * <p>
 * All the click events are handled in {@link MainActivityListener}
 */
public class MainActivity extends AppCompatActivity {

    //region 0. Constants
    //endregion

    //region 1. Decl. and Init widgets and attributes
    /**
     * Shows the main title logo of the game
     */
    ImageView imgFiveDice;

    /**
     * Open up the game or the leaderboard
     */
    Button btnPlay;
    Button btnLeaderboard;

    /**
     * Handles the clicks of the class
     */
    MainActivityListener mainActivityListener;
    //endregion

    //region 2. LifeCircle

    /**
     * First method invoked after the Constructor
     * @param savedInstanceState : {@link Bundle} : InputData from the user
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //1. Setting the Layout
        setContentView(R.layout.main_activity_layout);

        //2. Generate the Widgets
        imgFiveDice = findViewById(R.id.imgFiveDice);

        btnPlay = findViewById(R.id.btnPlay);
        btnLeaderboard = findViewById(R.id.btnLeaderboard);

        //3. Generate the Listener
        mainActivityListener = new MainActivityListener(this);

        //4. Add Listener
        btnPlay.setOnClickListener(mainActivityListener);
        btnLeaderboard.setOnClickListener(mainActivityListener);

    }
    //endregion

    //region 3. Helper functions and methods
    //endregion
}
