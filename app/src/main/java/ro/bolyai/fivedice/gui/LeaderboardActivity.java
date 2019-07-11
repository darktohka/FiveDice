package ro.bolyai.fivedice.gui;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import ro.bolyai.fivedice.R;
import ro.bolyai.fivedice.gui.listview.ListViewAdapter;

/**
 * This activity shows the list of players played in this games
 * and shows their achieved scores and performances in a {@link ListView}.
 */
public class LeaderboardActivity extends AppCompatActivity {

    //region 1. Declarations
    /**
     * This {@link ListView} shows all players in
     * itself. The data and the {@link ListView} items
     * will be generated and handled in the {@link ListViewAdapter}.
     */
    private ListView lvAllPlayers;

    /**
     * The {@link ListViewAdapter} responsible for
     * filling the ListView with the database elements.
     */
    private ListViewAdapter listViewAdapter;
    //endregion

    //region 2. Lifecycle

    /**
     * This is ran during the creation of the activity.
     *
     * @param savedInstanceState : {@link Bundle} : The saved instance state.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //1. Setting the layout
        this.setContentView(R.layout.leaderboard_activity_layout);

        //2. generate the widgets
        this.lvAllPlayers = findViewById(R.id.lvAllPlayers);

        //3.Generate new ListViewAdapter
        this.listViewAdapter = new ListViewAdapter(this);
        this.lvAllPlayers.setAdapter(this.listViewAdapter);
    }
    //endregion
}