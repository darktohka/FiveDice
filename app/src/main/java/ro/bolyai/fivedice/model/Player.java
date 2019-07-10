package ro.bolyai.fivedice.model;

import android.widget.ProgressBar;
import android.widget.TextView;

import ro.bolyai.fivedice.R;

public class Player extends Model {

    //region 1. Declarations
    private TextView txtPlayerName;
    private TextView txtPlayerScore;
    private ProgressBar barPlayer;

    private String name;
    private int score;

    private int targetScore;
    //endregion

    //region 2. Constructors
    public Player() {
        this.txtPlayerName = null;
        this.txtPlayerScore = null;
        this.barPlayer = null;
        this.name = Model.DEFAULT_STR_VALUE;
        this.score = Model.DEFAULT_INT_VALUE;
        this.targetScore = Model.DEFAULT_INT_VALUE;
    }

    public Player(TextView txtPlayerName, TextView txtPlayerScore, ProgressBar barPlayer, String name, int score) {
        this.txtPlayerName = txtPlayerName;
        this.txtPlayerScore = txtPlayerScore;
        this.barPlayer = barPlayer;
        this.name = name;
        this.score = score;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;

        if (barPlayer != null) {
            barPlayer.setProgress(score);
        }
        if (txtPlayerScore != null) {
            txtPlayerScore.setText(String.format(.getString(R.string.player_points_text), score, targetScore));
        }
    }

    public void addScore(int score) {
        this.setScore(this.score + score);
    }
    //endregion

    //region 4. Methods
    public void initialize(int targetScore) {
        this.targetScore = targetScore;

        barPlayer.setMax(targetScore);
        barPlayer.setProgress(score);
        txtPlayerName.setText(name);
    }
    //endregion
}