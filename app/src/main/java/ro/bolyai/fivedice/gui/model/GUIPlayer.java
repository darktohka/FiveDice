package ro.bolyai.fivedice.gui.model;

import androidx.core.content.ContextCompat;
import android.util.TypedValue;
import android.widget.ProgressBar;
import android.widget.TextView;

import ro.bolyai.fivedice.R;
import ro.bolyai.fivedice.model.Player;

public class GUIPlayer extends Player {

    //region 1. Declarations
    /**
     * The {@link TextView} responsible for
     * showing the player's name.
     */
    private TextView txtPlayerName;

    /**
     * The {@link TextView} responsible for
     * showing the player's current score.
     */
    private TextView txtPlayerScore;

    /**
     * The {@link ProgressBar} responsible for
     * showing the player's relative score.
     */
    private ProgressBar barPlayer;
    //endregion

    //region 2. Constructors

    /**
     * Creates an empty {@link GUIPlayer} with default values.
     */
    public GUIPlayer() {
        super();
        this.txtPlayerName = null;
        this.txtPlayerScore = null;
        this.barPlayer = null;
    }

    /**
     * Creates a {@link GUIPlayer} with default values,
     * creating a link between the model and the user interface.
     *
     * @param txtPlayerName  : {@link TextView} : The player name text.
     * @param txtPlayerScore : {@link TextView} : The player score text.
     * @param barPlayer      : {@link ProgressBar} : The relative score progress bar.
     */
    public GUIPlayer(TextView txtPlayerName, TextView txtPlayerScore, ProgressBar barPlayer) {
        this();
        this.txtPlayerName = txtPlayerName;
        this.txtPlayerScore = txtPlayerScore;
        this.barPlayer = barPlayer;
    }

    /**
     * Creates a {@link GUIPlayer}, creating a link between
     * the model and the user interface.
     *
     * @param txtPlayerName  : {@link TextView} : The player name text.
     * @param txtPlayerScore : {@link TextView} : The player score text.
     * @param barPlayer      : {@link ProgressBar} : The relative score progress bar.
     * @param name           :{@link String} : The player's current name.
     * @param score          : int : The player's current score.
     */
    public GUIPlayer(TextView txtPlayerName, TextView txtPlayerScore, ProgressBar barPlayer, String name, int score) {
        super(name, score);
        this.txtPlayerName = txtPlayerName;
        this.txtPlayerScore = txtPlayerScore;
        this.barPlayer = barPlayer;
    }
    //endregion

    //region 3. Getters and setters

    /**
     * Returns the {@link TextView} responsible
     * for showing the player's name.
     *
     * @return txtPlayerName : {@link TextView} : The player name text.
     */
    public TextView getTxtPlayerName() {
        return txtPlayerName;
    }

    /**
     * Returns the {@link TextView} responsible
     * for showing the player's current score.
     *
     * @return txtPlayerScroe : {@link TextView} : The player score text.
     */
    public TextView getTxtPlayerScore() {
        return txtPlayerScore;
    }

    /**
     * Returns the {@link ProgressBar} responsible
     * for showing the player's relative score.
     *
     * @return barPlayer : {@link ProgressBar} : The relative score progress bar.
     */
    public ProgressBar getBarPlayer() {
        return barPlayer;
    }

    /**
     * Sets the new score of the {@link Player}.
     *
     * @param score : int : The new score of the player.
     */
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

    /**
     * Activates the current player.
     * <p>
     * This method is called when it's the player's
     * turn in the current game.
     */
    @Override
    public void setActivePlayer() {
        txtPlayerName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        txtPlayerName.setTextColor(ContextCompat.getColor(txtPlayerName.getContext(), R.color.colorKeepInactive));
    }

    /**
     * Deactivates the current player.
     * <p>
     * This method is called when the player's
     * turn is over in the current game.
     */
    @Override
    public void setInactivePlayer() {
        txtPlayerName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        txtPlayerName.setTextColor(ContextCompat.getColor(txtPlayerName.getContext(), android.R.color.black));
    }

    /**
     * Initializes the player.
     * This method should be called after initialization.
     *
     * @param targetScore : int : The target score of the current game.
     */
    @Override
    public void initialize(int targetScore) {
        super.initialize(targetScore);

        barPlayer.setMax(targetScore);
        txtPlayerName.setText(getName());
        setScore(getScore());
    }
    //endregion
}