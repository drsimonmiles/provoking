package uk.ac.kcl.inf.provoking.model;

import uk.ac.kcl.inf.provoking.model.util.AttributeHolder;

public class WasInformedBy extends AttributeHolder implements Description {
    private Activity _informed;
    private Activity _informer;
    
    public WasInformedBy (Activity informed, Activity informer) {
        _informed = informed;
        _informer = informer;
    }

    public WasInformedBy (Object identifier, Activity informed, Activity informer) {
        super (identifier);
        _informed = informed;
        _informer = informer;
    }

    public Activity getInformed () {
        return _informed;
    }

    public void setInformed (Activity informed) {
        _informed = informed;
    }

    public Activity getInformer () {
        return _informer;
    }

    public void setInformer (Activity informer) {
        _informer = informer;
    }

    public static WasInformedBy reference (Object identifier) {
        WasInformedBy reference = new WasInformedBy (identifier, null, null);
        reference.setIsReference (true);
        return reference;
    }
}
