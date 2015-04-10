package pt.ulisboa.tecnico.cmov.airdesk_g10.core;

import java.util.ArrayList;

/**
 * Created by luis on 4/10/15.
 */
public class UserSubscriptions {

    private User user;
    private ArrayList<Subscription> subscriptions;

    public UserSubscriptions(User user, ArrayList<Subscription> subscriptions) {
        this.user = user;
        this.subscriptions = subscriptions;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ArrayList<Subscription> getSubscriptions() {
        return subscriptions;
    }

    public void setSubscriptions(ArrayList<Subscription> subscriptions) {
        this.subscriptions = subscriptions;
    }
}
