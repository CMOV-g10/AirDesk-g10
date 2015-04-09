package pt.ulisboa.tecnico.cmov.airdesk_g10.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Random;

import pt.ulisboa.tecnico.cmov.airdesk_g10.core.File;
import pt.ulisboa.tecnico.cmov.airdesk_g10.core.User;
import pt.ulisboa.tecnico.cmov.airdesk_g10.core.Workspace;
import pt.ulisboa.tecnico.cmov.airdesk_g10.exceptions.AirDeskException;
import pt.ulisboa.tecnico.cmov.airdesk_g10.exceptions.FileAlreadyExistsException;
import pt.ulisboa.tecnico.cmov.airdesk_g10.exceptions.FileDoesNotExistException;
import pt.ulisboa.tecnico.cmov.airdesk_g10.exceptions.UserAlreadyExistsException;
import pt.ulisboa.tecnico.cmov.airdesk_g10.exceptions.UserDoesNotExistException;
import pt.ulisboa.tecnico.cmov.airdesk_g10.exceptions.WorkspaceAlreadyExistsException;
import pt.ulisboa.tecnico.cmov.airdesk_g10.exceptions.WorkspaceDoesNotExistException;


/**
 * Created by luis on 4/7/15.
 */
public class AirDeskDbHelper extends SQLiteOpenHelper {

    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 2;
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
                AirDeskContract.UserHasWorkspaceEntry.COLUMN_UHW_UID + INTEGER_TYPE + COMMA_SEP +
                    AirDeskContract.UserHasWorkspaceEntry.COLUMN_UHW_WSID + INTEGER_TYPE + COMMA_SEP +
                    "FOREIGN KEY("+AirDeskContract.UserHasWorkspaceEntry.COLUMN_UHW_UID+")"+ " REFERENCES "+
                    AirDeskContract.UserEntry.TABLE_NAME+"("+AirDeskContract.UserEntry.COLUMN_USER_ID+")"+COMMA_SEP+
                    "FOREIGN KEY("+AirDeskContract.UserHasWorkspaceEntry.COLUMN_UHW_WSID+")"+ " REFERENCES "+
                    AirDeskContract.WorkspaceEntry.TABLE_NAME+"("+AirDeskContract.WorkspaceEntry.COLUMN_WORKSPACE_ID+")"+
           " )";
    private static  final String SQL_CREATE_WHS_TABLE =
            "CREATE TABLE " + AirDeskContract.WorkspaceHasSubscriptionsEntry.TABLE_NAME + " (" +
                    AirDeskContract.WorkspaceHasSubscriptionsEntry.COLUMN_WHS_ID + " INTEGER PRIMARY KEY" + COMMA_SEP +
                    AirDeskContract.WorkspaceHasSubscriptionsEntry.COLUMN_WHS_UID + INTEGER_TYPE + COMMA_SEP +
                    AirDeskContract.WorkspaceHasSubscriptionsEntry.COLUMN_WHS_WSID + INTEGER_TYPE + COMMA_SEP +
                    "FOREIGN KEY("+AirDeskContract.WorkspaceHasSubscriptionsEntry.COLUMN_WHS_UID+")"+ " REFERENCES "+
                    AirDeskContract.UserEntry.TABLE_NAME+"("+AirDeskContract.UserEntry.COLUMN_USER_ID+")"+COMMA_SEP+
                    "FOREIGN KEY("+AirDeskContract.WorkspaceHasSubscriptionsEntry.COLUMN_WHS_WSID+")"+ " REFERENCES "+
                    AirDeskContract.WorkspaceEntry.TABLE_NAME+"("+AirDeskContract.WorkspaceEntry.COLUMN_WORKSPACE_ID+")"+
                    " )";

    private static  final String SQL_CREATE_WHF_TABLE =
            "CREATE TABLE " + AirDeskContract.WorkspaceHasFileEntry.TABLE_NAME + " (" +
                    AirDeskContract.WorkspaceHasFileEntry.COLUMN_WHF_ID + " INTEGER PRIMARY KEY" + COMMA_SEP +
                    AirDeskContract.WorkspaceHasFileEntry.COLUMN_WHF_FID + INTEGER_TYPE + COMMA_SEP +
                    AirDeskContract.WorkspaceHasFileEntry.COLUMN_WHF_WSID + INTEGER_TYPE + COMMA_SEP +
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

