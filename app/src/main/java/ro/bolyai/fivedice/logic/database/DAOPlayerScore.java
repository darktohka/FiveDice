package ro.bolyai.fivedice.logic.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import ro.bolyai.fivedice.model.PlayerScore;

/**
 * [D]ata [A]ccess [O]bject for the table tblPlayerScores
 * in the local SQLite3 Database. It implements
 * all the needed Information such as:<br>
 * <br>the<br>
 * Tablename : {@link DAOPlayerScore#TBL_NAME}<br>
 * <br>the<br>
 * Columnnames:
 * <ul>
 * <li>{@link DAOPlayerScore#COL_NAME_ID}</li>
 * <li>{@link DAOPlayerScore#COL_NAME_PLAYER_NAME}</li>
 * * <li>{@link DAOPlayerScore#COL_NAME_PLAYER_PVP_WINS}</li>
 * * <li>{@link DAOPlayerScore#COL_NAME_PLAYER_PVE_WINS}</li>
 * </ul>
 * <br> and the <br>CRUD Operations:
 */

public class DAOPlayerScore extends ASQLiteKeyWords {

    //region 0. Constants
    /**
     * Table name
     */
    private static final String TBL_NAME = "tblPlayerScores";
    /**
     * 1. Column _id with the Index 0
     */
    private static final String COL_NAME_ID = "_id";
    /**
     * 2. Column playerName with the index 1
     */
    private static final String COL_NAME_PLAYER_NAME = "playerName";
    /**
     * 3. Column playerPvPWins with the index 2
     */
    private static final String COL_NAME_PLAYER_PVP_WINS = "playerPvPWins";
    /**
     * 4. Column playerPvEWins with the index 3
     */
    private static final String COL_NAME_PLAYER_PVE_WINS = "playerPvEWins";
    //endregion

    //region 1. Decl and Init
    //endregion

    //region 2. Constructor

    /**
     * Default Constructor
     */
    public DAOPlayerScore() {
        //nothing to do
    }
    //endregion

    //region 3. Create Table Statement

    /**
     * Returns the SQL-Statement for
     * creating this database table
     * {@link DAOPlayerScore#TBL_NAME}
     * CREATE TABLE tblPlayerScores<br>
     * //Column name / Datatype Additional Orders//<br>
     * (<br>
     * _id INTEGER PRIMARY KEY AUTOINCREMENT, <br>
     * playerName TEXT,<br>
     * playerPvPWins INTEGER,<br>
     * playerPvEWins INTEGER<br>
     * );<br>
     *
     * @return strCreateTableStatement : {@link String}
     */
    public String getCreateTableStatement() {
        return CREATE_TBL + TBL_NAME
                + CHAR_OPEN_BRACKET
                + COL_NAME_ID + PRIMARY_KEY_AUTO_INCREMENT_INC_COMMA
                + COL_NAME_PLAYER_NAME + DATA_TYPE_TEXT_INC_COMMA
                + COL_NAME_PLAYER_PVP_WINS + DATA_TYPE_INTEGER_INC_COMMA
                + COL_NAME_PLAYER_PVE_WINS + DATA_TYPE_INTEGER
                + CHAR_CLOSE_BRACKET_SEMICOLON;
    }
    //endregion

    //region 4. Read Operations

    /**
     * Returns all the {@link PlayerScore}s in the database table
     *
     * @param db : {@link SQLiteDatabase} : Database object
     * @return allPlayerScoresFromDbTable : {@link PlayerScore}s:
     */
    public List<PlayerScore> getAllPlayerScoresFromDbTable(SQLiteDatabase db) {
        List<PlayerScore> allPlayerScoreFromDbTable = new ArrayList<>();

        try {
            Cursor cResultSet = db.query(TBL_NAME,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null);

            if (cResultSet != null) {
                while (cResultSet.moveToNext()) {
                    allPlayerScoreFromDbTable.add(getPlayerScoreTableStatement(cResultSet));
                }
            }
        } catch (SQLiteException sqlEx) {
            Log.e(TBL_NAME, sqlEx.getMessage() + "\n" + sqlEx.getStackTrace().toString());
        } finally {
            db.close();
        }

        return allPlayerScoreFromDbTable;
    }

    /**
     * Returns the {@link PlayerScore}s by the given player name
     *
     * @param db         : {@link SQLiteDatabase} : Database object
     * @param playerName : String : {@link PlayerScore#getName()} PlayerScore to get from the database
     * @return playerScoreFromTable : {@link PlayerScore}s: PlayerScore fount by the name
     * or null if there is no record with the given name
     */

    @Nullable
    public PlayerScore getPlayerScoreByNameFromDbTable(SQLiteDatabase db, String playerName) {
        PlayerScore playerScoreFromTable = null;

        try {
            String strWhereClause = COL_NAME_PLAYER_NAME + EQUALS_OPERATOR_INC_PLACE_HOLDER;
            String[] strWhereArgs = {playerName};

            Cursor cResultSet = db.query(TBL_NAME,
                    null,
                    strWhereClause,
                    strWhereArgs,
                    null,
                    null,
                    null
            );

            if (cResultSet != null && cResultSet.moveToFirst()) {
                // Extract the data into a PlayerScore
                playerScoreFromTable = this.getPlayerScoreTableStatement(cResultSet);
            }
        } catch (SQLException sqlEx) {
            Log.e(TBL_NAME, sqlEx.getMessage() + "\n" + sqlEx.getStackTrace().toString());
        } finally {
            db.close();
        }

        return playerScoreFromTable;
    }
    //endregion

