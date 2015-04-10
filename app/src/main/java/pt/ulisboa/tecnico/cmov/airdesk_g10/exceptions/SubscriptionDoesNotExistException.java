package pt.ulisboa.tecnico.cmov.airdesk_g10.exceptions;

/**
 * Created by luis on 4/10/15.
 */
public class SubscriptionDoesNotExistException extends AirDeskException {

    private String user;
    private String workspace;
    private int uid;
    private int wsid;
    public SubscriptionDoesNotExistException() {
    }

    public SubscriptionDoesNotExistException(String user, String workspace) {
        this.workspace = workspace;
        this.user= user;
    }
    public SubscriptionDoesNotExistException(int uid, int wsid){this.uid = uid;
    this.wsid = wsid;}

    @Override
    public String getMessage() {
        return "Subscription: " + this.user + " to "+ this.workspace +" does not exist.";
    }
    public String getFidMessage(){
        return "Subscription id: "+this.uid+ " to " + this.wsid +" does not exist.";
    }
}
