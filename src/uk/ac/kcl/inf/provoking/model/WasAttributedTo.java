package uk.ac.kcl.inf.provoking.model;

import uk.ac.kcl.inf.provoking.model.util.AttributeHolder;

public class WasAttributedTo extends AttributeHolder implements Description {
    private Agent _attributedTo;
    private Entity _attributed;
    
    public WasAttributedTo (Entity attributed, Agent attributedTo) {
        _attributedTo = attributedTo;
        _attributed = attributed;
    }
    
    public WasAttributedTo (Object identifier, Entity attributed, Agent attributedTo) {
        super (identifier);
        _attributedTo = attributedTo;
        _attributed = attributed;
    }

    public Agent getAttributedTo () {
        return _attributedTo;
    }

    public void setAttributedTo (Agent attributedTo) {
        _attributedTo = attributedTo;
    }

    public Entity getAttributed () {
        return _attributed;
    }

    public void setAttributed (Entity attributed) {
        _attributed = attributed;
    }
    
    public static WasAttributedTo reference (Object identifier) {
        WasAttributedTo reference = new WasAttributedTo (identifier, null, null);
        reference.setIsReference (true);
        return reference;
    }
}
