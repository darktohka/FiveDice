package ro.bolyai.fivedice.model;

public class Player extends Model {

    //region 1. Declarations
    private String name;
    private int score;

    private int targetScore;
    //endregion

    //region 2. Constructors
    public Player() {
        this.name = Model.DEFAULT_STR_VALUE;
        this.score = Model.DEFAULT_INT_VALUE;
        this.targetScore = Model.DEFAULT_INT_VALUE;
    }

    public Player(String name, int score) {
        this();
        this.name = name;
        this.score = score;
    }
    //endregion

    //region 3. Getters and setters
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
    }

    public void addScore(int score) {
        this.setScore(this.score + score);
    }

    public int getTargetScore() {
        return targetScore;
    }

    public void setTargetScore(int targetScore) {
        this.targetScore = targetScore;
    }
    //endregion

    //region 4. Methods
    public void setActivePlayer() {
        throw new UnsupportedOperationException("This operation has not been implemented yet.");
    }

    public void setInactivePlayer() {
        throw new UnsupportedOperationException("This operation has not been implemented yet.");
    }

    public void initialize(int targetScore) {
        this.targetScore = targetScore;
    }
    //endregion
}