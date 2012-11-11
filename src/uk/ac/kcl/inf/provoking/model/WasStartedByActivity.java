package uk.ac.kcl.inf.provoking.model;

import uk.ac.kcl.inf.provoking.model.util.AttributeHolder;

public class WasStartedByActivity extends AttributeHolder implements Description {
    public final Activity _started;
    public final Activity _starter;
    
    public WasStartedByActivity (Activity started, Activity starter) {
        _started = started;
        _starter = starter;
    }

}
