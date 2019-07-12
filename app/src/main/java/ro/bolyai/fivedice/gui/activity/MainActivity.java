package ro.bolyai.fivedice.gui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageView;

import ro.bolyai.fivedice.R;
import ro.bolyai.fivedice.logic.listener.MainActivityListener;

/**
 * Entry point of the application
 * Here you can choose if you want to
 * play the game or check the
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
    private ImageView imgFiveDice;

    /**
     * This {@link Button} is in charge of
     * opening up the {@link IntroActivity}.
     */
    private Button btnPlay;

    /**
     * This {@link Button} is in charge of
     * opening up the {@link LeaderboardActivity}.
     */
    private Button btnLeaderboard;

    /**
     * This {@link Button} is in charge of
     * opening up the {@link InfoActivity}.
     */
    private Button btnInfo;

    /**
     * Handles the clicks of the class
     */
    private MainActivityListener mainActivityListener;
    //endregion

    //region 2. Lifecycle

    /**
     * First method invoked after the constructor
     *
     * @param savedInstanceState : {@link Bundle} : The saved instance state
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
        btnInfo=findViewById(R.id.btnInfo);

        //3. Generate the Listener
        mainActivityListener = new MainActivityListener(this);

        //4. Add Listener
        btnPlay.setOnClickListener(mainActivityListener);
        btnLeaderboard.setOnClickListener(mainActivityListener);
        btnInfo.setOnClickListener(mainActivityListener);
    }
    //endregion
}