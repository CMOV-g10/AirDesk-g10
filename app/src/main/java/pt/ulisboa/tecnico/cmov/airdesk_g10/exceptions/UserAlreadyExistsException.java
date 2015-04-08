package pt.ulisboa.tecnico.cmov.airdesk_g10.exceptions;

/**
 * Created by Dolan on 08-04-2015.
 */
public class UserAlreadyExistsException extends AirDeskException{
    private String username;
    private int uid;
    public UserAlreadyExistsException(){}
    public UserAlreadyExistsException(String username){
        this.username= username;
    }
    public UserAlreadyExistsException(int uid) {this.uid = uid;}
    @Override
    public String getMessage(){
        return "Username: "+this.username+" already exists.";
    }
    public String getUidMessage(){
        return "User id: "+this.uid+" already exists.";
    }
}
