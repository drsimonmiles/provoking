package uk.ac.kcl.inf.provoking.model;

import uk.ac.kcl.inf.provoking.model.util.AttributeHolder;

public class Entity extends AttributeHolder implements Description {
    public Entity () {
    }
    
    protected Entity (Object identifier) {
        super (identifier);
    }
    
    public static Entity reference (Object identifier) {
        Entity reference = new Entity (identifier);
        reference.setIsReference (true);
        return reference;
    }
}
