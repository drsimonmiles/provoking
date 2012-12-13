package uk.ac.kcl.inf.provoking.model;

import uk.ac.kcl.inf.provoking.model.util.Term;

public class Collection extends Entity {
    public Collection () {
        subtype (Term.Collection.uri ());
    }
    
    protected Collection (Object identifier) {
        super (identifier);
        subtype (Term.Collection.uri ());
    }
}
