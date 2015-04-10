package pt.ulisboa.tecnico.cmov.airdesk_g10.exceptions;

/**
 * Created by Dolan on 10-04-2015.
 */
public class NoWorkspaceWithTagException extends AirDeskException {

    private String tag;
    private int fid;
    public NoWorkspaceWithTagException() {
    }

    public NoWorkspaceWithTagException(String tag) {
        this.tag = tag;
    }

    @Override
    public String getMessage() {
        return "Workspace with: " + this.tag + " does not exist.";
    }

}