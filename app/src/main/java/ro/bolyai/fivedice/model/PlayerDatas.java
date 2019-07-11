package ro.bolyai.fivedice.model;


public class PlayerDatas {

    //region 0. Constants
    private static final int DEF_INT_VALUE=0;
    //endregion


    //region 1. Decl and Init
    private int iId;

    private String strName;
    private long lngWinsPvP;
    private long lngWinsPvE;
    //endregion

    //region 2. Constructor
    public PlayerDatas(String name){
        this.strName=name;
        this.lngWinsPvP=DEF_INT_VALUE;
        this.lngWinsPvE=DEF_INT_VALUE;
    }
    //endregion

    //region 3. Getters and Setters

    public int getId() {
        return iId;
    }
    public String getName() {
        return strName;
    }
    public long getWinsPvE() {
        return lngWinsPvE;
    }
    public long getWinsPvP() {
        return lngWinsPvP;
    }

    public void setId(int iId) {
        this.iId = iId;
    }
    public void setName(String strName) {
        this.strName = strName;
    }
    public void setWinsPvE(long lngWinsPvE) {
        this.lngWinsPvE = lngWinsPvE;
    }
    public void setWinsPvP(long lngWinsPvP) {
        this.lngWinsPvP = lngWinsPvP;
    }
    //endregion

}
