package com.example.studygroupapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBUtil extends SQLiteOpenHelper {

    // Variables pertaining to the SQLite Database
    private static final String DATABASE_NAME = "users.db";
    private static final int VERSION = 1;

    /**
     * Constructor for DBUtil
     * @param context
     */
    public DBUtil(Context context){
        super(context, DATABASE_NAME, null, VERSION);
    }

    public static final class UserTable {
        private static final String TABLE = "users";
        private static final String COL_ID = "_id";
        private static final String COL_USERNAME = "username";
        private static final String COL_PASSWORD = "password";
        private static final String COL_EMAIL = "email";
    }

    /**
     * The onCreate method will execute a SQL statement to create a table in the database.
     * @param db — SQLite Database
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + UserTable.TABLE + " (" +
                UserTable.COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                UserTable.COL_USERNAME + " TEXT, " +
                UserTable.COL_PASSWORD + " TEXT, " +
                UserTable.COL_EMAIL + " TEXT)";
        db.execSQL(createTable);
    }

    /**
     *
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + UserTable.TABLE);
        onCreate(db);
    }

    /**
     * Boolean method, insertData, is used to communicate with SQLite database and insert the user's
     * login credentials.
     * @param username — String variable containing a user's username.
     * @param password — String variable containing a user's password.
     * @return boolean — Boolean variable returning either true or false depending on result insertion.
     */
    public Boolean insertData(String username, String password, String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(UserTable.COL_USERNAME, username);
        contentValues.put(UserTable.COL_PASSWORD, password);
        contentValues.put(UserTable.COL_EMAIL, email);

        long result = db.insert(UserTable.TABLE, null, contentValues);

        if(result == -1) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Boolean method, checkUsername, is used to communicate with the SQLite database to retrieve
     * the username. If that specific username is retrieved, then the count will be greater than 0.
     * This means that there is a duplicate and returns true otherwise false.
     * @param username — String variable containing a user's username.
     * @return boolean — Boolean variable returning either true or false depending on username.
     */
    public Boolean checkUsername(String username) {
        SQLiteDatabase db = this.getWritableDatabase();

        String Query = "SELECT * FROM " + UserTable.TABLE + " WHERE " + UserTable.COL_USERNAME +
                " = ?";

        Cursor cursor = db.rawQuery(Query, new String[] {username});

        if (cursor.getCount() > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Boolean method, verifyLogin, is used to communicate with the SQLite database to retrieve the
     * username and password. If the username and password is retrieved, then the count will be
     * greater than 0. This means that the login credentials are valid otherwise invalid.
     * @param username — String variable containing a user's username.
     * @param password password — String variable containing a user's password.
     * @return boolean — Boolean variable returning either true or false depending on login credentials.
     */
    public Boolean verifyLogin(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        String Query = "SELECT * FROM " + UserTable.TABLE + " WHERE " + UserTable.COL_USERNAME +
                " = ? AND " + UserTable.COL_PASSWORD + " = ?";

        Cursor cursor = db.rawQuery(Query, new String[] {username, password});
        if (cursor.getCount() > 0) {
            return true;
        } else {
            return false;
        }
    }
}
