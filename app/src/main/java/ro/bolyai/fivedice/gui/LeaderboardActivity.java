package ro.bolyai.fivedice.gui;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import ro.bolyai.fivedice.R;
import ro.bolyai.fivedice.logic.listener.LeaderboardActivityListener;

/**
 * This activity shows the list of players played in this games
 * and shows their achieved scores and performances in a {@link ListView}
 */

public class LeaderboardActivity extends AppCompatActivity {
    //region 0. Constants

    //endregion

    //region 1. Decl and Init
    /**
     * Shows all Players in itself
     * the data and the ListViewItems
     * will be generated and handled
     * in the ListViewAdapter
     */
    private ListView lvAllPlayers;


    private LeaderboardActivityListener leaderboardActivityListener;
    //endregion

    //region 2. Lifecycle


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //1. Setting the layout
        this.setContentView(R.layout.leaderboard_activity_layout);

        //2. generate the widgets
        this.lvAllPlayers = findViewById(R.id.lvAllPlayers);

        //3.Generate listener
        leaderboardActivityListener = new LeaderboardActivityListener(this);

        //4. Generate new ListViewAdapter
        leaderboardActivityListener.updatePlayerListView();


    }
    //endregion
}