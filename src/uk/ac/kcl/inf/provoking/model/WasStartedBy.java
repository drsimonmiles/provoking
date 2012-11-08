package uk.ac.kcl.inf.provoking.model;

public class WasStartedBy extends AttributeHolder implements Description {
    public final Activity _started;
    public final Entity _starter;
    
    public WasStartedBy (Activity started, Entity starter) {
        _started = started;
        _starter = starter;
    }

}