    //region 5. Insert Operations

    /**
     * Inserts set of PlayerScores into database table
     *
     * @param db                     : {@link SQLiteDatabase} : Database object
     * @param allPlayerScoreToInsert : {@link List} - {@link PlayerScore} : PlayerScores which are going to be inserted
     * @return rowId : long : row id of the last inserted customer
     */
    public long insertAllPlayerScoresIntoDbTable(SQLiteDatabase db, @NonNull List<PlayerScore> allPlayerScoreToInsert) {
        long rowId = -1L;

        if (allPlayerScoreToInsert.isEmpty()) {
            return rowId;
        }

        try {
            for (PlayerScore score : allPlayerScoreToInsert) {
                ContentValues cvPlayerScore = this.getContentValuesFromPlayerScore(score);
                rowId = db.insert(TBL_NAME, null, cvPlayerScore);
            }
        } catch (SQLException sqlEx) {
            Log.e(TBL_NAME, sqlEx.getMessage() + "\n" + sqlEx.getStackTrace().toString());
        } finally {
            db.close();
        }

        return rowId;
    }

    /**
     * Insert a single  {@link PlayerScore} into the database table
     *
     * @param db          : {@link SQLiteDatabase} : Database object
     * @param playerScore : {@link PlayerScore} : PlayerScore which is going to be inserted
     * @return rowId : long : row id of the last inserted customer
     */

    public long insertPlayerScoreIntoDbTable(SQLiteDatabase db, @NonNull PlayerScore playerScore) {
        long rowId = -1L;

        try {
            ContentValues cvPlayerScore = this.getContentValuesFromPlayerScore(playerScore);

            rowId = db.insert(TBL_NAME, null, cvPlayerScore);
        } catch (SQLException sqlEx) {
            Log.e(TBL_NAME, sqlEx.getMessage() + "\n" + sqlEx.getStackTrace().toString());
        } finally {
            // Closing the database connection
            db.close();
        }

        return rowId;
    }
    //endregion

    //region 6. Update Operations

    /**
     * Updates a single existent {@link PlayerScore} in the database table
     *
     * @param db                  : {@link SQLiteDatabase} : Database object
     * @param playerScoreToUpdate : {@link PlayerScore} : PlayerScore which is going to be updated
     * @return iCountofAffectedRows : int : The number describes how many data records where updated
     */
    public int updatePlayerScoreInTable(SQLiteDatabase db, @NonNull PlayerScore playerScoreToUpdate) {
        int iCountOfAffectedRows = -1;

        try {
            ContentValues cvPlayerScore = this.getContentValuesFromPlayerScore(playerScoreToUpdate);
            String strWhereClaus = COL_NAME_ID + EQUALS_OPERATOR_INC_PLACE_HOLDER;
            String[] strWhereArgs = {String.valueOf(playerScoreToUpdate.getId())};

            iCountOfAffectedRows = db.update(TBL_NAME, cvPlayerScore, strWhereClaus, strWhereArgs);
        } catch (SQLException sqlEx) {
            Log.e(TBL_NAME, sqlEx.getMessage() + "\n" + sqlEx.getStackTrace().toString());
        } finally {
            // Closing the database connection
            db.close();
        }

        return iCountOfAffectedRows;

    }
    //endregion

    //region 7. ContentValues and Result set Handling
    private ContentValues getContentValuesFromPlayerScore(PlayerScore playerScore) {
        ContentValues cvPlayerScore = new ContentValues();

        cvPlayerScore.put(COL_NAME_PLAYER_NAME, playerScore.getName());
        cvPlayerScore.put(COL_NAME_PLAYER_PVP_WINS, playerScore.getWinsPvP());
        cvPlayerScore.put(COL_NAME_PLAYER_PVE_WINS, playerScore.getWinsPvE());

        return cvPlayerScore;
    }

    private PlayerScore getPlayerScoreTableStatement(Cursor cResultSet) {
        PlayerScore playerScoreFromDbTable = new PlayerScore();

        int indexId = cResultSet.getColumnIndex(COL_NAME_ID);
        int indexPlayerName = cResultSet.getColumnIndex(COL_NAME_PLAYER_NAME);
        int indexPlayerPvPWins = cResultSet.getColumnIndex(COL_NAME_PLAYER_PVP_WINS);
        int indexPlayerPvEWins = cResultSet.getColumnIndex(COL_NAME_PLAYER_PVE_WINS);

        playerScoreFromDbTable.setId(cResultSet.getInt(indexId));
        playerScoreFromDbTable.setWinsPvP(cResultSet.getInt(indexPlayerPvPWins));
        playerScoreFromDbTable.setWinsPvE(cResultSet.getInt(indexPlayerPvEWins));

        playerScoreFromDbTable.setName(cResultSet.getString(indexPlayerName));

        return playerScoreFromDbTable;
    }
    //endregion
}
