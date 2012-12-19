package uk.ac.kcl.inf.provoking.model;

import uk.ac.kcl.inf.provoking.model.util.Term;

public class Collection extends Entity {
    public Collection (Object identifier) {
        super (identifier);
        subtype (Term.Collection.uri ());
    }
}
