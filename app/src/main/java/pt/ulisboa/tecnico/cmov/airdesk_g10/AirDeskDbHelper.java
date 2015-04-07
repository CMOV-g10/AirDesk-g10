package pt.ulisboa.tecnico.cmov.airdesk_g10;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by luis on 4/7/15.
 */
public class AirDeskDbHelper extends SQLiteOpenHelper {

    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "AirDesk.db";

    private static final String TEXT_TYPE = " TEXT";
    private static final String BOOLEAN_TYPE = " BOOLEAN";
    private static final String INTEGER_TYPE = " INTEGER";
    private static final String LIST_TYPE = " ARRAY";
    private static final String COMMA_SEP = ",";

    private static final String SQL_CREATE_USER_TABLE =
            "CREATE TABLE " + AirDeskContract.UserEntry.TABLE_NAME + " (" +
                    AirDeskContract.UserEntry._ID + " INTEGER PRIMARY KEY," +
                    AirDeskContract.UserEntry.COLUMN_USER_ID + TEXT_TYPE + COMMA_SEP +
                    AirDeskContract.UserEntry.COLUMN_USER_NAME + TEXT_TYPE + COMMA_SEP +
                    AirDeskContract.UserEntry.COLUMN_USER_PASSWORD + TEXT_TYPE +
                    " )";

    private static final String SQL_CREATE_FILE_TABLE =
            "CREATE TABLE " + AirDeskContract.FileEntry.TABLE_NAME + " (" +
                    AirDeskContract.FileEntry._ID + " INTEGER PRIMARY KEY," +
                    AirDeskContract.FileEntry.COLUMN_FILE_ID + TEXT_TYPE + COMMA_SEP +
                    AirDeskContract.FileEntry.COLUMN_FILE_TITLE + TEXT_TYPE + COMMA_SEP +
                    AirDeskContract.FileEntry.COLUMN_FILE_CONTENT + TEXT_TYPE +
                    " )";

    private static final String SQL_CREATE_WORKSPACE_TABLE =
            "CREATE TABLE " + AirDeskContract.WorkspaceEntry.TABLE_NAME + " (" +
                    AirDeskContract.WorkspaceEntry._ID + " INTEGER PRIMARY KEY," +
                    AirDeskContract.WorkspaceEntry.COLUMN_WORKSPACE_ID + TEXT_TYPE + COMMA_SEP +
                    AirDeskContract.WorkspaceEntry.COLUMN_WORKSPACE_NAME + TEXT_TYPE + COMMA_SEP +
                    AirDeskContract.WorkspaceEntry.COLUMN_WORKSPACE_PUBLIC + BOOLEAN_TYPE + COMMA_SEP +
                    AirDeskContract.WorkspaceEntry.COLUMN_WORKSPACE_QUOTA + INTEGER_TYPE + COMMA_SEP +
                    AirDeskContract.WorkspaceEntry.COLUMN_WORKSPACE_TAGS + LIST_TYPE +
                    " )";


    private static final String SQL_DELETE_USER_TABLE =
            "DROP TABLE IF EXISTS " + AirDeskContract.UserEntry.TABLE_NAME;

    private static final String SQL_DELETE_FILE_TABLE =
            "DROP TABLE IF EXISTS " + AirDeskContract.FileEntry.TABLE_NAME;

    private static final String SQL_DELETE_WORKSPACE_TABLE =
            "DROP TABLE IF EXISTS " + AirDeskContract.WorkspaceEntry.TABLE_NAME;

    public AirDeskDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_FILE_TABLE);
        db.execSQL(SQL_CREATE_USER_TABLE);
        db.execSQL(SQL_CREATE_WORKSPACE_TABLE);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_FILE_TABLE);
        db.execSQL(SQL_DELETE_USER_TABLE);
        db.execSQL(SQL_DELETE_WORKSPACE_TABLE);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}