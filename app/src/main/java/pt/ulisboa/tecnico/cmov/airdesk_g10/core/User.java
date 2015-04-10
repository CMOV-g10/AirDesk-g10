package pt.ulisboa.tecnico.cmov.airdesk_g10.core;

/**
 * Created by luis on 4/8/15.
 */
public class User {

    //Always filled parameteres
    private int userid;
    private String useremail;
    private String userpassword;
    private String usernickname;

    public User(int userid, String useremail, String userpassword, String usernickname) {
        this.userid = userid;
        this.useremail = useremail;
        this.userpassword = userpassword;
        this.usernickname = usernickname;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getUseremail() {
        return useremail;
    }

    public void setUseremail(String useremail) {
        this.useremail = useremail;
    }

    public String getUserpassword() {
        return userpassword;
    }

    public void setUserpassword(String userpassword) {
        this.userpassword = userpassword;
    }

}
