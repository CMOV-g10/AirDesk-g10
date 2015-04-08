package pt.ulisboa.tecnico.cmov.airdesk_g10.exceptions;

/**
 * Created by luis on 4/8/15.
 */
public class FileDoesNotExistException extends AirDeskException {

    private String filename;

    public FileDoesNotExistException() {
    }

    public FileDoesNotExistException(String filename) {
        this.filename = filename;
    }

    @Override
    public String getMessage() {
        return "File: " + this.filename + " does not exist.";
    }
}
