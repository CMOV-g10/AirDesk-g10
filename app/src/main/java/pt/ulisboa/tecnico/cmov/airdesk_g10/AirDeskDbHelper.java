package pt.ulisboa.tecnico.cmov.airdesk_g10;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Random;

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

    private static final String COMMA_SEP = ",";

    private static final String SQL_CREATE_USER_TABLE =
            "CREATE TABLE " + AirDeskContract.UserEntry.TABLE_NAME + " (" +
                    AirDeskContract.UserEntry.COLUMN_USER_ID + " INTEGER PRIMARY KEY" + COMMA_SEP +
                    AirDeskContract.UserEntry.COLUMN_USER_NAME + TEXT_TYPE + COMMA_SEP +
                    AirDeskContract.UserEntry.COLUMN_USER_PASSWORD + TEXT_TYPE +
                    " )";

    private static final String SQL_CREATE_FILE_TABLE =
            "CREATE TABLE " + AirDeskContract.FileEntry.TABLE_NAME + " (" +
                    AirDeskContract.FileEntry.COLUMN_FILE_ID +" INTEGER PRIMARY KEY" + COMMA_SEP +
                    AirDeskContract.FileEntry.COLUMN_FILE_TITLE + TEXT_TYPE + COMMA_SEP +
                    AirDeskContract.FileEntry.COLUMN_FILE_CONTENT + TEXT_TYPE +
                    " )";

    private static final String SQL_CREATE_WORKSPACE_TABLE =
            "CREATE TABLE " + AirDeskContract.WorkspaceEntry.TABLE_NAME + " (" +
                    AirDeskContract.WorkspaceEntry.COLUMN_WORKSPACE_ID + " INTEGER PRIMARY KEY" + COMMA_SEP +
                    AirDeskContract.WorkspaceEntry.COLUMN_WORKSPACE_NAME + TEXT_TYPE + COMMA_SEP +
                    AirDeskContract.WorkspaceEntry.COLUMN_WORKSPACE_PUBLIC + BOOLEAN_TYPE + COMMA_SEP +
                    AirDeskContract.WorkspaceEntry.COLUMN_WORKSPACE_QUOTA + INTEGER_TYPE + COMMA_SEP +
                    AirDeskContract.WorkspaceEntry.COLUMN_WORKSPACE_TAGS + TEXT_TYPE +
                    " )";

    private static  final String SQL_CREATE_UHW_TABLE =
            "CREATE TABLE " + AirDeskContract.UserHasWorkspaceEntry.TABLE_NAME + " (" +
                AirDeskContract.UserHasWorkspaceEntry.COLUMN_UHW_ID + " INTEGER PRIMARY KEY" + COMMA_SEP +
                AirDeskContract.UserHasWorkspaceEntry.COLUMN_UHW_UID + TEXT_TYPE + COMMA_SEP +
                    AirDeskContract.UserHasWorkspaceEntry.COLUMN_UHW_WSID + TEXT_TYPE + COMMA_SEP +
                    "FOREIGN KEY("+AirDeskContract.UserHasWorkspaceEntry.COLUMN_UHW_UID+")"+ " REFERENCES "+
                    AirDeskContract.UserEntry.TABLE_NAME+"("+AirDeskContract.UserEntry.COLUMN_USER_ID+")"+COMMA_SEP+
                    "FOREIGN KEY("+AirDeskContract.UserHasWorkspaceEntry.COLUMN_UHW_WSID+")"+ " REFERENCES "+
                    AirDeskContract.WorkspaceEntry.TABLE_NAME+"("+AirDeskContract.WorkspaceEntry.COLUMN_WORKSPACE_ID+")"+
           " )";
    private static  final String SQL_CREATE_WHS_TABLE =
            "CREATE TABLE " + AirDeskContract.WorkspaceHasSubscriptionsEntry.TABLE_NAME + " (" +
                    AirDeskContract.WorkspaceHasSubscriptionsEntry.COLUMN_WHS_ID + " INTEGER PRIMARY KEY" + COMMA_SEP +
                    AirDeskContract.WorkspaceHasSubscriptionsEntry.COLUMN_WHS_UID + TEXT_TYPE + COMMA_SEP +
                    AirDeskContract.WorkspaceHasSubscriptionsEntry.COLUMN_WHS_WSID + TEXT_TYPE + COMMA_SEP +
                    "FOREIGN KEY("+AirDeskContract.WorkspaceHasSubscriptionsEntry.COLUMN_WHS_UID+")"+ " REFERENCES "+
                    AirDeskContract.UserEntry.TABLE_NAME+"("+AirDeskContract.UserEntry.COLUMN_USER_ID+")"+COMMA_SEP+
                    "FOREIGN KEY("+AirDeskContract.WorkspaceHasSubscriptionsEntry.COLUMN_WHS_WSID+")"+ " REFERENCES "+
                    AirDeskContract.WorkspaceEntry.TABLE_NAME+"("+AirDeskContract.WorkspaceEntry.COLUMN_WORKSPACE_ID+")"+
                    " )";

    private static  final String SQL_CREATE_WHF_TABLE =
            "CREATE TABLE " + AirDeskContract.WorkspaceHasFileEntry.TABLE_NAME + " (" +
                    AirDeskContract.WorkspaceHasFileEntry.COLUMN_WHF_ID + " INTEGER PRIMARY KEY" + COMMA_SEP +
                    AirDeskContract.WorkspaceHasFileEntry.COLUMN_WHF_FID + TEXT_TYPE + COMMA_SEP +
                    AirDeskContract.WorkspaceHasFileEntry.COLUMN_WHF_WSID + TEXT_TYPE + COMMA_SEP +
                    "FOREIGN KEY("+AirDeskContract.WorkspaceHasFileEntry.COLUMN_WHF_FID+")"+ " REFERENCES "+
                    AirDeskContract.FileEntry.TABLE_NAME+"("+AirDeskContract.FileEntry.COLUMN_FILE_ID+")"+COMMA_SEP+
                    "FOREIGN KEY("+AirDeskContract.WorkspaceHasFileEntry.COLUMN_WHF_WSID+")"+ " REFERENCES "+
                    AirDeskContract.WorkspaceEntry.TABLE_NAME+"("+AirDeskContract.WorkspaceEntry.COLUMN_WORKSPACE_ID+")"+
                    " )";

    private static final String SQL_DELETE_USER_TABLE =
            "DROP TABLE IF EXISTS " + AirDeskContract.UserEntry.TABLE_NAME;

    private static final String SQL_DELETE_FILE_TABLE =
            "DROP TABLE IF EXISTS " + AirDeskContract.FileEntry.TABLE_NAME;

    private static final String SQL_DELETE_WORKSPACE_TABLE =
            "DROP TABLE IF EXISTS " + AirDeskContract.WorkspaceEntry.TABLE_NAME;

    private static final String SQL_DELETE_UHW_TABLE =
            "DROP TABLE IF EXISTS " + AirDeskContract.UserHasWorkspaceEntry.TABLE_NAME;

    private static final String SQL_DELETE_WHS_TABLE =
            "DROP TABLE IF EXISTS " + AirDeskContract.WorkspaceHasSubscriptionsEntry.TABLE_NAME;

    private static final String SQL_DELETE_WHF_TABLE =
            "DROP TABLE IF EXISTS " + AirDeskContract.WorkspaceHasFileEntry.TABLE_NAME;

    public AirDeskDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_FILE_TABLE);
        db.execSQL(SQL_CREATE_USER_TABLE);
        db.execSQL(SQL_CREATE_WORKSPACE_TABLE);
        db.execSQL(SQL_CREATE_UHW_TABLE);
        db.execSQL(SQL_CREATE_WHS_TABLE);
        db.execSQL(SQL_CREATE_WHF_TABLE);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_FILE_TABLE);
        db.execSQL(SQL_DELETE_USER_TABLE);
        db.execSQL(SQL_DELETE_WORKSPACE_TABLE);
        db.execSQL(SQL_DELETE_UHW_TABLE);
        db.execSQL(SQL_DELETE_WHS_TABLE);
        db.execSQL(SQL_DELETE_WHF_TABLE);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }



    public void addUser(String username, String password){


        SQLiteDatabase db = this.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(AirDeskContract.UserEntry.COLUMN_USER_ID, generator());
        values.put(AirDeskContract.UserEntry.COLUMN_USER_NAME, username);
        values.put(AirDeskContract.UserEntry.COLUMN_USER_PASSWORD, password);

        // Insert the new row, returning the primary key value of the new row
        long newRowId;
        newRowId = db.insert(
                AirDeskContract.UserEntry.TABLE_NAME,
                null,
                values);

    }
    public void addWorkspace(String wname, boolean pub,int quota, String tags){

        SQLiteDatabase db = this.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(AirDeskContract.WorkspaceEntry.COLUMN_WORKSPACE_ID, generator());
        values.put(AirDeskContract.WorkspaceEntry.COLUMN_WORKSPACE_NAME,wname);
        values.put(AirDeskContract.WorkspaceEntry.COLUMN_WORKSPACE_PUBLIC,pub);
        values.put(AirDeskContract.WorkspaceEntry.COLUMN_WORKSPACE_QUOTA,quota);
        values.put(AirDeskContract.WorkspaceEntry.COLUMN_WORKSPACE_TAGS,tags);

        // Insert the new row, returning the primary key value of the new row
        long newRowId;
        newRowId = db.insert(
                AirDeskContract.WorkspaceEntry.TABLE_NAME,
                null,
                values);





    }
    public int generator(){
        Random r=new Random();

        return r.nextInt();
    }
}