package uk.ac.kcl.inf.provoking.model;

public class WasAttributedTo extends AttributeHolder implements Description {
    public final Agent _attributedTo;
    public final Entity _attributed;
    
    public WasAttributedTo (Entity attributed, Agent attributedTo) {
        _attributedTo = attributedTo;
        _attributed = attributed;
    }
}
