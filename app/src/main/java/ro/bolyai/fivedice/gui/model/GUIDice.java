package ro.bolyai.fivedice.gui.model;

import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import ro.bolyai.fivedice.R;
import ro.bolyai.fivedice.model.Dice;

public class GUIDice extends Dice {

    //region 1. Declarations
    private ImageView imgDice;
    private Button btnKeep;
    //endregion

    //region 2. Constructors
    public GUIDice() {
        super();
    }

    public GUIDice(ImageView imgDice, Button btnKeep) {
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

    public void updateDiceImage() {
        if (hasImgDice()) {
            getImgDice().setImageResource(getBitmapForValue(getValue(), isLocked()));
        }
    }

    @Override
    public void setLocked(boolean locked) {
        super.setLocked(locked);

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

    @Override
    public void setValue(int value) {
        super.setValue(value);
        updateDiceImage();
    }

    @Override
    public void initialize() {
        super.initialize();

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLocked(!isLocked());
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
    //endregion

    //region 4. Static helper methods
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