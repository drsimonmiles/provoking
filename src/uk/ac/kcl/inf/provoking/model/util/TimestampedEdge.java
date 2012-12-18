package uk.ac.kcl.inf.provoking.model.util;

import java.util.Date;
import uk.ac.kcl.inf.provoking.model.Description;

public abstract class TimestampedEdge extends AttributeHolder implements Description {
    private Date _time;
    
    protected TimestampedEdge () {
        _time = null;
    }

    protected TimestampedEdge (Object identifier) {
        super (identifier);
        _time = null;
    }

    public Date getTime () {
        return _time;
    }

    public void setTime (Date time) {
        _time = time;
    }
}

