package ro.bolyai.fivedice.gui.listview;


import android.widget.TextView;

public class ViewHolder {

    //region 1. Decl and Init
    private TextView txtPlayerName;

    private TextView txtPlayerPvPWins;

    private TextView txtPlayerPvEWins;

    //endregion

    //region 2. Constructor
    public ViewHolder(TextView txtPlayerName, TextView txtPlayerPvPWins, TextView txtPlayerPvEWins){
        this.txtPlayerName=txtPlayerName;
        this.txtPlayerPvPWins =txtPlayerPvPWins;
        this.txtPlayerPvEWins =txtPlayerPvEWins;
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
