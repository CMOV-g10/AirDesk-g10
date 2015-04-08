package pt.ulisboa.tecnico.cmov.airdesk_g10.exceptions;

/**
 * Created by Dolan on 08-04-2015.
 */
public class WorkSpaceAlreadyExistsException extends AirDeskException{

    private String wsname;
    public WorkSpaceAlreadyExistsException(){}
    public WorkSpaceAlreadyExistsException(String wname){
        this.wsname= wname;
    }
    @Override
    public String getMessage(){
        return "Workspace:"+this.wsname+"does not exist";
    }

}
