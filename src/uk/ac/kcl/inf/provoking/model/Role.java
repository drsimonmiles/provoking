package uk.ac.kcl.inf.provoking.model;

import uk.ac.kcl.inf.provoking.model.util.AttributeHolder;
import uk.ac.kcl.inf.provoking.model.util.Term;

public class Role extends AttributeHolder implements Description {
    public Role (Object identifier) {
        super (identifier);
    }
    
    public static Role reference (Object identifier) {
        Role reference = new Role (identifier);
        reference.setIsReference (true);
        return reference;
    }

    private static Term[] CLASS_TERMS = terms (Term.Role);
    @Override
    public Term[] getClassTerms () {
        return CLASS_TERMS;
    }
}
