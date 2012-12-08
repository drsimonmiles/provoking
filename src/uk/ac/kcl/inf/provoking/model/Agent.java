package uk.ac.kcl.inf.provoking.model;

import java.net.URI;
import uk.ac.kcl.inf.provoking.model.util.AttributeHolder;

public class Agent extends AttributeHolder implements Description {
    public Agent () {
    }
    
    public Agent (URI identifier, boolean isReference) {
        super (identifier, isReference);
    }
}
