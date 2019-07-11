package ro.bolyai.fivedice.model;

import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import ro.bolyai.fivedice.R;

public class Dice extends Model {

    //region 1. Variables
    private ImageView imgDice;
    private Button btnKeep;

    private int value;
    private boolean locked;
    //endregion

    //region 2. Constructors
    public Dice() {
        this.imgDice = null;
        this.btnKeep = null;
        this.value = Model.DEFAULT_INT_VALUE;
        this.locked = false;
    }

    public Dice(ImageView imgDice, Button btnKeep) {
        this();
        this.imgDice = imgDice;
        this.btnKeep = btnKeep;
    }
    //endregion

    //region 3. Getters and setters
    public ImageView getImgDice() {
        return imgDice;
    }

    public void setImgDice(ImageView imgDice) {
        this.imgDice = imgDice;
    }

    public boolean hasImgDice() {
        return this.imgDice != null;
    }

    public Button getBtnKeep() {
        return btnKeep;
    }

    public void setBtnKeep(Button btnKeep) {
        this.btnKeep = btnKeep;
    }

    public boolean hasBtnKeep() {
        return this.btnKeep != null;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
        updateDiceImage();
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;

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

        updateDiceImage();
    }

    public void rollDice() {
        if (isLocked()) {
            return;
        }

        setValue((int) ((Math.random() * 6) + 1));
    }

    public void updateDiceImage() {
        if (hasImgDice()) {
            getImgDice().setImageResource(getBitmapForValue(value, locked));
        }
    }

    public void initialize() {
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLocked(!locked);
            }
        };

        imgDice.setOnClickListener(listener);
        btnKeep.setOnClickListener(listener);
    }

    public void hideKeepButton() {
        btnKeep.setVisibility(View.GONE);
        imgDice.setEnabled(false);
    }

    public void showKeepButton() {
        btnKeep.setVisibility(View.VISIBLE);
        imgDice.setEnabled(true);
    }

    public void setEnabled(boolean enabled) {
        btnKeep.setEnabled(enabled);
        imgDice.setEnabled(enabled);
    }

    public boolean aiShouldLock(int rerollsLeft) {
        // On the first roll, any 5s or 6s are kept. Then the first re-roll is used to change anything not kept.
        // After the first re-roll, any new 4s, 5s or 6s are kept. Anything not kept is re-rolled the second time.
        // The chance of getting all 6s and 5s on the first throw is 1/729. Individually, there is a 1/3th chance per dice of getting a 5 or 6.
        // For the first re-roll, I decided to stick with keeping 4s, 5s and 6s, instead of 3s, 4s, 5s and 6s.
        // Assuming no dice were kept from the initial roll, the chance of getting all 4s 5s and 6s is 1/64. If I included 3s that chance would be 1/11.
        // However, after testing I found that the best strategy is to go with the slightly lower, but acceptable, odds of 1/64 to keep only the 4s, 5s and 6s after the first re-roll.

        if (rerollsLeft < 2) {
            return value == 4 || value == 5 || value == 6;
        } else {
            return value == 5 || value == 6;
        }
    }
    //endregion

    //region 4. Static methods
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
