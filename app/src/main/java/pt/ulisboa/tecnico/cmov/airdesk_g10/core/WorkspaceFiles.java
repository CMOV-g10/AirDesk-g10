package pt.ulisboa.tecnico.cmov.airdesk_g10.core;

import java.util.ArrayList;

/**
 * Created by luis on 4/10/15.
 */
public class WorkspaceFiles {

    private Workspace workspace;
    private ArrayList<File> files;

    public WorkspaceFiles(Workspace workspace, ArrayList<File> files) {
        this.workspace = workspace;
        this.files = files;
    }

    public Workspace getWorkspace() {
        return workspace;
    }

    public void setWorkspace(Workspace workspace) {
        this.workspace = workspace;
    }

    public ArrayList<File> getFiles() {
        return files;
    }

    public void setFiles(ArrayList<File> files) {
        this.files = files;
    }
}
