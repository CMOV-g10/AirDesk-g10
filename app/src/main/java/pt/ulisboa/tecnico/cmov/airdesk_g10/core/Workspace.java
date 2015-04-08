package pt.ulisboa.tecnico.cmov.airdesk_g10.core;

import java.util.ArrayList;

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

    //Parameters that can be null
    private ArrayList<String> wstags;
    private ArrayList<User> wssubscription;
    private ArrayList<File> wsfiles;

    public Workspace(int wsid, String wsname, int wsquota, boolean wspublic, User wsowner) {
        this.wsid = wsid;
        this.wsname = wsname;
        this.wsquota = wsquota;
        this.wspublic = wspublic;
        this.wsowner = wsowner;
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

    public ArrayList<String> getWstags() {
        return wstags;
    }

    public ArrayList<User> getWssubscription() {
        return wssubscription;
    }

    public User getWsowner() {
        return wsowner;
    }

    public ArrayList<File> getWsfiles() {
        return wsfiles;
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

    public void setWssubscription(ArrayList<User> wssubscription) {
        this.wssubscription = wssubscription;
    }

    public void setWsowner(User wsowner) {
        this.wsowner = wsowner;
    }

    public void setWsfiles(ArrayList<File> wsfiles) {
        this.wsfiles = wsfiles;
    }



}
