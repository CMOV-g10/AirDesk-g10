package pt.ulisboa.tecnico.cmov.airdesk_g10.exceptions;

/**
 * Created by Dolan on 08-04-2015.
 */
public class AirDeskException extends RuntimeException{

    public AirDeskException(){}
    public AirDeskException(String msg){
        super(msg);
    }
}
