package uk.ac.kcl.inf.provoking.model;

import uk.ac.kcl.inf.provoking.model.util.AttributeHolder;

public class WasEndedBy extends AttributeHolder implements Description {
    public final Activity _ended;
    public final Entity _ender;
    
    public WasEndedBy (Activity ended, Entity ender) {
        _ended = ended;
        _ender = ender;
    }

}
