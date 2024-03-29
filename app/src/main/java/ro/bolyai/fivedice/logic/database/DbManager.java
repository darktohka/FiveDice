package ro.bolyai.fivedice.logic.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.util.Log;

import java.util.List;

import ro.bolyai.fivedice.model.PlayerScore;

public class DbManager extends SQLiteOpenHelper {

    //region 0. Constants
    private static final String TAG = DbManager.class.getSimpleName();
    private static final String DB_NAME = "fiveDice.db";
    private static final int DB_VERSION = 1;
    //endregion

    //region 1. Variables
    private static DbManager instance;

    private DAOPlayerScore daoPlayerScore;
    //endregion

    //region 2. Constructor

    public DbManager(Context context) {
        super(context, DB_NAME, null, DB_VERSION);

        this.daoPlayerScore = new DAOPlayerScore();

        this.getWritableDatabase();
    }

    //endregion

    //region 3. Create and Update the Database

    /**
     * Called when the database is created for the first time. This is where the
     * creation of tables and the initial population of the tables should happen.
     *
     * @param db The database.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(this.daoPlayerScore.getCreateTableStatement());
        } catch (SQLiteException sqlEx) {
            Log.e(TAG, sqlEx.getMessage() + "\n" + sqlEx.getStackTrace().toString());
        }
    }

    /**
     * Called when the database needs to be upgraded. The implementation
     * should use this method to drop tables, add tables, or do anything else it
     * needs to upgrade to the new schema version.
     *
     * <p>
     * The SQLite ALTER TABLE documentation can be found
     * <a href="http://sqlite.org/lang_altertable.html">here</a>. If you add new columns
     * you can use ALTER TABLE to insert them into a live table. If you rename or remove columns
     * you can use ALTER TABLE to rename the old table, then create the new table and then
     * populate the new table with the contents of the old table.
     * </p><p>
     * This method executes within a transaction.  If an exception is thrown, all changes
     * will automatically be rolled back.
     * </p>
     *
     * @param db         The database.
     * @param oldVersion The old database version.
     * @param newVersion The new database version.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //1. Save all tables
        //2. Generate new tables
        //3. Insert the saved table data
        //4. rop all tables

        Log.d(TAG + " - onUpgrade", "todo");
    }
    //endregion

    //region 4. Get Instance

    public static synchronized DbManager getInstance(Context context) {
        if (instance == null) {
            instance = new DbManager(context);
        }

        return instance;
    }

    //endregion

    //region 5. Read Customers

    public List<PlayerScore> getAllPlayerScores() {
        return this.daoPlayerScore.getAllPlayerScoresFromDbTable(this.getWritableDatabase());
    }

    @Nullable
    public PlayerScore getPlayerScoreByName(String strName) {
        return this.daoPlayerScore.getPlayerScoreByNameFromDbTable(this.getWritableDatabase(), strName);
    }

    //endregion

    //region 6. Insert All PlayerScore

    public long insertAllPlayerScores(@NonNull List<PlayerScore> playerScoreToInsert) {
        return this.daoPlayerScore.insertAllPlayerScoresIntoDbTable(this.getWritableDatabase(), playerScoreToInsert);
    }

    public long insertPlayerScore(@NonNull PlayerScore playerScoreToInsert) {
        return this.daoPlayerScore.insertPlayerScoreIntoDbTable(this.getWritableDatabase(), playerScoreToInsert);
    }

    //endregion

    //region 7. Update PlayerScore
    public int updatePlayerScore(@NonNull PlayerScore playerScoreToUpdate) {
        return this.daoPlayerScore.updatePlayerScoreInTable(this.getWritableDatabase(), playerScoreToUpdate);
    }
    //endregion

}
