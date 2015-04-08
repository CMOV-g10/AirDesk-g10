package pt.ulisboa.tecnico.cmov.airdesk_g10.exceptions;

/**
 * Created by luis on 4/8/15.
 */
public class FileAlreadyExistsException  extends AirDeskException{
    private String filename;
    public FileAlreadyExistsException(){}
    public FileAlreadyExistsException(String filename){
        this.filename= filename;
    }
    @Override
    public String getMessage(){
        return "File: "+this.filename+" already exists.";
    }
}
