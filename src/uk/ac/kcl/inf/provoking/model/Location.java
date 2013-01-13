package uk.ac.kcl.inf.provoking.model;

import uk.ac.kcl.inf.provoking.model.util.AttributeHolder;
import uk.ac.kcl.inf.provoking.model.util.Term;

public class Location extends AttributeHolder implements Description {
    public Location (Object identifier) {
        super (identifier);
    }
    
    public static Location reference (Object identifier) {
        Location reference = new Location (identifier);
        reference.setIsReference (true);
        return reference;
    }

    private static Term[] CLASS_TERMS = terms (Term.Location);
    @Override
    public Term[] getClassTerms () {
        return CLASS_TERMS;
    }
}
