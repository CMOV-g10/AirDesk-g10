package pt.ulisboa.tecnico.cmov.airdesk_g10.core;

import java.util.ArrayList;
import java.util.Arrays;

import pt.ulisboa.tecnico.cmov.airdesk_g10.db.AirDeskDbHelper;

/**
 * Created by luis on 4/8/15.
 */
public class Workspace {

    //Always filled parameteres
    private int wsid;
    private String wsname;
    private int wsquota;
    private boolean wspublic;
    private User wsowner;

    private ArrayList<String> wstags;

    private boolean readPermission;
    private boolean writePermission;
    private boolean createPermission;
    private boolean deletePermission;


    public Workspace(int wsid, String wsname, int wsquota, boolean wspublic, User wsowner, ArrayList<String> wstags, boolean readPermission, boolean writePermission, boolean createPermission, boolean deletePermission) {
        this.wsid = wsid;
        this.wsname = wsname;
        this.wsquota = wsquota;
        this.wspublic = wspublic;
        this.wsowner = wsowner;
        this.wstags = wstags;
        this.readPermission = readPermission;
        this.writePermission = writePermission;
        this.createPermission = createPermission;
        this.deletePermission = deletePermission;
    }

    public Workspace(int wsid, String wsname, int wsquota, boolean wspublic, User wsowner, String wstags, boolean readPermission, boolean writePermission, boolean createPermission, boolean deletePermission) {
        String delims = ",";
        String[] tokens = wstags.split(delims);
        this.wstags = new ArrayList<String>(Arrays.asList(tokens));
        this.wsid = wsid;
        this.wsname = wsname;
        this.wsquota = wsquota;
        this.wspublic = wspublic;
        this.wsowner = wsowner;
        this.readPermission = readPermission;
        this.writePermission = writePermission;
        this.createPermission = createPermission;
        this.deletePermission = deletePermission;
    }

    public int getWsid() {
        return wsid;
    }

    public String getWsname() {
        return wsname;
    }

    public int getWsquota() {
        return wsquota;
    }

    public boolean isWspublic() {
        return wspublic;
    }

    public String getWstagsInString() {
        String result = "";
        for(String s : wstags){
            result = result + s + ",";
        }

        if(!result.equals(""))
            return result.substring(0,result.length()-1);
        else
            return result;
    }

    public ArrayList<String> getWstags() {
        return wstags;
    }

    public User getWsowner() {
        return wsowner;
    }

    public void setWsid(int wsid) {
        this.wsid = wsid;
    }

    public void setWsname(String wsname) {
        this.wsname = wsname;
    }

    public void setWsquota(int wsquota) {
        this.wsquota = wsquota;
    }

    public void setWspublic(boolean wspublic) {
        this.wspublic = wspublic;
    }

    public void setWstags(ArrayList<String> wstags) {
        this.wstags = wstags;
    }

    public void setWstags(String wstags) {
        String delims = ",";
        String[] tokens = wstags.split(delims);
        this.wstags = new ArrayList<String>(Arrays.asList(tokens));
    }

    public void setWsowner(User wsowner) {
        this.wsowner = wsowner;
    }

    public boolean isReadPermission() {
        return readPermission;
    }

    public void setReadPermission(boolean readPermission) {
        this.readPermission = readPermission;
    }

    public boolean isWritePermission() {
        return writePermission;
    }

    public void setWritePermission(boolean writePermission) {
        this.writePermission = writePermission;
    }

    public boolean isCreatePermission() {
        return createPermission;
    }

    public void setCreatePermission(boolean createPermission) {
        this.createPermission = createPermission;
    }

    public boolean isDeletePermission() {
        return deletePermission;
    }

    public void setDeletePermission(boolean deletePermission) {
        this.deletePermission = deletePermission;
    }


}