    public ArrayList<Workspace> getUserWorkSpaces(int uid){
        Cursor cUHW;
        SQLiteDatabase db = this.getReadableDatabase();
        String SqlUHW= "SELECT * FROM "+AirDeskContract.UserHasWorkspaceEntry.TABLE_NAME;
        ArrayList<Workspace> wid=new ArrayList<Workspace>();
        cUHW=db.rawQuery(SqlUHW,null);

        try{
            cUHW.moveToFirst();
            while(!cUHW.isAfterLast()){
                if(uid==cUHW.getInt(cUHW.getColumnIndexOrThrow(AirDeskContract.UserHasWorkspaceEntry.COLUMN_UHW_UID))){
                   wid.add(this.getWorkspace(cUHW.getInt(cUHW.getColumnIndexOrThrow(AirDeskContract.UserHasWorkspaceEntry.COLUMN_UHW_WSID))));
                }
                cUHW.moveToNext();
            }
            cUHW.close();
            return wid;
        } catch(WorkspaceDoesNotExistException w){
             cUHW.close();
            throw w;
        } catch(UserDoesNotExistException u){
             cUHW.close();
            throw u;
        }
    }

    public ArrayList<Workspace> getUserSubscriptions(int uid){
        Cursor cUHW;
        SQLiteDatabase db = this.getReadableDatabase();
        String SqlUHW= "SELECT * FROM "+AirDeskContract.WorkspaceHasSubscriptionsEntry.TABLE_NAME;
        ArrayList<Workspace> wid=new ArrayList<Workspace>();
        cUHW=db.rawQuery(SqlUHW,null);

        try{
            cUHW.moveToFirst();
            while(!cUHW.isAfterLast()){
                if(uid==cUHW.getInt(cUHW.getColumnIndexOrThrow(AirDeskContract.WorkspaceHasSubscriptionsEntry.COLUMN_WHS_UID))){
                    wid.add(this.getWorkspace(cUHW.getInt(cUHW.getColumnIndexOrThrow(AirDeskContract.WorkspaceHasSubscriptionsEntry.COLUMN_WHS_WSID))));
                }
                cUHW.moveToNext();
            }
            cUHW.close();
            return wid;
        } catch(WorkspaceDoesNotExistException w){
            cUHW.close();
            throw w;
        } catch(UserDoesNotExistException u){
            cUHW.close();
            throw u;
        }
    }

    public ArrayList<User> getWorkspaceSubscribers(int wid) {
        Cursor c;
        SQLiteDatabase db = this.getReadableDatabase();
        String SqlWHS= "SELECT * FROM "+AirDeskContract.WorkspaceHasSubscriptionsEntry.TABLE_NAME;
        ArrayList<User> subscribers = new ArrayList<User>();
        c=db.rawQuery(SqlWHS,null);

        try{
            c.moveToFirst();
            while(!c.isAfterLast()){
                if(wid==c.getInt(c.getColumnIndexOrThrow(AirDeskContract.WorkspaceHasSubscriptionsEntry.COLUMN_WHS_WSID))){
                    subscribers.add(this.getUser(c.getInt(c.getColumnIndexOrThrow(AirDeskContract.WorkspaceHasSubscriptionsEntry.COLUMN_WHS_UID))));
                }
                c.moveToNext();
            }
            c.close();
            return subscribers;
        } catch(UserDoesNotExistException u){
            c.close();
            throw u;
        }
    }

    public ArrayList<File> getWorkspaceFiles(int wid) {
        Cursor c;
        SQLiteDatabase db = this.getReadableDatabase();
        String SqlWHF= "SELECT * FROM " + AirDeskContract.WorkspaceHasFileEntry.TABLE_NAME;
        ArrayList<File> files = new ArrayList<File>();
        c=db.rawQuery(SqlWHF,null);

        try{
            c.moveToFirst();
            while(!c.isAfterLast()){
                if(wid ==c.getInt(c.getColumnIndexOrThrow(AirDeskContract.WorkspaceHasFileEntry.COLUMN_WHF_WSID))){
                    files.add(this.getFile(c.getInt(c.getColumnIndexOrThrow(AirDeskContract.WorkspaceHasFileEntry.COLUMN_WHF_FID))));
                }
                c.moveToNext();
            }
            c.close();
            return files;
        } catch(FileDoesNotExistException f){
            c.close();
            throw f;
        } catch(UserDoesNotExistException u){
            c.close();
            throw u;
        }
    }

