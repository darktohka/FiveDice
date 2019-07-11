package ro.bolyai.fivedice.gui.model;

import android.support.v4.content.ContextCompat;
import android.util.TypedValue;
import android.widget.ProgressBar;
import android.widget.TextView;

import ro.bolyai.fivedice.R;
import ro.bolyai.fivedice.model.Player;

public class GUIPlayer extends Player {

    //region 1. Declarations
    private TextView txtPlayerName;
    private TextView txtPlayerScore;
    private ProgressBar barPlayer;
    //endregion

    //region 2. Constructors
    public GUIPlayer() {
        super();
        this.txtPlayerName = null;
        this.txtPlayerScore = null;
        this.barPlayer = null;
    }

    public GUIPlayer(TextView txtPlayerName, TextView txtPlayerScore, ProgressBar barPlayer) {
        this();
        this.txtPlayerName = txtPlayerName;
        this.txtPlayerScore = txtPlayerScore;
        this.barPlayer = barPlayer;
    }

    public GUIPlayer(TextView txtPlayerName, TextView txtPlayerScore, ProgressBar barPlayer, String name, int score) {
        super(name, score);
        this.txtPlayerName = txtPlayerName;
        this.txtPlayerScore = txtPlayerScore;
        this.barPlayer = barPlayer;
    }
    //endregion

    //region 3. Getters and setters
    public TextView getTxtPlayerName() {
        return txtPlayerName;
    }

    public void setTxtPlayerName(TextView txtPlayerName) {
        this.txtPlayerName = txtPlayerName;
    }

    public TextView getTxtPlayerScore() {
        return txtPlayerScore;
    }

    public void setTxtPlayerScore(TextView txtPlayerScore) {
        this.txtPlayerScore = txtPlayerScore;
    }

    public ProgressBar getBarPlayer() {
        return barPlayer;
    }

    public void setBarPlayer(ProgressBar barPlayer) {
        this.barPlayer = barPlayer;
    }

    @Override
    public void setScore(int score) {
        super.setScore(score);

        if (barPlayer != null) {
            barPlayer.setProgress(score);
        }

        if (txtPlayerScore != null) {
            txtPlayerScore.setText(String.format(txtPlayerName.getContext().getString(R.string.player_points_text), score, getTargetScore()));
        }
    }

    @Override
    public void setActivePlayer() {
        txtPlayerName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        txtPlayerName.setTextColor(ContextCompat.getColor(txtPlayerName.getContext(), R.color.colorKeepInactive));
    }

    @Override
    public void setInactivePlayer() {
        txtPlayerName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        txtPlayerName.setTextColor(ContextCompat.getColor(txtPlayerName.getContext(), android.R.color.black));
    }

    @Override
    public void initialize(int targetScore) {
        super.initialize(targetScore);

        barPlayer.setMax(targetScore);
        txtPlayerName.setText(getName());
        setScore(getScore());
    }
    //endregion
}