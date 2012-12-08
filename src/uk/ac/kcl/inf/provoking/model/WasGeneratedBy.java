package uk.ac.kcl.inf.provoking.model;

import java.net.URI;
import java.util.Date;
import uk.ac.kcl.inf.provoking.model.util.TimestampedEdge;

public class WasGeneratedBy extends TimestampedEdge {
    private Activity _generater;
    private Entity _generated;
    
    public WasGeneratedBy (Entity generated, Activity generater) {
        _generater = generater;
        _generated = generated;
    }

    public WasGeneratedBy (Entity generated, Activity generater, Date time) {
        super (time);
        _generater = generater;
        _generated = generated;
    }

    public WasGeneratedBy (URI identifier, Entity generated, Activity generater) {
        super (identifier);
        _generater = generater;
        _generated = generated;
    }

    public WasGeneratedBy (URI identifier, Entity generated, Activity generater, Date time) {
        super (identifier, time);
        _generater = generater;
        _generated = generated;
    }
    
    public Activity getGenerater () {
        return _generater;
    }
    
    public void setGenerater (Activity generater) {
        _generater = generater;
    }
    
    public Entity getGenerated () {
        return _generated;
    }
    
    public void setGenerated (Entity generated) {
        _generated = generated;
    }
}
