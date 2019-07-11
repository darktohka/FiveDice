package ro.bolyai.fivedice.gui;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import ro.bolyai.fivedice.R;
import ro.bolyai.fivedice.logic.LeaderboardActivityListener;

public class LeaderboardActivity extends AppCompatActivity {
    //region 0. Constants

    //endregion

    //region 1. Decl and Init
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
        this.lvAllPlayers=findViewById(R.id.lvAllPlayers);

        //3.Generate listener
        leaderboardActivityListener= new LeaderboardActivityListener(this);

        //4. Generate new ListViewAdapter
        leaderboardActivityListener.updatePlayerListView();


    }
    //endregion
}