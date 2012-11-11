package uk.ac.kcl.inf.provoking.model;

import java.net.URI;
import uk.ac.kcl.inf.provoking.model.util.AttributeHolder;

public class Entity extends AttributeHolder implements Description {
    public Entity () {
    }
    
    protected Entity (URI identifier, boolean isReference) {
        super (identifier, !isReference);
    }
}
