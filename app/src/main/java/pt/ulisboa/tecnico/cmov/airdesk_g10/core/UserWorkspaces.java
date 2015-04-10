package pt.ulisboa.tecnico.cmov.airdesk_g10.core;

import java.util.ArrayList;

/**
 * Created by luis on 4/10/15.
 */
public class UserWorkspaces {

    private User user;
    private ArrayList<Workspace> workspaces;

    public UserWorkspaces(User user, ArrayList<Workspace> workspaces) {
        this.user = user;
        this.workspaces = workspaces;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ArrayList<Workspace> getWorkspaces() {
        return workspaces;
    }

    public void setWorkspaces(ArrayList<Workspace> workspaces) {
        this.workspaces = workspaces;
    }
}
