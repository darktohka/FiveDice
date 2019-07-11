package ro.bolyai.fivedice.logic.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.List;

import ro.bolyai.fivedice.model.PlayerDatas;

public class DbManager extends SQLiteOpenHelper {

    //region 0. Constants
    private static final String TAG = DbManager.class.getSimpleName();
    private static final String DB_NAME="fiveDice.db";
    private static final int DB_VERSION=1;
    //endregion

    //region 1. Variables
    private static DbManager instance;

    private DAOPlayerDatas daoPlayerDatas;
    //endregion

    //region 2. Constructor

    public DbManager(Context context){
        super(context, DB_NAME, null, DB_VERSION);

        this.daoPlayerDatas=new DAOPlayerDatas();

        this.getWritableDatabase();

        Log.d(TAG, "Generated the database and all the DAO Objects");
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
        try{
            db.execSQL(this.daoPlayerDatas.getCreateTableStatement());
        }catch (SQLiteException sqlEx) {
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

    public static synchronized DbManager getInstance(Context context){
        if(instance==null){
            instance=new DbManager(context);
        }
        return instance;
    }

    //endregion

    //region 5. Read Customers

    public List<PlayerDatas> getAllPlayerDatas(){
        return this.daoPlayerDatas.getPlayerDatasByIdFromDbTable(this.getWritableDatabase());
    }

    @Nullable
    public PlayerDatas getPlayerDatasById(int iId){
        return this.daoPlayerDatas.getPlayerDatasByIdFromDbTable(this.getWritableDatabase(),iId);
    }

    //endregion

    //region 6. Insert All PlayerDatas

    public long insertAllPlayerDatas(@NonNull List<PlayerDatas> playerDatasToInsert){
        return this.daoPlayerDatas.insertAllPlayerDatasIntoFromTable(this.getWritableDatabase(), playerDatasToInsert);
    }

    public long insertPlayerDatas(@NonNull PlayerDatas playerDatasToInsert){
        return this.daoPlayerDatas.insertPlayerDatasIntoDbTable(this.getWritableDatabase(), playerDatasToInsert);
    }

    //endregion

    //region 7. Update PlayerDatas
    public int updatePlayerDatas(@NonNull PlayerDatas playerDatasToUpdate){
        return this.daoPlayerDatas.updatePlayerDatasInTable(this.getWritableDatabase(), playerDatasToUpdate);
    }
    //endregion

}
