package uk.ac.kcl.inf.provoking.model;

public class WasDerivedFrom implements Description {
    public final Entity _derived;
    public final Entity _derivedFrom;
    
    public WasDerivedFrom (Entity derived, Entity derivedFrom) {
        _derived = derived;
        _derivedFrom = derivedFrom;
    }
}
