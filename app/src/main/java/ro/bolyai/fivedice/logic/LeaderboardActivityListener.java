package ro.bolyai.fivedice.logic;

import android.widget.ListView;

import ro.bolyai.fivedice.R;
import ro.bolyai.fivedice.gui.LeaderboardActivity;
import ro.bolyai.fivedice.gui.MainActivity;
import ro.bolyai.fivedice.gui.listview.ListViewAdapter;

public class LeaderboardActivityListener {
    //region 1. Decl and Init
    private LeaderboardActivity activity;

    private ListView lvAllPlayers;
    private ListViewAdapter listViewAdapter;
    //endregion

    //region 2.Constructor

    public LeaderboardActivityListener(LeaderboardActivity activity) {
        this.activity=activity;
        generateWidgetReferences();
    }


    //endregion

    //region 3. Helper functions

    public void updatePlayerListView(){
        this.listViewAdapter= new ListViewAdapter(activity);
        lvAllPlayers.setAdapter(listViewAdapter);
    }

    private void generateWidgetReferences() {
        lvAllPlayers = activity.findViewById(R.id.lvAllPlayers);
    }
    //endregion
}
