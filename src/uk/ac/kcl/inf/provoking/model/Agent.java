package uk.ac.kcl.inf.provoking.model;

import uk.ac.kcl.inf.provoking.model.util.AttributeHolder;

public class Agent extends AttributeHolder implements Description {
    public Agent (Object identifier) {
        super (identifier);
    }

    public static Agent reference (Object identifier) {
        Agent reference = new Agent (identifier);
        reference.setIsReference (true);
        return reference;
    }
}
