package ro.bolyai.fivedice.gui.model;

import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import ro.bolyai.fivedice.R;
import ro.bolyai.fivedice.model.Dice;

public class GUIDice extends Dice {

    //region 1. Declarations
    /**
     * The {@link ImageView} responsible for
     * showing the dice score to the players.
     */
    private ImageView imgDice;

    /**
     * The {@link Button} responsible for
     * allowing the player to keep the dice.
     */
    private Button btnKeep;
    //endregion

    //region 2. Constructors

    /**
     * Creates an empty {@link GUIDice} with default values.
     */
    public GUIDice() {
        super();
    }

    /**
     * Creates a new {@link GUIDice}, creating a link
     * between the model and the user interface.
     *
     * @param imgDice : {@link ImageView} : The image responsible for showing the dice vscore.
     * @param btnKeep : {@link Button} : The button responsible for keeping the dice.
     */
    public GUIDice(ImageView imgDice, Button btnKeep) {
        this();
        this.imgDice = imgDice;
        this.btnKeep = btnKeep;
    }
    //endregion

    //region 3. Getters and setters

    /**
     * Returns the {@link ImageView} responsible for the dice score.
     *
     * @return imgDice : {@link ImageView} : The dice score image.
     */
    public ImageView getImgDice() {
        return imgDice;
    }

    /**
     * Returns whether the {@link ImageView} responsible for the
     * dice score has been specified.
     *
     * @return hasImgDice : boolean : True if the dice image is set.
     */
    public boolean hasImgDice() {
        return this.imgDice != null;
    }

    /**
     * Returns the {@link Button} responsible for keeping the dice.
     *
     * @return btnKeep : {@link Button} : The keep button.
     */
    public Button getBtnKeep() {
        return btnKeep;
    }

    /**
     * Returns whether the {@link Button} responsible for keeping
     * the dice.
     *
     * @return hasBtnKeep : boolean : True if the keep button is set.
     */
    public boolean hasBtnKeep() {
        return this.btnKeep != null;
    }

    /**
     * Sets the locked state of the dice.
     *
     * @param locked : boolean : The new locked state of the dice.
     */
    @Override
    public void setLocked(boolean locked) {
        super.setLocked(locked);

        // Update the keep button state.
        // We need to set the color and the text,
        // according to the locked state.
        if (hasBtnKeep()) {
            int colorId = R.color.colorKeepInactive;
            int nameId = R.string.keep_btn_discard;

            if (locked) {
                colorId = R.color.colorKeepActive;
                nameId = R.string.btn_keep;
            }

            btnKeep.setBackgroundColor(ContextCompat.getColor(btnKeep.getContext(), colorId));
            btnKeep.setText(btnKeep.getContext().getString(nameId));
        }

        // Also update the dice image.
        updateDiceImage();
    }

    /**
     * Sets the value of the dice.
     *
     * @param value : int : The new value of the dice.
     */
    @Override
    public void setValue(int value) {
        super.setValue(value);
        updateDiceImage();
    }

    /**
     * Initializes the dice.
     * This method should be ran sometime after initialization.
     */
    @Override
    public void initialize() {
        super.initialize();

        // If the dice or the keep button
        // is pressed, the lock state
        // should be toggled immediately.
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLocked(!isLocked());
            }
        };

        imgDice.setOnClickListener(listener);
        btnKeep.setOnClickListener(listener);
    }
    //endregion

    //region 3. Convenience GUI methods

    /**
     * Updates the dice image based on the
     * dice's current score value.
     */
    public void updateDiceImage() {
        if (hasImgDice()) {
            getImgDice().setImageResource(getBitmapForValue(getValue(), isLocked()));
        }
    }

    /**
     * Hides the keep button from the player.
     */
    public void hideKeepButton() {
        btnKeep.setVisibility(View.GONE);
        imgDice.setEnabled(false);
    }

    /**
     * Shows the keep button to the player.
     */
    public void showKeepButton() {
        btnKeep.setVisibility(View.VISIBLE);
        imgDice.setEnabled(true);
    }

    /**
     * Sets the enabled state of the keep button.
     * The keep button should be disabled during rolls.
     *
     * @param enabled : boolean : The enabled state of the keep button.
     */
    public void setEnabled(boolean enabled) {
        btnKeep.setEnabled(enabled);
        imgDice.setEnabled(enabled);
    }
    //endregion

    //region 4. Static helper methods

    /**
     * Returns a drawable resource ID based on the score
     * and locked state of a {@link Dice}.
     *
     * @param value  : int : The value of the dice.
     * @param locked : boolean : The locked state of the dice.
     * @return resourceId : int : A drawable resource ID.
     */
    public static int getBitmapForValue(int value, boolean locked) {
        if (locked) {
            switch (value) {
                case 1:
                    return R.drawable.dice_1l;
                case 2:
                    return R.drawable.dice_2l;
                case 3:
                    return R.drawable.dice_3l;
                case 4:
                    return R.drawable.dice_4l;
                case 5:
                    return R.drawable.dice_5l;
                case 6:
                    return R.drawable.dice_6l;
                default:
                    return R.drawable.dice_0;
            }
        } else {
            switch (value) {
                case 1:
                    return R.drawable.dice_1;
                case 2:
                    return R.drawable.dice_2;
                case 3:
                    return R.drawable.dice_3;
                case 4:
                    return R.drawable.dice_4;
                case 5:
                    return R.drawable.dice_5;
                case 6:
                    return R.drawable.dice_6;
                default:
                    return R.drawable.dice_0;
            }
        }
    }
    //endregion
}