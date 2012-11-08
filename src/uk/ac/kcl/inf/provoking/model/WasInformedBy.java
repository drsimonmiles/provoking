package uk.ac.kcl.inf.provoking.model;

public class WasInformedBy extends AttributeHolder implements Description {
    public final Activity _informed;
    public final Activity _informer;
    
    public WasInformedBy (Activity informed, Activity informer) {
        _informed = informed;
        _informer = informer;
    }

}
