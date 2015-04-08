package pt.ulisboa.tecnico.cmov.airdesk_g10.exceptions;

/**
 * Created by luis on 4/8/15.
 */
public class FileDoesNotExistException extends AirDeskException {

    private String filename;
    private int fid;
    public FileDoesNotExistException() {
    }

    public FileDoesNotExistException(String filename) {
        this.filename = filename;
    }
    public FileDoesNotExistException(int fid){this.fid = fid;}

    @Override
    public String getMessage() {
        return "File: " + this.filename + " does not exist.";
    }
    public String getFidMessage(){
        return "File id: "+this.fid+" does not exist.";
    }
}
