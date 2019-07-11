package ro.bolyai.fivedice.gui.listview;


import android.widget.TextView;

/**
 * Holds the Widget references of
 * a single ListViewItem which is going to
 * be generated in the ListViewAdapter.
 * <p>
 * It has no Logic, no generation of
 * Objects/Widgets what so ever.
 */
public class ViewHolder {

    //region 1. Decl and Init

    /**
     * Will display the {@link ro.bolyai.fivedice.model.Player} Names in a single
     * ListViewItem. Which is integerated / shown in
     * the ListView Widget in the {@link ro.bolyai.fivedice.gui.LeaderboardActivity}
     */
    private TextView txtPlayerName;

    /**
     * Will display the {@link ro.bolyai.fivedice.model.Player} PvP Wins
     * in a single ListViewItem. Which is integrateed / shown in
     * the ListView Widget in the {@link ro.bolyai.fivedice.gui.LeaderboardActivity}
     */
    private TextView txtPlayerPvPWins;

    /**
     * Will display the {@link ro.bolyai.fivedice.model.Player} PvE Wins
     * in a single ListViewItem. Which is integrateed / shown in
     * the ListView Widget in the {@link ro.bolyai.fivedice.gui.LeaderboardActivity}
     */
    private TextView txtPlayerPvEWins;

    //endregion

    //region 2. Constructor

    /**
     * Default Constructor to set the Widget referenves from
     * a single ListViewItem directly
     *
     * @param txtPlayerName : {@link TextView} : {@link ro.bolyai.fivedice.model.Player} name
     * @param txtPlayerPvPWins : {@link TextView} : {@link ro.bolyai.fivedice.model.Player} PvP Wins
     * @param txtPlayerPvEWins : {@link TextView} : {@link ro.bolyai.fivedice.model.Player} PvE Wins
     */
    public ViewHolder(TextView txtPlayerName, TextView txtPlayerPvPWins, TextView txtPlayerPvEWins) {
        this.txtPlayerName = txtPlayerName;
        this.txtPlayerPvPWins = txtPlayerPvPWins;
        this.txtPlayerPvEWins = txtPlayerPvEWins;
    }

    //endregion

    //region 3. Getters and Setters

    public TextView getTxtPlayerName() {
        return txtPlayerName;
    }

    public TextView getTxtPlayerPvPWins() {
        return txtPlayerPvPWins;
    }

    public TextView getTxtPlayerPvEWins() {
        return txtPlayerPvEWins;
    }

    public void setTxtPlayerName(TextView txtPlayerName) {
        this.txtPlayerName = txtPlayerName;
    }

    public void setTxtPlayerPvPWins(TextView txtPlayerPvPWins) {
        this.txtPlayerPvPWins = txtPlayerPvPWins;
    }

    public void setTxtPlayerPvEWins(TextView txtPlayerPvEWins) {
        this.txtPlayerPvEWins = txtPlayerPvEWins;
    }
    //endregion
}
