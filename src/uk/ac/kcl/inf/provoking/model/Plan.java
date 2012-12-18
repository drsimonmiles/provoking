package uk.ac.kcl.inf.provoking.model;

import uk.ac.kcl.inf.provoking.model.util.Term;

public class Plan extends Entity {
    public Plan () {
        subtype (Term.Plan.uri ());
    }
    
    public Plan (Object identifier) {
        super (identifier);
        subtype (Term.Plan.uri ());
    }
}