    public int getUserId(String username){
        SQLiteDatabase db = this.getReadableDatabase();
        String SqlUid= "SELECT * FROM "+AirDeskContract.UserEntry.TABLE_NAME;
        Cursor cuid = db.rawQuery(SqlUid, null);
        cuid.moveToFirst();
        while(!cuid.isAfterLast()){
            String cusername=cuid.getString(cuid.getColumnIndexOrThrow(AirDeskContract.UserEntry.COLUMN_USER_NAME));
            if(cusername.equals(username)){
                int userid = cuid.getInt(cuid.getColumnIndexOrThrow(AirDeskContract.UserEntry.COLUMN_USER_ID));
                cuid.close();
                return userid;
            }
            cuid.moveToNext();
        }
        cuid.close();
        throw new UserDoesNotExistException(username);
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
                c.close();
                return new User(cuid,cuname,cpass);

            }
            c.moveToNext();
        }
        c.close();
        throw new UserDoesNotExistException(uid);

    }

    public int getWorkspaceId(String wsname,int ownerID) {

        ArrayList<Workspace> wlist= new ArrayList<>(this.getUserWorkSpaces(ownerID));
        for(Workspace w:wlist){
            if(w.getWsname().equals(wsname)){
                return w.getWsid();}
        }

        throw new WorkspaceDoesNotExistException(wsname);
    }

    public Workspace getWorkspace(int wid) {
        SQLiteDatabase db = this.getReadableDatabase();

        String wssql = "SELECT * FROM " + AirDeskContract.WorkspaceEntry.TABLE_NAME;
        Cursor c = db.rawQuery(wssql, null);
        try {

            c.moveToFirst();
            String wsname, wstags;
            User wsowner;
            int wsquota, wspublic;
            boolean isPublic;
            while (!c.isAfterLast()) {
                if (wid == c.getInt(c.getColumnIndexOrThrow(AirDeskContract.WorkspaceEntry.COLUMN_WORKSPACE_ID))) {
                    wsname = c.getString(c.getColumnIndexOrThrow(AirDeskContract.WorkspaceEntry.COLUMN_WORKSPACE_NAME));
                    wsquota = c.getInt(c.getColumnIndexOrThrow(AirDeskContract.WorkspaceEntry.COLUMN_WORKSPACE_QUOTA));
                    wspublic = c.getInt(c.getColumnIndexOrThrow(AirDeskContract.WorkspaceEntry.COLUMN_WORKSPACE_PUBLIC));
                    wsowner = getWorkspaceOwner(wid);
                    c.close();
                    if(wspublic==0)
                        isPublic = false;
                    else isPublic = true;
                    return new Workspace(wid, wsname, wsquota, isPublic, wsowner); //TRUE AIN T TRUE
                }
                c.moveToNext();
            }
            c.close();
            throw new WorkspaceDoesNotExistException(wid);
        } catch(UserDoesNotExistException u){c.close(); throw u;}

    }

    public File getFile(int fid) {
        Cursor c = null;
        try{
            SQLiteDatabase db = this.getReadableDatabase();

            String sql= "SELECT * FROM "+ AirDeskContract.FileEntry.TABLE_NAME;
            c = db.rawQuery(sql,null);
            c.moveToFirst();
            File f = null;
            int cFid = -1;
            String cFTitle, cContent;
            while(!c.isAfterLast()){
                cFid = c.getInt(c.getColumnIndexOrThrow(AirDeskContract.FileEntry.COLUMN_FILE_ID));
                if(cFid == fid) {
                    cFTitle = c.getString(c.getColumnIndexOrThrow(AirDeskContract.FileEntry.COLUMN_FILE_TITLE));
                    cContent = c.getString(c.getColumnIndexOrThrow(AirDeskContract.FileEntry.COLUMN_FILE_CONTENT));

                    c.close();
                    return new File(cFid,cFTitle,cContent, this.getWorkspace(this.getWorkspaceByFile(cFid)));

                }
                c.moveToNext();
            }
            c.close();
            throw new FileDoesNotExistException(fid);}
        catch(WorkspaceDoesNotExistException w){c.close();throw w;}
        catch(FileDoesNotExistException f){c.close();throw f;}
        catch(UserDoesNotExistException u){c.close();throw u;}
    }

    public User getWorkspaceOwner(int wid) {
        SQLiteDatabase db = this.getReadableDatabase();

        String wssql = "SELECT * FROM " + AirDeskContract.UserHasWorkspaceEntry.TABLE_NAME;
        Cursor c = db.rawQuery(wssql, null);
        try {

            c.moveToFirst();
            while (!c.isAfterLast()) {
                if (wid == c.getInt(c.getColumnIndexOrThrow(AirDeskContract.UserHasWorkspaceEntry.COLUMN_UHW_WSID))) {
                    User u = getUser(c.getInt(c.getColumnIndexOrThrow(AirDeskContract.UserHasWorkspaceEntry.COLUMN_UHW_UID)));
                    c.close();
                    return u;
                }
                c.moveToNext();
            }

        } catch(UserDoesNotExistException u) {c.close(); throw u;}
        c.close();
        return null;
    }

    public ArrayList<String> getWorkspaceTags(int wid){
        Cursor cUHW;
        SQLiteDatabase db = this.getReadableDatabase();
        String SqlUHW= "SELECT * FROM "+AirDeskContract.WorkspaceEntry.TABLE_NAME;
        ArrayList<String> tlist = new ArrayList<String>();
        String wtags = new String();
        String delims=",";
        cUHW=db.rawQuery(SqlUHW,null);

        try{
            cUHW.moveToFirst();
            while(!cUHW.isAfterLast()){
                if(wid==cUHW.getInt(cUHW.getColumnIndexOrThrow(AirDeskContract.WorkspaceEntry.COLUMN_WORKSPACE_ID))){
                    wtags=cUHW.getString(cUHW.getColumnIndexOrThrow(AirDeskContract.WorkspaceEntry.COLUMN_WORKSPACE_TAGS));
                }
                cUHW.moveToNext();
            }
            cUHW.close();
            String[] temp=wtags.split(delims);
            for(String s:temp){
                tlist.add(s);}
            return tlist;
        } catch(WorkspaceDoesNotExistException w){
            cUHW.close();
            throw w;
        } catch(UserDoesNotExistException u){
            cUHW.close();
            throw u;
        }
    }



    public User searchUser(String username){
        SQLiteDatabase db= this.getReadableDatabase();

        String Sql= "SELECT * FROM "+AirDeskContract.UserEntry.TABLE_NAME;
        Cursor c = db.rawQuery(Sql,null);
        c.moveToFirst();
        while(!c.isAfterLast()){
            String cuname=c.getString(c.getColumnIndexOrThrow(AirDeskContract.UserEntry.COLUMN_USER_NAME));
            if(cuname.equals(username)){
                int cuid=c.getInt(c.getColumnIndexOrThrow(AirDeskContract.UserEntry.COLUMN_USER_ID));
                String cpass=c.getString(c.getColumnIndexOrThrow(AirDeskContract.UserEntry.COLUMN_USER_PASSWORD));
                c.close();
                return new User(cuid,cuname,cpass);
            }
            c.moveToNext();
        }
        c.close();
        throw new UserDoesNotExistException(username);
    }

    public Workspace searchWorkspace(String wsname, String username) {
        int wsid, userid;
        try{userid = getUserId(username);} catch(UserDoesNotExistException u) {throw u;}
        try{wsid = getWorkspaceId(wsname,getUserId(username));} catch(UserDoesNotExistException u) {throw u;}
        Workspace workspace;
        SQLiteDatabase db= this.getReadableDatabase();

        String sql= "SELECT * FROM " + AirDeskContract.UserHasWorkspaceEntry.TABLE_NAME;
        Cursor c = db.rawQuery(sql,null);
        c.moveToFirst();
        while(!c.isAfterLast()){
            if(    (userid == c.getColumnIndexOrThrow(AirDeskContract.UserHasWorkspaceEntry.COLUMN_UHW_UID))
                && (wsid == c.getColumnIndexOrThrow(AirDeskContract.UserHasWorkspaceEntry.COLUMN_UHW_WSID))) {
                    try{workspace = getWorkspace(wsid);} catch(UserDoesNotExistException u) {c.close(); throw u;}
                    c.close();
                    return workspace;
            }
            c.moveToNext();
        }
        c.close();
        throw new UserDoesNotExistException(username);
    }

    public boolean userExists(int uid){
        SQLiteDatabase db = this.getReadableDatabase();
        String Sql= "SELECT * FROM "+AirDeskContract.UserEntry.TABLE_NAME;

        Cursor c = db.rawQuery(Sql, null);

        c.moveToFirst();

        while (!c.isAfterLast()){
            int dbUid = c.getInt(c.getColumnIndexOrThrow(AirDeskContract.UserEntry.COLUMN_USER_ID));

            if(dbUid==uid) {
                //if user exists return true
                c.close();
                return true;
            }
            c.moveToNext();
        }
        //returns false if no user with that username exists
        c.close();
        return false;
    }

    public boolean userExists(String username){
        SQLiteDatabase db = this.getReadableDatabase();
        String Sql= "SELECT * FROM "+AirDeskContract.UserEntry.TABLE_NAME;

        Cursor c = db.rawQuery(Sql, null);

        c.moveToFirst();

        while (!c.isAfterLast()){
            String dbUserName = c.getString(c.getColumnIndexOrThrow(AirDeskContract.UserEntry.COLUMN_USER_NAME));

            if(dbUserName.equals(username)) {
                //if user exists return true
                c.close();
                return true;
            }
            c.moveToNext();
        }
        //returns false if no user with that username exists
        c.close();
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
            if(dbUserName.equals(username)&&dbUserPass.equals(password)) {
                //if user exists with pass return true
                c.close();
                return true;
            }
            c.moveToNext();
        }
        //returns false if no user with that username and password exists
        c.close();
        return false;
    }

    public boolean workspaceExists(String wname, String username){

        int uid;
        try {
            uid = this.getUserId(username);
        }catch(AirDeskException u){ throw u;}

        ArrayList<Workspace> wid=this.getUserWorkSpaces(uid);


            for(Workspace w: wid){
                if(w.getWsname().equals(wname)){

                    return true;
                }

        }

        return false;
    }
    public boolean workspaceExists(int wid){
        SQLiteDatabase db = this.getReadableDatabase();
        String Sql= "SELECT * FROM "+AirDeskContract.WorkspaceEntry.TABLE_NAME;

        Cursor c = db.rawQuery(Sql, null);

        c.moveToFirst();

        while (!c.isAfterLast()){
            int dbWid = c.getInt(c.getColumnIndexOrThrow(AirDeskContract.WorkspaceEntry.COLUMN_WORKSPACE_ID));

            if(dbWid==wid) {
                //if user exists return true
                c.close();
                return true;
            }
            c.moveToNext();
        }
        //returns false if no user with that username exists
        c.close();
        return false;

    }
    public boolean fileExists(String title,int wid) {
        ArrayList<File> filelist;
        try {
            filelist = getWorkspaceFiles(wid);
        } catch(FileDoesNotExistException f){
            throw f;
        } catch(WorkspaceDoesNotExistException w){
            throw w;
        }
        for (File f : filelist) {
            if (f.getFiletitle().equals(title)) {
                return true;
            }
        }
        return false;
    }

    public boolean fileExists(int fid,int wid) {
        ArrayList<File> filelist;
        try {
            filelist = getWorkspaceFiles(wid);
        } catch(FileDoesNotExistException f){
            throw f;
        } catch(WorkspaceDoesNotExistException w){
            throw w;
        }
        for (File f : filelist) {
            if (f.getFileid()==fid) {
                return true;
            }
        }
        return false;
    }


    public int getWorkspaceByFile(int fid){
        SQLiteDatabase db= this.getReadableDatabase();

        String Sql= "SELECT * FROM "+AirDeskContract.WorkspaceHasFileEntry.TABLE_NAME;
        Cursor c = db.rawQuery(Sql,null);
        c.moveToFirst();

        int wid=-1;

        while(!c.isAfterLast()){
            int cfid = c.getInt(c.getColumnIndexOrThrow(AirDeskContract.WorkspaceHasFileEntry.COLUMN_WHF_FID));
            if(cfid == fid) {
                wid=c.getInt(c.getColumnIndexOrThrow(AirDeskContract.WorkspaceHasFileEntry.COLUMN_WHF_WSID));
                c.close();
                return wid;

            }
            c.moveToNext();
        }
        c.close();
        throw new FileDoesNotExistException(fid);

    }

    public void addUser(String username, String password) {

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
        try {
            if(workspaceExists(wname, username))
                throw new WorkspaceAlreadyExistsException(wname);
        } catch (UserDoesNotExistException u) {throw u;}

        int uid;
       try {
          uid = getUserId(username);
       } catch (UserDoesNotExistException u) {throw u;}

            SQLiteDatabase db = this.getWritableDatabase();

            db.beginTransaction();
            try {
                int gen = generator();
                // Create a new map of values, where column names are the keys
                ContentValues values = new ContentValues();
                values.put(AirDeskContract.WorkspaceEntry.COLUMN_WORKSPACE_ID, gen);
                values.put(AirDeskContract.WorkspaceEntry.COLUMN_WORKSPACE_NAME, wname);
                values.put(AirDeskContract.WorkspaceEntry.COLUMN_WORKSPACE_PUBLIC, pub);
                values.put(AirDeskContract.WorkspaceEntry.COLUMN_WORKSPACE_QUOTA, quota);
                values.put(AirDeskContract.WorkspaceEntry.COLUMN_WORKSPACE_TAGS, tags);

                // Insert the new row, returning the primary key value of the new row
                long newRowId;
                newRowId = db.insert(
                        AirDeskContract.WorkspaceEntry.TABLE_NAME,
                        null,
                        values);


                ContentValues uhwValues = new ContentValues();
                uhwValues.put(AirDeskContract.UserHasWorkspaceEntry.COLUMN_UHW_ID, generator());
                uhwValues.put(AirDeskContract.UserHasWorkspaceEntry.COLUMN_UHW_UID, uid);
                uhwValues.put(AirDeskContract.UserHasWorkspaceEntry.COLUMN_UHW_WSID, gen);
                long newRowId2;
                newRowId2 = db.insert(
                        AirDeskContract.UserHasWorkspaceEntry.TABLE_NAME,
                        null,
                        uhwValues);
                db.setTransactionSuccessful();
            } finally {
            db.endTransaction();
            }

    }

    public void addFile(String owner, String client, String wname, String title, String content){
        try {
            if(!workspaceExists(wname,owner))
                throw new WorkspaceDoesNotExistException(wname);
        } catch (UserDoesNotExistException u) {throw u;}

        int uid;
        try {
            uid = getUserId(client);
        } catch (UserDoesNotExistException u) {throw u;}
        try {
            if(fileExists(title,getWorkspaceId(wname, getUserId(owner))))
                throw new FileAlreadyExistsException(title);
        } catch (UserDoesNotExistException u) {throw u;}
        SQLiteDatabase db = this.getWritableDatabase();

        db.beginTransaction();
        try {
            int gen = generator();
            // Create a new map of values, where column names are the keys
            ContentValues values = new ContentValues();
            values.put(AirDeskContract.FileEntry.COLUMN_FILE_ID, gen);
            values.put(AirDeskContract.FileEntry.COLUMN_FILE_TITLE,title);
            values.put(AirDeskContract.FileEntry.COLUMN_FILE_CONTENT,content);

            // Insert the new row, returning the primary key value of the new row
            long newRowId;
            newRowId = db.insert(
                    AirDeskContract.FileEntry.TABLE_NAME,
                    null,
                    values);


            ContentValues uhwValues = new ContentValues();
            uhwValues.put(AirDeskContract.WorkspaceHasFileEntry.COLUMN_WHF_ID, generator());
            uhwValues.put(AirDeskContract.WorkspaceHasFileEntry.COLUMN_WHF_FID,gen);
            uhwValues.put(AirDeskContract.WorkspaceHasFileEntry.COLUMN_WHF_WSID,getWorkspaceId(wname, getUserId(owner)));
            long newRowId2;
            newRowId2 = db.insert(
                    AirDeskContract.WorkspaceHasFileEntry.TABLE_NAME,
                    null,
                    uhwValues);
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    public void addSubscriberToWorkspace(int wid, int uid) {
        User user;
        try{
            user = getUser(uid);
        } catch(UserDoesNotExistException u) {throw u;}
        ArrayList<User> userList;
        try{
            userList = new ArrayList<User>(getWorkspaceSubscribers(wid));
            for(User u: userList) {
                if(u.getUsername().equals(user.getUsername()))
                    throw new UserAlreadyExistsException(user.getUsername());
            }
        } catch(UserDoesNotExistException u) {throw u;}

        SQLiteDatabase db = this.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(AirDeskContract.WorkspaceHasSubscriptionsEntry.COLUMN_WHS_ID, generator());
        values.put(AirDeskContract.WorkspaceHasSubscriptionsEntry.COLUMN_WHS_WSID, wid);
        values.put(AirDeskContract.WorkspaceHasSubscriptionsEntry.COLUMN_WHS_UID, uid);

        // Insert the new row, returning the primary key value of the new row
        long newRowId;
        newRowId = db.insert(
                AirDeskContract.WorkspaceHasSubscriptionsEntry.TABLE_NAME,
                null,
                values);
    }

    public void changeWorkspaceData(Workspace workspace) {
        int wid = workspace.getWsid();
        Workspace tworkspace;
        try {
            tworkspace = getWorkspace(wid);
        } catch (AirDeskException e) {throw e;}
        if(workspaceExists(workspace.getWsname(), workspace.getWsowner().getUsername()) && !(workspace.getWsname().equals(tworkspace.getWsname())))
            throw new WorkspaceAlreadyExistsException(workspace.getWsname());

        SQLiteDatabase db = this.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(AirDeskContract.WorkspaceEntry.COLUMN_WORKSPACE_NAME, workspace.getWsname());
        values.put(AirDeskContract.WorkspaceEntry.COLUMN_WORKSPACE_PUBLIC, workspace.isWspublic());
        values.put(AirDeskContract.WorkspaceEntry.COLUMN_WORKSPACE_QUOTA, workspace.getWsquota());
        values.put(AirDeskContract.WorkspaceEntry.COLUMN_WORKSPACE_TAGS, workspace.getWstags().toString());
        //String sql = "UPDATE "+ AirDeskContract"COMPANY SET ADDRESS = 'Texas' WHERE ID = 6;"
        //db.rawQuery(sql, null);
        db.update(AirDeskContract.WorkspaceEntry.TABLE_NAME, values, AirDeskContract.WorkspaceEntry.COLUMN_WORKSPACE_ID + " =" + workspace.getWsid(), null);

    }

    public boolean removeSubscriptionFromUser(int wid,int uid){
        if(!workspaceExists(wid)){
            throw new WorkspaceDoesNotExistException(wid);
        }
        if(!userExists(uid)){
            throw new UserDoesNotExistException(uid);
        }

        SQLiteDatabase db= this.getWritableDatabase();
        String whereclause = AirDeskContract.WorkspaceHasSubscriptionsEntry.COLUMN_WHS_WSID + " = "+ wid +" and "+
                            AirDeskContract.WorkspaceHasSubscriptionsEntry.COLUMN_WHS_UID+" = "+ uid;
               return db.delete(AirDeskContract.WorkspaceHasSubscriptionsEntry.TABLE_NAME,whereclause,null)>0;
    }

    public boolean removeFile(int fid,int wid){
        if(!fileExists(fid, wid)){
            throw new FileDoesNotExistException(fid);
        }
        if(!workspaceExists(wid)){
            throw new WorkspaceDoesNotExistException(wid);
        }
        SQLiteDatabase db= this.getWritableDatabase();

            String wcFE = AirDeskContract.FileEntry.COLUMN_FILE_ID+" = "+fid;

            boolean d1=db.delete(AirDeskContract.FileEntry.TABLE_NAME,wcFE,null)>0;

            return d1;





    }

    public boolean removeWorkspace(int wid){
        if(!workspaceExists(wid)){
            throw new WorkspaceDoesNotExistException(wid);
        }
        SQLiteDatabase db=this.getWritableDatabase();

            String wcWE=AirDeskContract.WorkspaceEntry.COLUMN_WORKSPACE_ID+" = "+ wid;
            return db.delete(AirDeskContract.WorkspaceEntry.TABLE_NAME,wcWE,null)>0;

    }

    public int generator(){
        Random r=new Random();

        return r.nextInt();
    }
}