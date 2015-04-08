package pt.ulisboa.tecnico.cmov.airdesk_g10.exceptions;

/**
 * Created by luis on 4/8/15.
 */
public class FileAlreadyExistsException  extends AirDeskException{
    private int fid;
    private String filename;
    public FileAlreadyExistsException(){}
    public FileAlreadyExistsException(String filename){
        this.filename= filename;
    }
    public FileAlreadyExistsException(int fid){this.fid = fid;}
    @Override
    public String getMessage(){
        return "File: "+this.filename+" already exists.";
    }
    public String getFidMessage(){
        return "File id: "+this.fid+" already exists.";
    }
}
