package pt.ulisboa.tecnico.cmov.airdesk_g10.exceptions;

/**
 * Created by Dolan on 08-04-2015.
 */
public class UserDoesNotExistException extends AirDeskException{

        private int uid;
        private String username;
        public UserDoesNotExistException(){}
        public UserDoesNotExistException(String username){
            this.username= username;
        }
        public UserDoesNotExistException(int  uid){
            this.uid= uid;
        }

        @Override
        public String getMessage(){
            return "Username: "+this.username+" does not exist.";
        }

        public String getUidMessage(){
        return "Uid:"+this.uid+"does not exist";
    }

}
