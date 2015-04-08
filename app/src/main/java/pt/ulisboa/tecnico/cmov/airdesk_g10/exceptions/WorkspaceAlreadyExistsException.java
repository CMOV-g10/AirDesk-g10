package pt.ulisboa.tecnico.cmov.airdesk_g10.exceptions;

/**
 * Created by Dolan on 08-04-2015.
 */
public class WorkspaceAlreadyExistsException extends AirDeskException{

    private String wsname;
    private int wsid;
    public WorkspaceAlreadyExistsException(){}
    public WorkspaceAlreadyExistsException(String wname){
        this.wsname= wname;
    }
    public WorkspaceAlreadyExistsException(int wsid){this.wsid = wsid;}
    @Override
    public String getMessage(){
        return "Workspace: "+this.wsname+" already exists.";
    }
    public String getWidMessage(){
        return "Workspace id: "+this.wsid+" already exists.";
    }

}
