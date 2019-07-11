package ro.bolyai.fivedice.model;

public class Dice extends Model {

    //region 1. Variables
    private int iValue;
    private boolean locked;
    //endregion

    //region 2. Constructors
    public Dice() {
        this.iValue = Model.DEFAULT_INT_VALUE;
        this.locked = Model.DEFAULT_BOOL_VALUE;
    }
    //endregion

    //region 3. Getters and setters
    public int getValue() {
        return iValue;
    }

    public void setValue(int value) {
        this.iValue = value;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public void rollDice() {
        if (!isLocked()) {
            setValue((int) ((Math.random() * 6) + 1));
        }
    }

    public void initialize() {
        // Do nothing.
    }

    public boolean aiShouldLock(int rerollsLeft) {
        // On the first roll, any 5s or 6s are kept. Then the first re-roll is used to change anything not kept.
        // After the first re-roll, any new 4s, 5s or 6s are kept. Anything not kept is re-rolled the second time.
        // The chance of getting all 6s and 5s on the first throw is 1/729. Individually, there is a 1/3th chance per dice of getting a 5 or 6.
        // For the first re-roll, I decided to stick with keeping 4s, 5s and 6s, instead of 3s, 4s, 5s and 6s.
        // Assuming no dice were kept from the initial roll, the chance of getting all 4s 5s and 6s is 1/64. If I included 3s that chance would be 1/11.
        // However, after testing I found that the best strategy is to go with the slightly lower, but acceptable, odds of 1/64 to keep only the 4s, 5s and 6s after the first re-roll.

        if (rerollsLeft < 2) {
            return iValue >= 4;
        } else {
            return iValue >= 5;
        }
    }
    //endregion
}