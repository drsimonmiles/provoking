package uk.ac.kcl.inf.provoking.model;

import uk.ac.kcl.inf.provoking.model.util.AttributeHolder;
import uk.ac.kcl.inf.provoking.model.util.Term;

public class Entity extends AttributeHolder implements Description {
    public Entity (Object identifier) {
        super (identifier);
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
