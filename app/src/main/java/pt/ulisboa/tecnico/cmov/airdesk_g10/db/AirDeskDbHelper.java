package pt.ulisboa.tecnico.cmov.airdesk_g10.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Random;

import pt.ulisboa.tecnico.cmov.airdesk_g10.core.File;
import pt.ulisboa.tecnico.cmov.airdesk_g10.core.Subscription;
import pt.ulisboa.tecnico.cmov.airdesk_g10.core.User;
import pt.ulisboa.tecnico.cmov.airdesk_g10.core.UserSubscriptions;
import pt.ulisboa.tecnico.cmov.airdesk_g10.core.UserWorkspaces;
import pt.ulisboa.tecnico.cmov.airdesk_g10.core.Workspace;
import pt.ulisboa.tecnico.cmov.airdesk_g10.core.WorkspaceFiles;
import pt.ulisboa.tecnico.cmov.airdesk_g10.core.WorkspaceSubscriptions;
import pt.ulisboa.tecnico.cmov.airdesk_g10.exceptions.AirDeskException;
import pt.ulisboa.tecnico.cmov.airdesk_g10.exceptions.FileAlreadyExistsException;
import pt.ulisboa.tecnico.cmov.airdesk_g10.exceptions.FileDoesNotExistException;
import pt.ulisboa.tecnico.cmov.airdesk_g10.exceptions.NoWorkspaceWithTagException;
import pt.ulisboa.tecnico.cmov.airdesk_g10.exceptions.SubscriptionDoesNotExistException;
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
                    AirDeskContract.UserEntry.COLUMN_USER_EMAIL + TEXT_TYPE + COMMA_SEP +
                    AirDeskContract.UserEntry.COLUMN_USER_PASSWORD + TEXT_TYPE + COMMA_SEP +
                    AirDeskContract.UserEntry.COLUMN_USER_NICKNAME + TEXT_TYPE +
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
                    AirDeskContract.WorkspaceEntry.COLUMN_WORKSPACE_TAGS + TEXT_TYPE + COMMA_SEP +
                    AirDeskContract.WorkspaceEntry.COLUMN_WORKSPACE_READ_PERM + BOOLEAN_TYPE + COMMA_SEP +
                    AirDeskContract.WorkspaceEntry.COLUMN_WORKSPACE_WRITE_PERM + BOOLEAN_TYPE + COMMA_SEP +
                    AirDeskContract.WorkspaceEntry.COLUMN_WORKSPACE_CREATE_PERM + BOOLEAN_TYPE + COMMA_SEP +
                    AirDeskContract.WorkspaceEntry.COLUMN_WORKSPACE_DELETE_PERM + BOOLEAN_TYPE +
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
                    AirDeskContract.WorkspaceHasSubscriptionsEntry.COLUMN_WHS_READ_PERM + BOOLEAN_TYPE + COMMA_SEP +
                    AirDeskContract.WorkspaceHasSubscriptionsEntry.COLUMN_WHS_WRITE_PERM + BOOLEAN_TYPE + COMMA_SEP +
                    AirDeskContract.WorkspaceHasSubscriptionsEntry.COLUMN_WHS_CREATE_PERM + BOOLEAN_TYPE + COMMA_SEP +
                    AirDeskContract.WorkspaceHasSubscriptionsEntry.COLUMN_WHS_DELETE_PERM + BOOLEAN_TYPE + COMMA_SEP +
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

    public UserWorkspaces getUserWorkSpaces(int uid){
        Cursor cUHW;
        SQLiteDatabase db = this.getReadableDatabase();
        String SqlUHW= "SELECT * FROM "+AirDeskContract.UserHasWorkspaceEntry.TABLE_NAME;
        ArrayList<Workspace> wid=new ArrayList<Workspace>();
        cUHW=db.rawQuery(SqlUHW,null);

        User u;
        try {
            u = getUser(uid);
        }catch(AirDeskException w){
            throw w;
        }


        try{
            cUHW.moveToFirst();
            while(!cUHW.isAfterLast()){
                if(uid==cUHW.getInt(cUHW.getColumnIndexOrThrow(AirDeskContract.UserHasWorkspaceEntry.COLUMN_UHW_UID))){
                   wid.add(this.getWorkspace(cUHW.getInt(cUHW.getColumnIndexOrThrow(AirDeskContract.UserHasWorkspaceEntry.COLUMN_UHW_WSID))));
                }
                cUHW.moveToNext();
            }
            cUHW.close();
            return new UserWorkspaces(u, wid);
        } catch(AirDeskException w){
             cUHW.close();
            throw w;
        }
    }

    public Subscription getSubscription(int wsid, int uid){
        Cursor cUHW;
        SQLiteDatabase db = this.getReadableDatabase();
        String SqlUHW= "SELECT * FROM "+AirDeskContract.WorkspaceHasSubscriptionsEntry.TABLE_NAME;
        cUHW=db.rawQuery(SqlUHW,null);
        User user;
        try {
            user = getUser(uid);
        }catch (AirDeskException u) {throw u;}

        Workspace ws;
        try {
            ws = getWorkspace(wsid);
        }catch (AirDeskException u) {throw u;}

        try{
            cUHW.moveToFirst();
            while(!cUHW.isAfterLast()){
                if((uid==cUHW.getInt(cUHW.getColumnIndexOrThrow(AirDeskContract.WorkspaceHasSubscriptionsEntry.COLUMN_WHS_UID))) &&
                        (wsid==cUHW.getInt(cUHW.getColumnIndexOrThrow(AirDeskContract.WorkspaceHasSubscriptionsEntry.COLUMN_WHS_WSID)))){
                    int wsreadPerm = cUHW.getInt(cUHW.getColumnIndexOrThrow(AirDeskContract.WorkspaceHasSubscriptionsEntry.COLUMN_WHS_READ_PERM));
                    int wswritePerm = cUHW.getInt(cUHW.getColumnIndexOrThrow(AirDeskContract.WorkspaceHasSubscriptionsEntry.COLUMN_WHS_WRITE_PERM));
                    int wscreatePerm = cUHW.getInt(cUHW.getColumnIndexOrThrow(AirDeskContract.WorkspaceHasSubscriptionsEntry.COLUMN_WHS_CREATE_PERM));
                    int wsdeletePerm = cUHW.getInt(cUHW.getColumnIndexOrThrow(AirDeskContract.WorkspaceHasSubscriptionsEntry.COLUMN_WHS_DELETE_PERM));
                    boolean readPerm, writePerm, createPerm, deletePerm;
                    if(wsreadPerm==0)
                        readPerm = false;
                    else readPerm = true;
                    if(wswritePerm==0)
                        writePerm = false;
                    else writePerm = true;
                    if(wscreatePerm==0)
                        createPerm = false;
                    else createPerm = true;
                    if(wsdeletePerm==0)
                        deletePerm = false;
                    else deletePerm = true;
                    cUHW.close();
                    return new Subscription(user, ws, readPerm, writePerm, createPerm, deletePerm);
                }
                cUHW.moveToNext();
            }
            cUHW.close();
            throw new SubscriptionDoesNotExistException(uid, wsid);
        } catch(AirDeskException w){
            cUHW.close();
            throw w;
        }
    }

    public UserSubscriptions getUserSubscriptions(int uid){
        Cursor cUHW;
        SQLiteDatabase db = this.getReadableDatabase();
        String SqlUHW= "SELECT * FROM "+AirDeskContract.WorkspaceHasSubscriptionsEntry.TABLE_NAME;
        ArrayList<Subscription> wid=new ArrayList<Subscription>();
        cUHW=db.rawQuery(SqlUHW,null);
        User user;
        try {
            user = getUser(uid);
        }catch (AirDeskException u) {throw u;}

        try{
            cUHW.moveToFirst();
            while(!cUHW.isAfterLast()){
                if(uid==cUHW.getInt(cUHW.getColumnIndexOrThrow(AirDeskContract.WorkspaceHasSubscriptionsEntry.COLUMN_WHS_UID))){
                    int wsreadPerm = cUHW.getInt(cUHW.getColumnIndexOrThrow(AirDeskContract.WorkspaceHasSubscriptionsEntry.COLUMN_WHS_READ_PERM));
                    int wswritePerm = cUHW.getInt(cUHW.getColumnIndexOrThrow(AirDeskContract.WorkspaceHasSubscriptionsEntry.COLUMN_WHS_WRITE_PERM));
                    int wscreatePerm = cUHW.getInt(cUHW.getColumnIndexOrThrow(AirDeskContract.WorkspaceHasSubscriptionsEntry.COLUMN_WHS_CREATE_PERM));
                    int wsdeletePerm = cUHW.getInt(cUHW.getColumnIndexOrThrow(AirDeskContract.WorkspaceHasSubscriptionsEntry.COLUMN_WHS_DELETE_PERM));
                    boolean readPerm, writePerm, createPerm, deletePerm;
                    if(wsreadPerm==0)
                        readPerm = false;
                    else readPerm = true;
                    if(wswritePerm==0)
                        writePerm = false;
                    else writePerm = true;
                    if(wscreatePerm==0)
                        createPerm = false;
                    else createPerm = true;
                    if(wsdeletePerm==0)
                        deletePerm = false;
                    else deletePerm = true;

                    wid.add(new Subscription(user, getWorkspace(cUHW.getInt(cUHW.getColumnIndexOrThrow(AirDeskContract.WorkspaceHasSubscriptionsEntry.COLUMN_WHS_WSID))), readPerm, writePerm, createPerm, deletePerm));
                }
                cUHW.moveToNext();
            }
            cUHW.close();
            return new UserSubscriptions(user,wid);
        } catch(AirDeskException w){
            cUHW.close();
            throw w;
        }
    }

    public WorkspaceSubscriptions getWorkspaceSubscribers(int wid) {
        Cursor c;
        SQLiteDatabase db = this.getReadableDatabase();
        String SqlWHS= "SELECT * FROM "+AirDeskContract.WorkspaceHasSubscriptionsEntry.TABLE_NAME;
        ArrayList<User> subscribers = new ArrayList<User>();
        c=db.rawQuery(SqlWHS,null);
        Workspace ws;
        try{
            ws = getWorkspace(wid);
        }catch( AirDeskException u){
            throw u;
        }

        try{
            c.moveToFirst();
            while(!c.isAfterLast()){
                if(wid==c.getInt(c.getColumnIndexOrThrow(AirDeskContract.WorkspaceHasSubscriptionsEntry.COLUMN_WHS_WSID))){
                    subscribers.add(this.getUser(c.getInt(c.getColumnIndexOrThrow(AirDeskContract.WorkspaceHasSubscriptionsEntry.COLUMN_WHS_UID))));
                }
                c.moveToNext();
            }
            c.close();
            return new WorkspaceSubscriptions(ws, subscribers);
        } catch(AirDeskException u){
            c.close();
            throw u;
        }
    }

    public ArrayList<Workspace> getWorkspaceByTags(String tag){
        SQLiteDatabase db= this.getReadableDatabase();
        String query= "SELECT * FROM "+AirDeskContract.WorkspaceEntry.TABLE_NAME;
        Cursor c= db.rawQuery(query, null);
        c.moveToFirst();
        String delim = ",";
        String tags[];
        ArrayList<Workspace>wlist=new ArrayList<>();
        try{
            while(!c.isAfterLast()) {
                int check=c.getInt(c.getColumnIndexOrThrow(AirDeskContract.WorkspaceEntry.COLUMN_WORKSPACE_PUBLIC));
                if(check==1){
                    int wid= c.getInt(c.getColumnIndexOrThrow(AirDeskContract.WorkspaceEntry.COLUMN_WORKSPACE_ID));
                    ArrayList<String> wtags=this.getWorkspaceTags(wid);
                    for(String s: wtags){
                        if(s.equals(tag)){
                            wlist.add(this.getWorkspace(wid));
                        }
                    }

                }
                c.moveToNext();
            }
            c.close();
            if(wlist.isEmpty()){
                throw new NoWorkspaceWithTagException(tag);
            }
            return wlist;
        }
        catch (AirDeskException a){
            c.close();
            throw a;
        }
    }

    public WorkspaceFiles getWorkspaceFiles(int wid) {
        Cursor c;
        SQLiteDatabase db = this.getReadableDatabase();
        String SqlWHF= "SELECT * FROM " + AirDeskContract.WorkspaceHasFileEntry.TABLE_NAME;
        ArrayList<File> files = new ArrayList<File>();
        c=db.rawQuery(SqlWHF,null);

        Workspace ws;
        try{
            ws = getWorkspace(wid);
        }catch(AirDeskException u){
            throw  u;
        }

        try{
            c.moveToFirst();
            while(!c.isAfterLast()){
                if(wid ==c.getInt(c.getColumnIndexOrThrow(AirDeskContract.WorkspaceHasFileEntry.COLUMN_WHF_WSID))){
                    files.add(this.getFile(c.getInt(c.getColumnIndexOrThrow(AirDeskContract.WorkspaceHasFileEntry.COLUMN_WHF_FID))));
                }
                c.moveToNext();
            }
            c.close();
            return new WorkspaceFiles(ws,files);
        } catch(AirDeskException f){
            c.close();
            throw f;
        }
    }

    public int getUserId(String username){
        SQLiteDatabase db = this.getReadableDatabase();
        String SqlUid= "SELECT * FROM "+AirDeskContract.UserEntry.TABLE_NAME;
        Cursor cuid = db.rawQuery(SqlUid, null);
        cuid.moveToFirst();
        while(!cuid.isAfterLast()){
            String cusername=cuid.getString(cuid.getColumnIndexOrThrow(AirDeskContract.UserEntry.COLUMN_USER_EMAIL));
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
        String cuname, cpass, cnickname;
        while(!c.isAfterLast()){
            cuid = c.getInt(c.getColumnIndexOrThrow(AirDeskContract.UserEntry.COLUMN_USER_ID));
            if(cuid == uid) {
                cuname = c.getString(c.getColumnIndexOrThrow(AirDeskContract.UserEntry.COLUMN_USER_EMAIL));
                cpass = c.getString(c.getColumnIndexOrThrow(AirDeskContract.UserEntry.COLUMN_USER_PASSWORD));
                cnickname = c.getString(c.getColumnIndexOrThrow(AirDeskContract.UserEntry.COLUMN_USER_NICKNAME));
                c.close();
                return new User(cuid,cuname,cpass, cnickname);

            }
            c.moveToNext();
        }
        c.close();
        throw new UserDoesNotExistException(uid);

    }

    public int getWorkspaceId(String wsname,int ownerID) {

        UserWorkspaces wlist= this.getUserWorkSpaces(ownerID);
        for(Workspace w:wlist.getWorkspaces()){
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
            int wsquota, wspublic, wsreadPerm, wswritePerm, wscreatePerm, wsdeletePerm;
            boolean isPublic, readPerm, writePerm, createPerm, deletePerm;
            while (!c.isAfterLast()) {
                if (wid == c.getInt(c.getColumnIndexOrThrow(AirDeskContract.WorkspaceEntry.COLUMN_WORKSPACE_ID))) {
                    wsname = c.getString(c.getColumnIndexOrThrow(AirDeskContract.WorkspaceEntry.COLUMN_WORKSPACE_NAME));
                    wsquota = c.getInt(c.getColumnIndexOrThrow(AirDeskContract.WorkspaceEntry.COLUMN_WORKSPACE_QUOTA));
                    wspublic = c.getInt(c.getColumnIndexOrThrow(AirDeskContract.WorkspaceEntry.COLUMN_WORKSPACE_PUBLIC));
                    wstags = c.getString(c.getColumnIndexOrThrow(AirDeskContract.WorkspaceEntry.COLUMN_WORKSPACE_TAGS));
                    wsreadPerm = c.getInt(c.getColumnIndexOrThrow(AirDeskContract.WorkspaceEntry.COLUMN_WORKSPACE_READ_PERM));
                    wswritePerm = c.getInt(c.getColumnIndexOrThrow(AirDeskContract.WorkspaceEntry.COLUMN_WORKSPACE_WRITE_PERM));
                    wscreatePerm = c.getInt(c.getColumnIndexOrThrow(AirDeskContract.WorkspaceEntry.COLUMN_WORKSPACE_CREATE_PERM));
                    wsdeletePerm = c.getInt(c.getColumnIndexOrThrow(AirDeskContract.WorkspaceEntry.COLUMN_WORKSPACE_DELETE_PERM));
                    wsowner = getWorkspaceOwner(wid);
                    c.close();
                    if(wspublic==0)
                        isPublic = false;
                    else isPublic = true;
                    if(wsreadPerm==0)
                        readPerm = false;
                    else readPerm = true;
                    if(wswritePerm==0)
                        writePerm = false;
                    else writePerm = true;
                    if(wscreatePerm==0)
                        createPerm = false;
                    else createPerm = true;
                    if(wsdeletePerm==0)
                        deletePerm = false;
                    else deletePerm = true;
                    return new Workspace(wid, wsname, wsquota, isPublic, wsowner, wstags, readPerm, writePerm, createPerm, deletePerm);
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
            String cuname=c.getString(c.getColumnIndexOrThrow(AirDeskContract.UserEntry.COLUMN_USER_EMAIL));
            if(cuname.equals(username)){
                int cuid=c.getInt(c.getColumnIndexOrThrow(AirDeskContract.UserEntry.COLUMN_USER_ID));
                String cpass=c.getString(c.getColumnIndexOrThrow(AirDeskContract.UserEntry.COLUMN_USER_PASSWORD));
                String cnickname=c.getString(c.getColumnIndexOrThrow(AirDeskContract.UserEntry.COLUMN_USER_NICKNAME));
                c.close();
                return new User(cuid,cuname,cpass, cnickname);
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
            String dbUserName = c.getString(c.getColumnIndexOrThrow(AirDeskContract.UserEntry.COLUMN_USER_EMAIL));

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
            String dbUserName = c.getString(c.getColumnIndexOrThrow(AirDeskContract.UserEntry.COLUMN_USER_EMAIL));
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

        UserWorkspaces wid=this.getUserWorkSpaces(uid);


            for(Workspace w: wid.getWorkspaces()){
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
        WorkspaceFiles filelist;
        try {
            filelist = getWorkspaceFiles(wid);
        } catch(FileDoesNotExistException f){
            throw f;
        } catch(WorkspaceDoesNotExistException w){
            throw w;
        }
        for (File f : filelist.getFiles()) {
            if (f.getFiletitle().equals(title)) {
                return true;
            }
        }
        return false;
    }

    public boolean fileExists(int fid,int wid) {
        WorkspaceFiles filelist;
        try {
            filelist = getWorkspaceFiles(wid);
        } catch(FileDoesNotExistException f){
            throw f;
        } catch(WorkspaceDoesNotExistException w){
            throw w;
        }
        for (File f : filelist.getFiles()) {
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

    public void addUser(String useremail, String password, String nickname) {

        if(userExists(useremail)){
            throw new UserAlreadyExistsException(useremail);
        }

        SQLiteDatabase db = this.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(AirDeskContract.UserEntry.COLUMN_USER_ID, generator());
        values.put(AirDeskContract.UserEntry.COLUMN_USER_EMAIL, useremail);
        values.put(AirDeskContract.UserEntry.COLUMN_USER_PASSWORD, password);
        values.put(AirDeskContract.UserEntry.COLUMN_USER_NICKNAME, nickname);

        // Insert the new row, returning the primary key value of the new row
        long newRowId;
        newRowId = db.insert(
                AirDeskContract.UserEntry.TABLE_NAME,
                null,
                values);

    }

    public void addWorkspace(String username,String wname, boolean pub,int quota, String tags, boolean readPerm, boolean writePerm, boolean createPerm, boolean deletePerm){
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
                values.put(AirDeskContract.WorkspaceEntry.COLUMN_WORKSPACE_READ_PERM, readPerm);
                values.put(AirDeskContract.WorkspaceEntry.COLUMN_WORKSPACE_WRITE_PERM, writePerm);
                values.put(AirDeskContract.WorkspaceEntry.COLUMN_WORKSPACE_CREATE_PERM, createPerm);
                values.put(AirDeskContract.WorkspaceEntry.COLUMN_WORKSPACE_DELETE_PERM, deletePerm);

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

    public void addSubscriberToWorkspace(int wid, int uid, boolean readPerm, boolean writePerm, boolean createPerm, boolean deletePerm) {
        User user;
        try{
            user = getUser(uid);
        } catch(UserDoesNotExistException u) {throw u;}
        WorkspaceSubscriptions userList;
        try{
            userList = getWorkspaceSubscribers(wid);
            for(User u: userList.getSubscriptions()) {
                if(u.getUseremail().equals(user.getUseremail()))
                    throw new UserAlreadyExistsException(user.getUseremail());
            }
        } catch(UserDoesNotExistException u) {throw u;}

        SQLiteDatabase db = this.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(AirDeskContract.WorkspaceHasSubscriptionsEntry.COLUMN_WHS_ID, generator());
        values.put(AirDeskContract.WorkspaceHasSubscriptionsEntry.COLUMN_WHS_WSID, wid);
        values.put(AirDeskContract.WorkspaceHasSubscriptionsEntry.COLUMN_WHS_UID, uid);
        values.put(AirDeskContract.WorkspaceHasSubscriptionsEntry.COLUMN_WHS_READ_PERM, readPerm);
        values.put(AirDeskContract.WorkspaceHasSubscriptionsEntry.COLUMN_WHS_WRITE_PERM, writePerm);
        values.put(AirDeskContract.WorkspaceHasSubscriptionsEntry.COLUMN_WHS_CREATE_PERM, createPerm);
        values.put(AirDeskContract.WorkspaceHasSubscriptionsEntry.COLUMN_WHS_DELETE_PERM, deletePerm);

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
        if(workspaceExists(workspace.getWsname(), workspace.getWsowner().getUseremail()) && !(workspace.getWsname().equals(tworkspace.getWsname())))
            throw new WorkspaceAlreadyExistsException(workspace.getWsname());

        SQLiteDatabase db = this.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(AirDeskContract.WorkspaceEntry.COLUMN_WORKSPACE_NAME, workspace.getWsname());
        values.put(AirDeskContract.WorkspaceEntry.COLUMN_WORKSPACE_PUBLIC, workspace.isWspublic());
        values.put(AirDeskContract.WorkspaceEntry.COLUMN_WORKSPACE_QUOTA, workspace.getWsquota());
        values.put(AirDeskContract.WorkspaceEntry.COLUMN_WORKSPACE_TAGS, workspace.getWstags().toString());

        values.put(AirDeskContract.WorkspaceEntry.COLUMN_WORKSPACE_READ_PERM, workspace.isReadPermission());
        values.put(AirDeskContract.WorkspaceEntry.COLUMN_WORKSPACE_WRITE_PERM, workspace.isWritePermission());
        values.put(AirDeskContract.WorkspaceEntry.COLUMN_WORKSPACE_CREATE_PERM, workspace.isCreatePermission());
        values.put(AirDeskContract.WorkspaceEntry.COLUMN_WORKSPACE_DELETE_PERM, workspace.isDeletePermission());
        //String sql = "UPDATE "+ AirDeskContract"COMPANY SET ADDRESS = 'Texas' WHERE ID = 6;"
        //db.rawQuery(sql, null);
        db.update(AirDeskContract.WorkspaceEntry.TABLE_NAME, values, AirDeskContract.WorkspaceEntry.COLUMN_WORKSPACE_ID + " =" + workspace.getWsid(), null);

    }

    public void changeFileData(File file) {
        int fid = file.getFileid();
        File f;
        try {
            f = getFile(fid);
        } catch (AirDeskException e) {throw e;}
        if(fileExists(file.getFileid(), file.getFileworkspace().getWsid()) && !(file.getFiletitle().equals(f.getFiletitle())))
            throw new FileAlreadyExistsException(file.getFiletitle());

        SQLiteDatabase db = this.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(AirDeskContract.FileEntry.COLUMN_FILE_TITLE, file.getFiletitle());
        values.put(AirDeskContract.FileEntry.COLUMN_FILE_CONTENT, file.getFilecontent());

        db.update(AirDeskContract.FileEntry.TABLE_NAME, values, AirDeskContract.FileEntry.COLUMN_FILE_ID + " =" + file.getFileid(), null);
    }

    public void changeSubscriptionData(Subscription subscription) {
        int wid = subscription.getWorkspace().getWsid();
        int uid = subscription.getUser().getUserid();

        Subscription s;
        try {
            s = getSubscription(wid, uid);
        } catch (AirDeskException e) {throw e;}

        SQLiteDatabase db = this.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(AirDeskContract.WorkspaceHasSubscriptionsEntry.COLUMN_WHS_READ_PERM, subscription.isReadPermission());
        values.put(AirDeskContract.WorkspaceHasSubscriptionsEntry.COLUMN_WHS_WRITE_PERM, subscription.isWritePermission());
        values.put(AirDeskContract.WorkspaceHasSubscriptionsEntry.COLUMN_WHS_CREATE_PERM, subscription.isCreatePermission());
        values.put(AirDeskContract.WorkspaceHasSubscriptionsEntry.COLUMN_WHS_DELETE_PERM, subscription.isDeletePermission());

        db.update(AirDeskContract.WorkspaceHasSubscriptionsEntry.TABLE_NAME, values, AirDeskContract.WorkspaceHasSubscriptionsEntry.COLUMN_WHS_WSID + " =" + subscription.getWorkspace().getWsid()
                + " AND " + AirDeskContract.WorkspaceHasSubscriptionsEntry.COLUMN_WHS_UID + " =" + subscription.getUser().getUserid(), null);
    }

    public boolean isSubscribed(int uid,int wid){
        WorkspaceSubscriptions ws;
        try {
            ws = getWorkspaceSubscribers(wid);
        } catch (AirDeskException u){throw u;}

        ArrayList<User> list = ws.getSubscriptions();
        for(User u:list){
            if(u.getUserid()==uid){
                return true;
            }
        }
        return false;

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
        db.beginTransaction();
        boolean d1 = false;
        boolean d2 = false;
        try {
            String wcFE = AirDeskContract.FileEntry.COLUMN_FILE_ID + " = " + fid;
        String wcFEn = AirDeskContract.WorkspaceHasFileEntry.COLUMN_WHF_FID + " = " + fid;

            d1=db.delete(AirDeskContract.FileEntry.TABLE_NAME,wcFE,null)>0;
            d2=db.delete(AirDeskContract.WorkspaceHasFileEntry.TABLE_NAME, wcFEn, null)>0;
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }

        return d1&&d2;
    }

    public boolean removeWorkspace(int wid){
        if(!workspaceExists(wid)){
            throw new WorkspaceDoesNotExistException(wid);
        }

        SQLiteDatabase db= this.getWritableDatabase();
        db.beginTransaction();
        boolean d1 = false;
        boolean d2 = false;
        boolean d3 = false;
        boolean d4 = false;
        try{
            String wcWE=AirDeskContract.WorkspaceEntry.COLUMN_WORKSPACE_ID+" = "+ wid;
            String wcUHWE=AirDeskContract.UserHasWorkspaceEntry.COLUMN_UHW_WSID+" = "+ wid;
            String wcWHSE=AirDeskContract.WorkspaceHasSubscriptionsEntry.COLUMN_WHS_WSID+" = "+ wid;
            String wcWHFE=AirDeskContract.WorkspaceHasFileEntry.COLUMN_WHF_WSID+" = "+ wid;
           d1= db.delete(AirDeskContract.WorkspaceEntry.TABLE_NAME,wcWE,null)>0;
            d2= db.delete(AirDeskContract.UserHasWorkspaceEntry.TABLE_NAME,wcUHWE,null)>0;
            d3= db.delete(AirDeskContract.WorkspaceHasSubscriptionsEntry.TABLE_NAME,wcWHSE,null)>0;
            d4= db.delete(AirDeskContract.WorkspaceHasFileEntry.TABLE_NAME,wcWHFE,null)>0;
            db.setTransactionSuccessful();
        }
        finally {
            db.endTransaction();
        }
        return d1&&d2;
    }

    public int generator(){
        Random r=new Random();

        return r.nextInt();
    }
}