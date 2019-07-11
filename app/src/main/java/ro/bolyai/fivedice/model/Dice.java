package ro.bolyai.fivedice.model;

/**
 * Our Dice model.
 * <p>
 * The dice is responsible for holding its own score value.
 * Dice can also be locked, which means that during a re-roll,
 * their value can not be changed by the application.
 * <p>
 * This model only holds application data.
 * For GUI usage, use {@link ro.bolyai.fivedice.gui.model.GUIDice}.
 */
public class Dice extends Model {

    //region 1. Variables
    /**
     * The dice's score.
     * A score of 0 means a hidden dice.
     * Regular dice may have scores between 1 and 6.
     */
    private int iValue;

    /**
     * The dice's locked state.
     * Locked dice cannot be changed during a re-roll.
     */
    private boolean locked;
    //endregion

    //region 2. Constructors

    /**
     * Creates an empty {@link Dice} with default values.
     */
    public Dice() {
        this.iValue = Model.DEFAULT_INT_VALUE;
        this.locked = Model.DEFAULT_BOOL_VALUE;
    }
    //endregion

    //region 3. Getters and setters

    /**
     * Returns the value of the dice.
     *
     * @return iValue : int : The value of the dice.
     */
    public int getValue() {
        return iValue;
    }

    /**
     * Sets the value of the dice.
     *
     * @param value : int : The new value of the dice.
     */
    public void setValue(int value) {
        if (value >= 0 && value <= 6) {
            this.iValue = value;
        } else {
            this.iValue = 0;
        }
    }

    /**
     * Returns the locked state of the dice.
     *
     * @return locked : boolean : Returns true if the dice is locked, false otherwise.
     */
    public boolean isLocked() {
        return locked;
    }

    /**
     * Sets the locked state of the dice.
     *
     * @param locked : boolean : The new locked state of the dice.
     */
    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    /**
     * Rolls the dice, and assigns a new random score value.
     * Does nothing if the dice is currently locked.
     * See {@link Dice#isLocked()} for more information.
     */
    public void rollDice() {
        if (!isLocked()) {
            setValue((int) ((Math.random() * 6) + 1));
        }
    }

    /**
     * Initializes the dice.
     * This method should be ran sometime after initialization.
     */
    public void initialize() {
        // Do nothing.
    }

    /**
     * Checks whether the AI should lock this dice right now or not.
     * On the first roll, any 5s or 6s are kept. Then the first re-roll is used to change anything not kept.
     * After the first re-roll, any new 4s, 5s or 6s are kept. Anything not kept is re-rolled the second time.
     * The chance of getting all 6s and 5s on the first throw is 1/729. Individually, there is a 1/3th chance per dice of getting a 5 or 6.
     * For the first re-roll, I decided to stick with keeping 4s, 5s and 6s, instead of 3s, 4s, 5s and 6s.
     * Assuming no dice were kept from the initial roll, the chance of getting all 4s 5s and 6s is 1/64. If I included 3s that chance would be 1/11.
     * However, after testing I found that the best strategy is to go with the slightly lower, but acceptable, odds of 1/64 to keep only the 4s, 5s and 6s after the first re-roll.
     *
     * @param rerollsLeft : int : The current number of re-rolls left.
     * @return shouldLock : boolean : Returns true if the dice should be locked in the current round.
     */
    public boolean aiShouldLock(int rerollsLeft) {
        if (rerollsLeft < 2) {
            return iValue >= 4;
        } else {
            return iValue >= 5;
        }
    }
    //endregion
}