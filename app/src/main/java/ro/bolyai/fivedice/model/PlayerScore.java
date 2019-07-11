package ro.bolyai.fivedice.model;


import ro.bolyai.fivedice.logic.database.DAOPlayerScore;

public class PlayerScore extends Model implements Comparable<PlayerScore> {

    //region 1. Declarations
    /**
     * The ID of the player score entry.
     */
    private int iId;

    /**
     * The name of the player.
     */
    private String strName;

    /**
     * The amount of PvP matches won.
     */
    private long lngWinsPvP;

    /**
     * The amount of PvE matches won.
     */
    private long lngWinsPvE;
    //endregion

    //region 2. Constructors

    /**
     * Creates an empty {@link PlayerScore} with default values.
     */
    public PlayerScore() {
        this.iId = Model.DEFAULT_INT_VALUE;
        this.strName = Model.DEFAULT_STR_VALUE;
        this.lngWinsPvP = Model.DEFAULT_INT_VALUE;
        this.lngWinsPvE = Model.DEFAULT_INT_VALUE;
    }

    /**
     * Creates a {@link PlayerScore} with given values.
     *
     * @param iId        : int : The ID of the player score entry.
     * @param name       : {@link String} : The name of the player.
     * @param lngWinsPvP : long : The amount of PvP matches won.
     * @param lngWinsPvE : long : The amount of PvE matches won.
     */
    public PlayerScore(int iId, String name, long lngWinsPvP, long lngWinsPvE) {
        this();

        this.iId = iId;
        this.strName = name;
        this.lngWinsPvP = lngWinsPvP;
        this.lngWinsPvE = lngWinsPvE;
    }
    //endregion

    //region 3. Getters and setters

    /**
     * Returns the ID of the player score from the database.
     *
     * @return iId : int : The ID of the player score.
     */
    public int getId() {
        return iId;
    }

    /**
     * Returns the name of the player.
     *
     * @return strName : {@link String} : The name of the player.
     */
    public String getName() {
        return strName;
    }

    /**
     * Returns the amount of PvE matches won.
     *
     * @return lngWinsPvE : long : The amount of PvE matches won.
     */
    public long getWinsPvE() {
        return lngWinsPvE;
    }

    /**
     * Returns the amount of PvP matches won.
     *
     * @return lngWinsPvP : long : The amount of PvP matches won.
     */
    public long getWinsPvP() {
        return lngWinsPvP;
    }

    /**
     * Sets the ID of the player score.
     *
     * @param iId : int : The new ID of the player score.
     */
    public void setId(int iId) {
        this.iId = iId;
    }

    /**
     * Sets the new name of the player score.
     *
     * @param strName : {@link String} : The new name of the player score.
     */
    public void setName(String strName) {
        this.strName = strName;
    }

    /**
     * Sets the new amount of PvE matches won.
     *
     * @param lngWinsPvE : long : The new amount of PvE matches won.
     */
    public void setWinsPvE(long lngWinsPvE) {
        this.lngWinsPvE = lngWinsPvE;
    }

    /**
     * Sets the new amount of PvP matches won.
     *
     * @param lngWinsPvP : long : The new amount of PvP matches won.
     */
    public void setWinsPvP(long lngWinsPvP) {
        this.lngWinsPvP = lngWinsPvP;
    }

    /**
     * Compares this object with the specified object for order.  Returns a
     * negative integer, zero, or a positive integer as this object is less
     * than, equal to, or greater than the specified object.
     *
     * @param o the object to be compared.
     * @return a negative integer, zero, or a positive integer as this object
     * is less than, equal to, or greater than the specified object.
     */
    @Override
    public int compareTo(PlayerScore o) {
        return new Long(o.getWinsPvE() + o.getWinsPvP()).compareTo(getWinsPvE() + getWinsPvP());
    }
    //endregion
}