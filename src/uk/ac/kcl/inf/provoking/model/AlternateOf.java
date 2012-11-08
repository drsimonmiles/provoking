package uk.ac.kcl.inf.provoking.model;

public class AlternateOf implements Description {
    public final Entity _alternateA;
    public final Entity _alternateB;
    
    public AlternateOf (Entity alternateA, Entity alternateB) {
        _alternateA = alternateA;
        _alternateB = alternateB;
    }
}
