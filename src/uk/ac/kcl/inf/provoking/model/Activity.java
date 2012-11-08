package uk.ac.kcl.inf.provoking.model;

import java.util.Date;

public class Activity extends AttributeHolder implements Description {
    private Date _startedAt;
    private Date _endedAt;
    
    public Activity () {
    }
    
    public Activity (Object identifier) {
        super (identifier);
    }

    public Activity (Date startedAt, Date endedAt) {
        setStartedAt (startedAt);
        setEndedAt (endedAt);
    }

    public Activity (Object identifier, Date startedAt, Date endedAt) {
        super (identifier);
        setStartedAt (startedAt);
        setEndedAt (endedAt);
    }

    public Date getStartedAt () {
        return _startedAt;
    }

    public void setStartedAt (Date startedAt) {
        if (startedAt != null)
            _startedAt = startedAt;
    }

    public Date getEndedAt () {
        return _endedAt;
    }

    public void setEndedAt (Date endedAt) {
        if (endedAt != null)
            _endedAt = endedAt;
    }
}
