package uk.ac.kcl.inf.provoking.model;

import uk.ac.kcl.inf.provoking.model.util.AttributeHolder;
import uk.ac.kcl.inf.provoking.model.util.Term;

public class Agent extends AttributeHolder implements Description {
    private Location _location;

    public Agent (Object identifier, Location location) {
        super (identifier);
        _location = location;
    }
    
    public Agent (Object identifier) {
        this (identifier, null);
    }
    
    public Location getLocation () {
        return _location;
    }
    
    public void setLocation (Location location) {
        _location = location;
    }

    public static Agent reference (Object identifier) {
        Agent reference = new Agent (identifier);
        reference.setIsReference (true);
        return reference;
    }

    private static Term[] CLASS_TERMS = terms (Term.Agent);
    @Override
    public Term[] getClassTerms () {
        return CLASS_TERMS;
    }
}
