package ro.bolyai.fivedice.model;

/**
 * The application's Player data.
 * Each player has a name and a score.
 * The score represents the Player's progress in the game.
 * <p>
 * This model only holds application data.
 * For GUI usage, use {@link ro.bolyai.fivedice.gui.model.GUIPlayer}.
 */
public class Player extends Model {

    //region 1. Declarations
    /**
     * The current name of the player.
     */
    private String name;

    /**
     * The current score of the player.
     */
    private int score;

    /**
     * The target score of the game.
     * Used to show the relative progress of the player.
     */
    private int targetScore;
    //endregion

    //region 2. Constructors

    /**
     * Creates an empty {@link Player} with default values.
     */
    public Player() {
        this.name = Model.DEFAULT_STR_VALUE;
        this.score = Model.DEFAULT_INT_VALUE;
        this.targetScore = Model.DEFAULT_INT_VALUE;
    }

    /**
     * Creates a {@link Player} with the given name and initial score.
     *
     * @param name  : {@link String} : The name of the player.
     * @param score : int : The initial score of the player.
     */
    public Player(String name, int score) {
        this();
        this.name = name;
        this.score = score;
    }
    //endregion

    //region 3. Getters and setters

    /**
     * Returns the name of the {@link Player}.
     *
     * @return name : {@link String} : The current name of the player.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the {@link Player}.
     *
     * @param name : {@link String} : The new name of the player.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the current score of the {@link Player}.
     *
     * @return score : int : The current score of the player.
     */
    public int getScore() {
        return score;
    }

    /**
     * Sets the new score of the {@link Player}.
     *
     * @param score : int : The new score of the player.
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * Increments the score of the {@link Player}.
     *
     * @param score : int : The incrementation of the score, must be positive.
     */
    public void addScore(int score) {
        if (score > 0) {
            this.setScore(this.score + score);
        }
    }

    /**
     * Returns the target score of the current game.
     *
     * @return targetScore : int : The target score of the game.
     */
    public int getTargetScore() {
        return targetScore;
    }

    /**
     * Sets the new target score of the current game.
     *
     * @param targetScore : int : The new target score of the game.
     */
    public void setTargetScore(int targetScore) {
        this.targetScore = targetScore;
    }
    //endregion

    //region 4. Methods

    /**
     * Activates the current player.
     * <p>
     * This method is called when it's the player's
     * turn in the current game.
     */
    public void setActivePlayer() {
        throw new UnsupportedOperationException("This operation has not been implemented yet.");
    }

    /**
     * Deactivates the current player.
     * <p>
     * This method is called when the player's
     * turn is over in the current game.
     */
    public void setInactivePlayer() {
        throw new UnsupportedOperationException("This operation has not been implemented yet.");
    }

    /**
     * Initializes the player.
     * This method should be called after initialization.
     *
     * @param targetScore : int : The target score of the current game.
     */
    public void initialize(int targetScore) {
        this.targetScore = targetScore;
    }
    //endregion
}