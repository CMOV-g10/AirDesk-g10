package pt.ulisboa.tecnico.cmov.airdesk_g10;

import android.app.Application;
import android.content.Context;

import pt.ulisboa.tecnico.cmov.airdesk_g10.core.User;
import pt.ulisboa.tecnico.cmov.airdesk_g10.db.AirDeskDbHelper;

/**
 * Created by luis on 4/8/15.
 */
public class AirDesk extends Application {

    public final static int EDIT_SUBS_LIST = 10;

    private AirDeskDbHelper mDBHelper;
    private User loggedUser;

    public AirDesk(){
        mDBHelper = new AirDeskDbHelper(this);
     }

    public User getLoggedUser() {
        return loggedUser;
    }

    public void setLoggedUser(User loggedUser) {
        this.loggedUser = loggedUser;
    }

    public AirDeskDbHelper getmDBHelper() {
        return mDBHelper;
    }

    public void setmDBHelper(AirDeskDbHelper mDBHelper) {
        this.mDBHelper = mDBHelper;
    }

}
