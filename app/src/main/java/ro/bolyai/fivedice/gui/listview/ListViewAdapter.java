package ro.bolyai.fivedice.gui.listview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import ro.bolyai.fivedice.R;
import ro.bolyai.fivedice.logic.database.DbManager;
import ro.bolyai.fivedice.model.PlayerScore;

/**
 * Generates so many graphical ListViewItems as there
 * is space in the GUI.
 * <p>
 * The items are generated based on the main data set (which in our
 * case is a {@link java.util.List} of
 * {@link ro.bolyai.fivedice.model.Player}s and their scores).
 */

public class ListViewAdapter extends BaseAdapter {
    //region 0. Constants

    //endregion

    //region 1. Attributes

    /**
     * Is used to generate the
     * {@link LayoutInflater}, which
     * will generate the ListViewItems
     * in the function {@link ListViewAdapter#getView(int, View, ViewGroup)};
     * <p>
     * Working references
     */
    private Context context;

    /**
     * Generates the specified single
     * ListViewItem in the function {@link ListViewAdapter#getView(int, View, ViewGroup)}.
     * based on the res/layout/list_view_item_layout.xml .
     */
    private LayoutInflater layoutInflater;

    /**
     * Bulds the core of the ListViewAdapter
     * and is generated in the default constructos,
     * and used in the Listfunctions:<br>
     * <ul>
     * * <li>{@link ListViewAdapter#getCount()}</li>
     * * <li>{@link ListViewAdapter#getItem(int)}</li>
     * * <li>{@link ListViewAdapter#getItemId(int)}</li>
     * * </ul>
     * Its integrated in the List function of the Adapter, so
     * that the Adapter always knows the state anc content of the
     * List and its size.
     * <p>
     * and the {@link ListViewAdapter#getView(int, View, ViewGroup)}.
     */
    private List<PlayerScore> allPlayerScores;
    //endregion

    //region 2. Constructors

    /**
     * Default COnstructor needs the Context(currentActivity: {@link ro.bolyai.fivedice.gui.LeaderboardActivity})
     * to generate the {@link LayoutInflater}, so that he can be used to generate the ListViewItems
     * in the function {@link ListViewAdapter#getView(int, View, ViewGroup)}.
     *
     * @param context : {@link Context} : {@link ro.bolyai.fivedice.gui.LeaderboardActivity}
     */
    public ListViewAdapter(Context context) {
        //Set the context
        this.context = context;

        //Generate the LayoutInflater
        this.layoutInflater = LayoutInflater.from(this.context);

        //Link together with the Database
        this.allPlayerScores =DbManager.getInstance(this.context).getAllPlayerScores();
    }
    //endregion

    //region 3. List functions

    /**
     * How many items are in the data set represented by this Adapter.
     *
     * @return Count of items.
     */
    @Override
    public int getCount() {
        return allPlayerScores.size();
    }

    /**
     * Get the data item associated with the specified position in the data set.
     *
     * @param position Position of the item whose data we want within the adapter's
     *                 data set.
     * @return The data at the specified position.
     */
    @Override
    public Object getItem(int position) {
        return allPlayerScores.get(position);
    }

    /**
     * Get the row id associated with the specified position in the list.
     *
     * @param position The position of the item within the adapter's data set whose row id we want.
     * @return The id of the item at the specified position.
     */
    @Override
    public long getItemId(int position) {
        return (long) allPlayerScores.get(position).getId();
    }

    //endregion

    //region 4. ListViewItem handling

    /**
     * Get a View that displays the data at the specified position in the data set. You can either
     * create a View manually or inflate it from an XML layout file. When the View is inflated, the
     * parent View (GridView, ListView...) will apply default layout parameters unless you use
     * {@link LayoutInflater#inflate(int, ViewGroup, boolean)}
     * to specify a root view and to prevent attachment to the root.
     *
     * @param position            The position of the item within the adapter's data set of the item whose view
     *                            we want.
     * @param currentListViewItem The old view to reuse, if possible. Note: You should check that this view
     *                            is non-null and of an appropriate type before using. If it is not possible to convert
     *                            this view to display the correct data, this method can create a new view.
     *                            Heterogeneous lists can specify their number of view types, so that this View is
     *                            always of the right type (see {@link #getViewTypeCount()} and
     *                            {@link #getItemViewType(int)}).
     * @param parentListView      The parent that this view will eventually be attached to
     * @return A View corresponding to the data at the specified position.
     */
    @Override
    public View getView(int position, View currentListViewItem, ViewGroup parentListView) {
        //1. Declare ViewHolder
        ViewHolder viewHolder = null;

        if (currentListViewItem == null) {
            currentListViewItem = layoutInflater.inflate(R.layout.list_view_item_layout, parentListView, false);

            TextView txtPlayerName     = currentListViewItem.findViewById(R.id.txtPlayerName);
            TextView txtPlayerPvPWins = currentListViewItem.findViewById(R.id.txtPlayerPvPWins);
            TextView txtPlayerPvEWins = currentListViewItem.findViewById(R.id.txtPlayerPvEWins);

            viewHolder = new ViewHolder(txtPlayerName, txtPlayerPvPWins, txtPlayerPvEWins);

            currentListViewItem.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) currentListViewItem.getTag();
        }

        PlayerScore playerScore = (PlayerScore) allPlayerScores.get(position);

        viewHolder.getTxtPlayerName().setText(playerScore.getName());
        viewHolder.getTxtPlayerPvPWins().setText("PvP: " + playerScore.getWinsPvP());
        viewHolder.getTxtPlayerPvEWins().setText("PvE: " + playerScore.getWinsPvE());

        return currentListViewItem;
    }
    //endregion

}
