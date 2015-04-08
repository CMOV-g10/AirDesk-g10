package pt.ulisboa.tecnico.cmov.airdesk_g10.core;

import java.util.ArrayList;

/**
 * Created by luis on 4/8/15.
 */
public class File {

    //Always filled parameteres
    private int fileid;
    private String filetitle;
    private String filecontent;
    private Workspace fileworkspace;

    public File(int fileid, String filetitle, String filecontent, Workspace fileworkspace) {
        this.fileid = fileid;
        this.filetitle = filetitle;
        this.filecontent = filecontent;
        this.fileworkspace = fileworkspace;
    }

    public int getFileid() {
        return fileid;
    }

    public void setFileid(int fileid) {
        this.fileid = fileid;
    }

    public String getFiletitle() {
        return filetitle;
    }

    public void setFiletitle(String filetitle) {
        this.filetitle = filetitle;
    }

    public String getFilecontent() {
        return filecontent;
    }

    public void setFilecontent(String filecontent) {
        this.filecontent = filecontent;
    }

    public Workspace getFileworkspace() {
        return fileworkspace;
    }

    public void setFileworkspace(Workspace fileworkspace) {
        this.fileworkspace = fileworkspace;
    }
}
