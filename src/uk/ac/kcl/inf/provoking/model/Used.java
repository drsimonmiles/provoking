package uk.ac.kcl.inf.provoking.model;

import java.util.Date;

public class Used extends TimestampedEdge {
    private Activity _user;
    private Entity _used;
    
    public Used (Activity user, Entity used) {
        _user = user;
        _used = used;
    }

    public Used (Activity user, Entity used, Date time) {
        super (time);
        _user = user;
        _used = used;
    }

    public Activity getUser () {
        return _user;
    }

    public void setUser (Activity user) {
        _user = user;
    }

    public Entity getUsed () {
        return _used;
    }

    public void setUsed (Entity used) {
        _used = used;
    }
}
