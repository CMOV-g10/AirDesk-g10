package pt.ulisboa.tecnico.cmov.airdesk_g10.core;

import java.util.ArrayList;

/**
 * Created by luis on 4/10/15.
 */
public class WorkspaceSubscriptions {

    private Workspace workspace;
    private ArrayList<User> subscriptions;

    public WorkspaceSubscriptions(Workspace workspace, ArrayList<User> subscriptions) {
        this.workspace = workspace;
        this.subscriptions = subscriptions;
    }

    public Workspace getWorkspace() {
        return workspace;
    }

    public void setWorkspace(Workspace workspace) {
        this.workspace = workspace;
    }

    public ArrayList<User> getSubscriptions() {
        return subscriptions;
    }

    public void setSubscriptions(ArrayList<User> subscriptions) {
        this.subscriptions = subscriptions;
    }
}
