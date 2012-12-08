package uk.ac.kcl.inf.provoking.model;

import uk.ac.kcl.inf.provoking.model.util.AttributeHolder;
import uk.ac.kcl.inf.provoking.model.util.TimestampedEdge;

public class WasStartedBy extends TimestampedEdge implements Description {
    private Activity _started;
    private Activity _starter;
    private Entity _trigger;
    
    public WasStartedBy (Activity started, Entity trigger, Activity starter) {
        _started = started;
        _trigger = trigger;
        _starter = starter;
    }

    INCOMPLETE
}
