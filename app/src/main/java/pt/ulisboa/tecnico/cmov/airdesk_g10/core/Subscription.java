package pt.ulisboa.tecnico.cmov.airdesk_g10.core;

/**
 * Created by luis on 4/10/15.
 */
public class Subscription {

    private User user;
    private Workspace workspace;
    private boolean readPermission;
    private boolean writePermission;
    private boolean createPermission;
    private boolean deletePermission;

    public Subscription(User user, Workspace workspace, boolean readPermission, boolean writePermission, boolean createPermission, boolean deletePermission) {
        this.user = user;
        this.workspace = workspace;
        this.readPermission = readPermission;
        this.writePermission = writePermission;
        this.createPermission = createPermission;
        this.deletePermission = deletePermission;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Workspace getWorkspace() {
        return workspace;
    }

    public void setWorkspace(Workspace workspace) {
        this.workspace = workspace;
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
