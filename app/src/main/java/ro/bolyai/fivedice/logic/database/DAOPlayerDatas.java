package ro.bolyai.fivedice.logic.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import ro.bolyai.fivedice.model.Player;
import ro.bolyai.fivedice.model.PlayerDatas;

/**
 * [D]ata [A]ccess [O]bject for the table tblPlayerDatas
 * in the local SQLite3 Database. It implements
 * all the needed Information such as:<br>
 * <br>the<br>
 * Tablename : {@link DAOPlayerDatas#TBL_NAME}<br>
 * <br>the<br>
 * Columnnames:
 * <ul>
 * <li>{@link DAOPlayerDatas#COL_NAME_ID}</li>
 * <li>{@link DAOPlayerDatas#COL_NAME_PLAYER_NAME}</li>
 * * <li>{@link DAOPlayerDatas#COL_NAME_PLAYER_PVP_WINS}</li>
 * * <li>{@link DAOPlayerDatas#COL_NAME_PLAYER_PVE_WINS}</li>
 * </ul>
 * <br> and the <br>CRUD Operations:
 */

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
    public List<PlayerDatas> getPlayerDatasByIdFromDbTable(SQLiteDatabase db) {
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
                    PlayerDatas playerDatasFromDbTable= this.getPlayerDAtasTableStatement(cResultSet);

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

    @Nullable
    public PlayerDatas getPlayerDatasByIdFromDbTable(SQLiteDatabase db, int iId){
        PlayerDatas playerDatasFromTable=null;

        try{
            String   strWhereClause = COL_NAME_ID + EQUALS_OPERATOR_INC_PLACE_HOLDER;
            String[] strWhereArgs   = {String.valueOf(iId)};

            Cursor cResultSet = db.query(TBL_NAME,
                    null,
                    strWhereClause,
                    strWhereArgs,
                    null,
                    null,
                    null
            );

            if (cResultSet != null) {

                //3. Check the data in the result set
                if (cResultSet.moveToFirst()) {

                    //4. Extract the data into a Customer
                    playerDatasFromTable = this.getPlayerDAtasTableStatement(cResultSet);
                } else {
                    Log.d(TBL_NAME, "No record with the _id: " + iId + " found");
                }

            }

        }catch (SQLException sqlEx){
            Log.e(TBL_NAME, sqlEx.getMessage() + "\n" + sqlEx.getStackTrace().toString());
        }finally {
            db.close();
        }

        return playerDatasFromTable;
    }

    //endregion

    //region 5. Insert Operations
    public long insertAllPlayerDatasIntoFromTable(SQLiteDatabase db, @NonNull List<PlayerDatas> allPlayerDatasToInsert){
        long rowId = -1L;
        if(!allPlayerDatasToInsert.isEmpty()){
            try{

                for(PlayerDatas pd : allPlayerDatasToInsert){
                    ContentValues cvPlayerDatas=this.getContentValuesFromPlayerDatas(pd);

                    rowId=db.insert(TBL_NAME, null, cvPlayerDatas);
                }
            }catch (SQLException sqlEx) {
                Log.e(TBL_NAME, sqlEx.getMessage() + "\n" + sqlEx.getStackTrace().toString());
            }finally {
                db.close();
            }
        }

        return rowId;

    }


    public long insertPlayerDatasIntoDbTable(SQLiteDatabase db, @NonNull PlayerDatas playerDatas){
        long rowId = -1L;

        if(playerDatas!=null){
            try{
                ContentValues cvPlayerDatas = this.getContentValuesFromPlayerDatas(playerDatas);

            rowId=db.insert(TBL_NAME, null, cvPlayerDatas);

            }catch (SQLException sqlEx) {
                Log.e(TBL_NAME, sqlEx.getMessage() + "\n" + sqlEx.getStackTrace().toString());
            } finally {
                // Closing the database connection
                db.close();
            }
        }
        return rowId;
    }


    //endregion

    //region 6. Update Operations
    public int updatePlayerDatasInTable(SQLiteDatabase db, @NonNull PlayerDatas playerDatasToUpdate){
        int iCountOfAffectRows = -1;

        if(playerDatasToUpdate!=null) {
            try {
                ContentValues cvPlayerDatas = this.getContentValuesFromPlayerDatas(playerDatasToUpdate);
                String strWhereClaus = COL_NAME_ID + EQUALS_OPERATOR_INC_PLACE_HOLDER;
                String[] strWhereArgs = {String.valueOf(playerDatasToUpdate.getId())};

                iCountOfAffectRows = db.update(TBL_NAME, cvPlayerDatas, strWhereClaus, strWhereArgs);

            } catch (SQLException sqlEx) {
                Log.e(TBL_NAME, sqlEx.getMessage() + "\n" + sqlEx.getStackTrace().toString());
            } finally {
                // Closing the database connection
                db.close();
            }
        }

        return iCountOfAffectRows;

    }
    //endregion

    //region 7. ContentValues and Result set Handling

    private ContentValues getContentValuesFromPlayerDatas(PlayerDatas playerDatas){
        ContentValues cvPlayerDatas=new ContentValues();

        cvPlayerDatas.put(COL_NAME_PLAYER_NAME, playerDatas.getName());
        cvPlayerDatas.put(COL_NAME_PLAYER_PVP_WINS, playerDatas.getWinsPvP());
        cvPlayerDatas.put(COL_NAME_PLAYER_PVE_WINS, playerDatas.getWinsPvE());

        return cvPlayerDatas;
    }


    private PlayerDatas getPlayerDAtasTableStatement(Cursor cResultSet){
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
