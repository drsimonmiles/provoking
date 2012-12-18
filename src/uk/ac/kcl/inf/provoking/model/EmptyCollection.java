package uk.ac.kcl.inf.provoking.model;

import uk.ac.kcl.inf.provoking.model.util.Term;

public class EmptyCollection extends Collection {
    public EmptyCollection () {
        subtype (Term.EmptyCollection.uri ());
    }
    
    public EmptyCollection (Object identifier) {
        super (identifier);
        subtype (Term.EmptyCollection.uri ());
    }
}
