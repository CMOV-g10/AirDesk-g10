package pt.ulisboa.tecnico.cmov.airdesk_g10.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Random;

import pt.ulisboa.tecnico.cmov.airdesk_g10.core.User;
import pt.ulisboa.tecnico.cmov.airdesk_g10.core.Workspace;
import pt.ulisboa.tecnico.cmov.airdesk_g10.exceptions.UserAlreadyExistsException;
import pt.ulisboa.tecnico.cmov.airdesk_g10.exceptions.UserDoesNotExistException;
import pt.ulisboa.tecnico.cmov.airdesk_g10.exceptions.WorkspaceAlreadyExistsException;
import pt.ulisboa.tecnico.cmov.airdesk_g10.exceptions.WorkspaceDoesNotExistException;

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
    public ArrayList<String> getUserWorkSpaces(int uid){
        SQLiteDatabase db = this.getReadableDatabase();
        String SqlUHW= "SELECT * FROM "+AirDeskContract.UserHasWorkspaceEntry.TABLE_NAME;
        ArrayList<String> wid=new ArrayList<String>();
        Cursor cUHW=db.rawQuery(SqlUHW,null);
        while(!cUHW.isAfterLast()){
            int cUHWuid=cUHW.getInt(cUHW.getColumnIndexOrThrow(AirDeskContract.UserHasWorkspaceEntry.COLUMN_UHW_UID));
            String cUHWwsid=cUHW.getString(cUHW.getColumnIndexOrThrow(AirDeskContract.UserHasWorkspaceEntry.COLUMN_UHW_WSID));
            if(uid==cUHWuid)wid.add(cUHWwsid);
            cUHW.moveToNext();
        }
        return wid;
    }

    public int getUserId(String username){
        SQLiteDatabase db = this.getReadableDatabase();
        String SqlUid= "SELECT * FROM "+AirDeskContract.UserEntry.TABLE_NAME;
        Cursor cuid = db.rawQuery(SqlUid, null);
        int uid=-1;
        while(!cuid.isAfterLast()){
            String cusername=cuid.getString(cuid.getColumnIndexOrThrow(AirDeskContract.UserEntry.COLUMN_USER_NAME));
            if(cusername.equals(username)){
                uid=cuid.getInt(cuid.getColumnIndexOrThrow(AirDeskContract.UserEntry.COLUMN_USER_ID));
            }
            cuid.moveToNext();
        }
        if(uid==-1)throw new UserDoesNotExistException(username);
        else return uid;
    }

    public User getUser(int uid) {
        SQLiteDatabase db= this.getReadableDatabase();

        String Sql= "SELECT * FROM "+AirDeskContract.UserEntry.TABLE_NAME;
        Cursor c = db.rawQuery(Sql,null);
        c.moveToFirst();
        User u=null;
        int cuid = -1;
        String cuname, cpass;
        while(!c.isAfterLast()){
            cuid = c.getInt(c.getColumnIndexOrThrow(AirDeskContract.UserEntry.COLUMN_USER_ID));
            if(cuid == uid) {
                cuname = c.getString(c.getColumnIndexOrThrow(AirDeskContract.UserEntry.COLUMN_USER_NAME));
                cpass = c.getString(c.getColumnIndexOrThrow(AirDeskContract.UserEntry.COLUMN_USER_PASSWORD));
                u=new User(cuid,cuname,cpass);
                return u;
            }
        }
        throw new UserDoesNotExistException(uid);

    }

    public User getWorkspaceOwner(int wid) {
        try {
            SQLiteDatabase db = this.getReadableDatabase();

            String wssql = "SELECT * FROM " + AirDeskContract.UserHasWorkspaceEntry.TABLE_NAME;
            Cursor c = db.rawQuery(wssql, null);
            c.moveToFirst();
            User user = null;
            while (!c.isAfterLast()) {
                if (wid == c.getInt(c.getColumnIndexOrThrow(AirDeskContract.UserHasWorkspaceEntry.COLUMN_UHW_WSID))) {
                    return getUser(c.getInt(c.getColumnIndexOrThrow(AirDeskContract.UserHasWorkspaceEntry.COLUMN_UHW_UID)));
                }
            }

        } catch(UserDoesNotExistException u) {throw u;}
        return null;
    }

    public Workspace getWorkspace(int wid) {
        try {
            SQLiteDatabase db = this.getReadableDatabase();

            String wssql = "SELECT * FROM " + AirDeskContract.WorkspaceEntry.TABLE_NAME;
            Cursor c = db.rawQuery(wssql, null);
            c.moveToFirst();
            Workspace workspace = null;
            String wsname, wsquota, wspublic, wstags;
            User wsowner;
            while (!c.isAfterLast()) {
                if (wid == c.getInt(c.getColumnIndexOrThrow(AirDeskContract.WorkspaceEntry.COLUMN_WORKSPACE_ID))) {
                    wsname = c.getString(c.getColumnIndexOrThrow(AirDeskContract.WorkspaceEntry.COLUMN_WORKSPACE_NAME));
                    wsquota = c.getString(c.getColumnIndexOrThrow(AirDeskContract.WorkspaceEntry.COLUMN_WORKSPACE_QUOTA));
                    wspublic = c.getString(c.getColumnIndexOrThrow(AirDeskContract.WorkspaceEntry.COLUMN_WORKSPACE_PUBLIC));
                    wstags = c.getString(c.getColumnIndexOrThrow(AirDeskContract.UserEntry.COLUMN_USER_PASSWORD));
                    wsowner = getWorkspaceOwner(wid);
                    workspace = new Workspace(wid, wsname, Integer.getInteger(wsquota), true, wsowner); //TRUE AIN T TRUE
                    return workspace;
                }
            }throw new WorkspaceDoesNotExistException(wid);
        } catch(UserDoesNotExistException u){throw u;}




    }

    public User searchUser(String username){
        SQLiteDatabase db= this.getReadableDatabase();

        String Sql= "SELECT * FROM "+AirDeskContract.UserEntry.TABLE_NAME;
        Cursor c = db.rawQuery(Sql,null);
        c.moveToFirst();
        User u=null;
        while(!c.isAfterLast()){
            String cuname=c.getString(c.getColumnIndexOrThrow(AirDeskContract.UserEntry.COLUMN_USER_NAME));
            int cuid=c.getInt(c.getColumnIndexOrThrow(AirDeskContract.UserEntry.COLUMN_USER_ID));
            String cpass=c.getString(c.getColumnIndexOrThrow(AirDeskContract.UserEntry.COLUMN_USER_PASSWORD));
            if(cuname.equals(username)){
                u=new User(Integer.valueOf(cuid),cuname,cpass);
            }
        }
        if(u==null){throw new UserDoesNotExistException(username);}
        return u;



    }

    public boolean workspaceExists(String wname, String username){
        try{
        SQLiteDatabase db = this.getReadableDatabase();
        String SqlWname= "SELECT * FROM "+AirDeskContract.WorkspaceEntry.TABLE_NAME;
        Cursor cwname = db.rawQuery(SqlWname, null);
        cwname.moveToFirst();
        int uid=this.getUserId(username);
        ArrayList<String> wid=this.getUserWorkSpaces(uid);

        while(!cwname.isAfterLast()){
            int twid=cwname.getInt(cwname.getColumnIndexOrThrow(AirDeskContract.WorkspaceEntry.COLUMN_WORKSPACE_ID));
            String twname=cwname.getString(cwname.getColumnIndexOrThrow(AirDeskContract.WorkspaceEntry.COLUMN_WORKSPACE_NAME));
            for(String s: wid){
                if(s.equals(twid)&&twname.equals(wname)){
                    return true;
                }
            cwname.moveToNext();
            }
        }
        //returns false if no user with that username and password exists
        return false;}
        catch(UserDoesNotExistException u){return false;}
    }

    public boolean userExists(String username){
        SQLiteDatabase db = this.getReadableDatabase();
        String Sql= "SELECT * FROM "+AirDeskContract.UserEntry.TABLE_NAME;

        Cursor c = db.rawQuery(Sql, null);

        c.moveToFirst();

        while (!c.isAfterLast()){
            String dbUserName = c.getString(c.getColumnIndexOrThrow(AirDeskContract.UserEntry.COLUMN_USER_NAME));

            if(dbUserName.equals(username))
                //if user exists return true
                return true;
            c.moveToNext();
        }
        //returns false if no user with that username exists
        return false;
    }

    public boolean userExists(String username,String password){
        SQLiteDatabase db = this.getReadableDatabase();
        String Sql= "SELECT * FROM "+AirDeskContract.UserEntry.TABLE_NAME;

        Cursor c = db.rawQuery(Sql, null);

        c.moveToFirst();

        while (!c.isAfterLast()){
            String dbUserName = c.getString(c.getColumnIndexOrThrow(AirDeskContract.UserEntry.COLUMN_USER_NAME));
            String dbUserPass = c.getString(c.getColumnIndexOrThrow(AirDeskContract.UserEntry.COLUMN_USER_PASSWORD));
            if(dbUserName.equals(username)&&dbUserPass.equals(password))
                //if user exists with pass return true
                return true;
            c.moveToNext();
        }
        //returns false if no user with that username and password exists
        return false;
    }

    public void addUser(String username, String password)throws UserAlreadyExistsException{
        if(userExists(username)){
            throw new UserAlreadyExistsException(username);
        }
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

    public void addWorkspace(String username,String wname, boolean pub,int quota, String tags){
       try{
           if(workspaceExists(wname, username)){
            throw new WorkspaceAlreadyExistsException(wname);
        }
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        int gen= generator();
            // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(AirDeskContract.WorkspaceEntry.COLUMN_WORKSPACE_ID, gen);
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


        ContentValues uhwValues= new ContentValues();
        uhwValues.put(AirDeskContract.UserHasWorkspaceEntry.COLUMN_UHW_ID,generator());
        uhwValues.put(AirDeskContract.UserHasWorkspaceEntry.COLUMN_UHW_UID,getUserId(username));
        uhwValues.put(AirDeskContract.UserHasWorkspaceEntry.COLUMN_UHW_WSID,gen);
        long newRowId2;
        newRowId2=db.insert(
                AirDeskContract.UserHasWorkspaceEntry.TABLE_NAME,
                null,
                uhwValues);
        db.endTransaction();}
       catch(UserDoesNotExistException u){u.getMessage();}
    }

    public int generator(){
        Random r=new Random();

        return r.nextInt();
    }
}