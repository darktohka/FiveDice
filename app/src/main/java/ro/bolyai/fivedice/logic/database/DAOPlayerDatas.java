package ro.bolyai.fivedice.logic.database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import ro.bolyai.fivedice.model.PlayerDatas;

public class DAOPlayerDatas extends ASQLiteKeyWords {
    //region 0. Constants
    private static final String TBL_NAME="tblPlayerDatas";

    private static final String COL_NAME_ID="_id";

    private static final String COL_NAME_PLAYER_NAME="playerName";

    private static final String COL_NAME_PLAYER_PVP_WINS="playerPvPWins";

    private static final String COL_NAME_PLAYER_PVE_WINS="playerPvEWins";
    //endregion

    //region 1. Decl and Init
    //endregion

    //region 2. Constructor
    public DAOPlayerDatas(){
        //nothing to do
    }
    //endregion

    //region 3. Create Table Statement
    public String getCreateTableStatement(){
        return CREATE_TBL + TBL_NAME
                + CHAR_OPEN_BRACKET
                + COL_NAME_ID + PRIMARY_KEY_AUTO_INCREMENT_INC_COMMA
                + COL_NAME_PLAYER_NAME + DATA_TYPE_TEXT_INC_COMMA
                + COL_NAME_PLAYER_PVP_WINS + DATA_TYPE_TEXT_INC_COMMA
                + COL_NAME_PLAYER_PVE_WINS + DATA_TYPE_TEXT
                +CHAR_CLOSE_BRACKET_SEMICOLON;
    }
    //endregion

    //region 4. Read Operations
    public List<PlayerDatas> getCustomerByIdFromDbTable(SQLiteDatabase db) {
        List<PlayerDatas> allPlayerDatasFromDbTable= new ArrayList<>();

        try{
            Cursor cResultSet= db.query(TBL_NAME,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null);

            if(cResultSet!=null){
                while(cResultSet.moveToFirst()){
                    PlayerDatas playerDatasFromDbTable= this.getCreateTableStatement(cResultSet);

                    allPlayerDatasFromDbTable.add(playerDatasFromDbTable);
                }
            }


        }catch (SQLiteException sqlEx){
            Log.e(TBL_NAME, sqlEx.getMessage() + "\n" + sqlEx.getStackTrace().toString());
        }
        finally {
            db.close();
        }

        return allPlayerDatasFromDbTable;

    }

    //endregion

    //region 5. Insert Operations

    //endregion

    //region 6. Update Operations

    //endregion

    //region 7. ContentValues and Result set Handling

    private PlayerDatas getCreateTableStatement(Cursor cResultSet){
        PlayerDatas playerDatasFromDbTable= new PlayerDatas();

        int indexId=cResultSet.getColumnIndex(COL_NAME_ID);
        int indexPlayerName=cResultSet.getColumnIndex(COL_NAME_PLAYER_NAME);
        int indexPlayerPvPWins=cResultSet.getColumnIndex(COL_NAME_PLAYER_PVP_WINS);
        int indexPlayerPvEWins=cResultSet.getColumnIndex(COL_NAME_PLAYER_PVE_WINS);

        playerDatasFromDbTable.setId(cResultSet.getInt(indexId));
        playerDatasFromDbTable.setWinsPvP(cResultSet.getInt(indexPlayerPvPWins));
        playerDatasFromDbTable.setWinsPvE(cResultSet.getInt(indexPlayerPvEWins));

        playerDatasFromDbTable.setName(cResultSet.getString(indexPlayerName));

        return playerDatasFromDbTable;
    }

    //endregion
}
