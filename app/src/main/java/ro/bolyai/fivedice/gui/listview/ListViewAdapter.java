package ro.bolyai.fivedice.gui.listview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import ro.bolyai.fivedice.R;
import ro.bolyai.fivedice.model.PlayerDatas;
import ro.bolyai.fivedice.testdata.TestData;

public class ListViewAdapter extends BaseAdapter {
    //region 0. Constants

    //endregion

    //region 1. Attributes
    private Context context;
    private LayoutInflater layoutInflater;
    private List<PlayerDatas> allPlayerDatas;
    //endregion

    //region 2. Constructors
    public ListViewAdapter(Context context){
        //Set the context
        this.context=context;

        //Generate the LayoutInflater
        this.layoutInflater=LayoutInflater.from(this.context);

        //TODO Link together with the Database
        this.allPlayerDatas=TestData.getTestPlayerDatas();
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
        return allPlayerDatas.size();
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
        return allPlayerDatas.get(position);
    }

    /**
     * Get the row id associated with the specified position in the list.
     *
     * @param position The position of the item within the adapter's data set whose row id we want.
     * @return The id of the item at the specified position.
     */
    @Override
    public long getItemId(int position) {
        return (long) allPlayerDatas.get(position).getId();
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
     * @param position    The position of the item within the adapter's data set of the item whose view
     *                    we want.
     * @param currentListViewItem The old view to reuse, if possible. Note: You should check that this view
     *                    is non-null and of an appropriate type before using. If it is not possible to convert
     *                    this view to display the correct data, this method can create a new view.
     *                    Heterogeneous lists can specify their number of view types, so that this View is
     *                    always of the right type (see {@link #getViewTypeCount()} and
     *                    {@link #getItemViewType(int)}).
     * @param parentListView      The parent that this view will eventually be attached to
     * @return A View corresponding to the data at the specified position.
     */
    @Override
    public View getView(int position, View currentListViewItem, ViewGroup parentListView) {
        //1. Declare ViewHolder
        ViewHolder viewHolder=null;

        if(currentListViewItem==null){
            currentListViewItem=layoutInflater.inflate(R.layout.list_view_item_layout, parentListView, false);

            TextView txtPlayerName=currentListViewItem.findViewById(R.id.txtPlayerName);
            TextView txtPlayerPvPPoint=currentListViewItem.findViewById(R.id.txtPlayerPvPPoint);
            TextView txtPlayerPvEPoint=currentListViewItem.findViewById(R.id.txtPlayerPvEPoint);

            viewHolder=new ViewHolder(txtPlayerName, txtPlayerPvPPoint, txtPlayerPvEPoint);

            currentListViewItem.setTag(viewHolder);
        }else{
            viewHolder= (ViewHolder) currentListViewItem.getTag();
        }

        PlayerDatas playerDatas=(PlayerDatas) allPlayerDatas.get(position);

        viewHolder.getTxtPlayerName().setText(playerDatas.getName());
        viewHolder.getTxtPlayerPvPPoint().setText("PvP: "+ playerDatas.getWinsPvP());
        viewHolder.getTxtPlayerPvEPoint().setText("PvE: "+playerDatas.getWinsPvE());

        return currentListViewItem;
    }
    //endregion

}
