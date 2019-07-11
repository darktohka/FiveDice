package ro.bolyai.fivedice.gui.listview;


import android.widget.TextView;

public class ViewHolder {

    //region 1. Decl and Init
    private TextView txtPlayerName;

    private TextView txtPlayerPvPPoint;

    private TextView txtPlayerPvEPoint;

    //endregion

    //region 2. Constructor
    public ViewHolder(TextView txtPlayerName, TextView txtPlayerPvPPoint, TextView txtPlayerPvEPoint){
        this.txtPlayerName=txtPlayerName;
        this.txtPlayerPvPPoint=txtPlayerPvPPoint;
        this.txtPlayerPvEPoint=txtPlayerPvEPoint;
    }

    //endregion

    //region 3. Getters and Setters

    public TextView getTxtPlayerName() {
        return txtPlayerName;
    }
    public TextView getTxtPlayerPvPPoint() {
        return txtPlayerPvPPoint;
    }
    public TextView getTxtPlayerPvEPoint() {
        return txtPlayerPvEPoint;
    }

    public void setTxtPlayerName(TextView txtPlayerName) {
        this.txtPlayerName = txtPlayerName;
    }
    public void setTxtPlayerPvPPoint(TextView txtPlayerPvPPoint) {
        this.txtPlayerPvPPoint = txtPlayerPvPPoint;
    }
    public void setTxtPlayerPvEPoint(TextView txtPlayerPvEPoint) {
        this.txtPlayerPvEPoint = txtPlayerPvEPoint;
    }
    //endregion
}
