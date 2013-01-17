package uk.ac.kcl.inf.provoking.model;

import uk.ac.kcl.inf.provoking.model.util.AttributeHolder;
import uk.ac.kcl.inf.provoking.model.util.Influenceable;
import uk.ac.kcl.inf.provoking.model.util.Term;

public class Entity extends AttributeHolder implements Description, Influenceable {
    private Location _location;
    
    public Entity (Object identifier, Location location) {
        super (identifier);
        _location = location;
    }
    
    public Entity (Object identifier) {
        this (identifier, null);
    }

    public Location getLocation () {
        return _location;
    }
    
    public void setLocation (Location location) {
        _location = location;
    }    
    
    public static Entity reference (Object identifier) {
        Entity reference = new Entity (identifier);
        reference.setIsReference (true);
        return reference;
    }

    private static Term[] CLASS_TERMS = terms (Term.Entity);
    @Override
    public Term[] getClassTerms () {
        return CLASS_TERMS;
    }
}
