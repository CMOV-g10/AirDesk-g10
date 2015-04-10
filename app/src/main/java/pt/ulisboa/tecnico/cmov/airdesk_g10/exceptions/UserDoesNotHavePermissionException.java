package pt.ulisboa.tecnico.cmov.airdesk_g10.exceptions;

/**
 * Created by luis on 4/10/15.
 */
public class UserDoesNotHavePermissionException extends AirDeskException{

    private int uid;
    private String username;
    public UserDoesNotHavePermissionException(){}
    public UserDoesNotHavePermissionException(String username){
        this.username= username;
    }
    public UserDoesNotHavePermissionException(int  uid){
        this.uid= uid;
    }

    @Override
    public String getMessage(){
        return "Username: "+this.username+" does not have permission.";
    }

    public String getUidMessage(){
        return "User id: "+this.uid+" does not have permission.";
    }

}
