package uk.ac.kcl.inf.provoking.model;

import java.net.URI;
import java.util.Date;
import uk.ac.kcl.inf.provoking.model.util.AttributeHolder;

public class Activity extends AttributeHolder implements Description {
    private Date _startedAt;
    private Date _endedAt;
    
    public Activity () {
    }

    public Activity (Date startedAt, Date endedAt) {
        _startedAt = startedAt;
        _endedAt = endedAt;
    }
    
    public Activity (URI identifier, boolean isReference) {
        super (identifier, isReference);
    }

    public Activity (URI identifier, Date startedAt, Date endedAt) {
        super (identifier, false);
        _startedAt = startedAt;
        _endedAt = endedAt;
    }
    
    public Date getStartedAt () {
        return _startedAt;
    }

    public void setStartedAt (Date startedAt) {
        if (startedAt != null) {
            _startedAt = startedAt;
        }
    }

    public Date getEndedAt () {
        return _endedAt;
    }

    public void setEndedAt (Date endedAt) {
        if (endedAt != null) {
            _endedAt = endedAt;
        }
    }
}
