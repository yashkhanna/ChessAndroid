package org.honeywell.mytestapplication.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by yash.khanna on 4/28/2017.
 */

public class UserDBHelper extends SQLiteOpenHelper {

    public static final int DB_VERSION = 1;
    public static final String DB_NAME = "user_db";

    private final String SQL_CREATE_ENTRIES = "CREATE TABLE IF NOT EXISTS " + UserContract.UserEntry.TABLE_NAME + " (" + UserContract.UserEntry.COLUMN_NAME_EMAIL + " TEXT PRIMARY KEY," + UserContract.UserEntry.COLUMN_NAME_PASSWORD + " TEXT)";
    private final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + UserContract.UserEntry.TABLE_NAME;

    public UserDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onDowngrade(db, oldVersion, newVersion);
    }
}
