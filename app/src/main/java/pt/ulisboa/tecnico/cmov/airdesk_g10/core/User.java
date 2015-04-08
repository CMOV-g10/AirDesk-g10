package pt.ulisboa.tecnico.cmov.airdesk_g10.core;

import java.util.ArrayList;

/**
 * Created by luis on 4/8/15.
 */
public class User {

    //Always filled parameteres
    private int userid;
    private String username;
    private String userpassword;

    //Parameters that can be nul
    private ArrayList<Workspace> userworkspaces;
    private ArrayList<Workspace> usersubscriptions;

    public User(int userid, String username, String userpassword) {
        this.userid = userid;
        this.username = username;
        this.userpassword = userpassword;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserpassword() {
        return userpassword;
    }

    public void setUserpassword(String userpassword) {
        this.userpassword = userpassword;
    }

    public ArrayList<Workspace> getUserworkspaces() {
        return userworkspaces;
    }

    public void setUserworkspaces(ArrayList<Workspace> userworkspaces) {
        this.userworkspaces = userworkspaces;
    }

    public ArrayList<Workspace> getUsersubscriptions() {
        return usersubscriptions;
    }

    public void setUsersubscriptions(ArrayList<Workspace> usersubscriptions) {
        this.usersubscriptions = usersubscriptions;
    }
}
