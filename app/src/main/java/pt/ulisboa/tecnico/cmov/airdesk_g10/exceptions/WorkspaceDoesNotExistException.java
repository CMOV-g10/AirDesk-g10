package pt.ulisboa.tecnico.cmov.airdesk_g10.exceptions;

/**
 * Created by luis on 4/8/15.
 */
public class WorkspaceDoesNotExistException extends AirDeskException{

    private String wsname;
    public WorkspaceDoesNotExistException(){}
    public WorkspaceDoesNotExistException(String wname){
        this.wsname= wname;
    }
    @Override
    public String getMessage(){
        return "Workspace: "+this.wsname+" does not exist.";
    }

}
