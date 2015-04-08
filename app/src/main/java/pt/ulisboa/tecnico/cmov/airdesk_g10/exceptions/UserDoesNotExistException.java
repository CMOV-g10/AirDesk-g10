package pt.ulisboa.tecnico.cmov.airdesk_g10.exceptions;

/**
 * Created by Dolan on 08-04-2015.
 */
public class UserDoesNotExistException extends AirDeskException{

        private String username;
        public UserDoesNotExistException(){}
        public UserDoesNotExistException(String username){
            this.username= username;
        }
        @Override
        public String getMessage(){
            return "Username: "+this.username+" does not exist.";
        }

}
