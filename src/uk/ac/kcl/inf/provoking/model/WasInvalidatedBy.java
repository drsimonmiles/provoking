package uk.ac.kcl.inf.provoking.model;

import java.util.Date;
import uk.ac.kcl.inf.provoking.model.util.TimestampedEdge;

public class WasInvalidatedBy extends TimestampedEdge {
    private Activity _invalidater;
    private Entity _invalidated;
    
    public WasInvalidatedBy (Entity invalidated, Activity invalidater) {
        _invalidater = invalidater;
        _invalidated = invalidated;
    }

    public WasInvalidatedBy (Entity invalidated) {
        this (invalidated, (Activity) null);
    }

    public WasInvalidatedBy (Entity invalidated, Activity invalidater, Date time) {
        super (time);
        _invalidater = invalidater;
        _invalidated = invalidated;
    }

    public WasInvalidatedBy (Entity invalidated, Date time) {
        this (invalidated, (Activity) null, time);
    }

    public WasInvalidatedBy (Object identifier, Entity invalidated, Activity invalidater) {
        super (identifier);
        _invalidater = invalidater;
        _invalidated = invalidated;
    }

    public WasInvalidatedBy (Object identifier, Entity invalidated) {
        this (identifier, invalidated, (Activity) null);
    }
    
    public WasInvalidatedBy (Object identifier, Entity invalidated, Activity invalidater, Date time) {
        super (identifier);
        _invalidater = invalidater;
        _invalidated = invalidated;
        setTime (time);
    }
    
    public WasInvalidatedBy (Object identifier, Entity invalidated, Date time) {
        this (identifier, invalidated, null, time);
    }

    public Activity getInvalidater () {
        return _invalidater;
    }
    
    public void setInvalidater (Activity invalidater) {
        _invalidater = invalidater;
    }
    
    public Entity getInvalidated () {
        return _invalidated;
    }
    
    public void setInvalidated (Entity invalidated) {
        _invalidated = invalidated;
    }
    
    public static WasInvalidatedBy reference (Object identifier) {
        WasInvalidatedBy reference = new WasInvalidatedBy (identifier, null, (Activity) null);
        reference.setIsReference (true);
        return reference;
    }
}
