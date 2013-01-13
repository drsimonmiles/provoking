package uk.ac.kcl.inf.provoking.model;

import java.util.Date;
import uk.ac.kcl.inf.provoking.model.util.AttributeHolder;

public abstract class InstantaneousEvent extends AttributeHolder implements Description {
    private Date _time;
    private Location _location;
    private Role _role;
    
    protected InstantaneousEvent () {
        _time = null;
        _location = null;
        _role = null;
    }

    protected InstantaneousEvent (Object identifier) {
        super (identifier);
        _time = null;
        _location = null;
        _role = null;
    }

    public Date getTime () {
        return _time;
    }

    public void setTime (Date time) {
        _time = time;
    }

    public Location getLocation () {
        return _location;
    }
    
    public void setLocation (Location location) {
        _location = location;
    }

    public Role getRole () {
        return _role;
    }
    
    public void setRole (Role role) {
        _role = role;
    }
}

