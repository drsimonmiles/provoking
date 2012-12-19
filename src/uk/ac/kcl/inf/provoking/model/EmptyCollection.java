package uk.ac.kcl.inf.provoking.model;

import uk.ac.kcl.inf.provoking.model.util.Term;

public class EmptyCollection extends Collection {
    public EmptyCollection (Object identifier) {
        super (identifier);
        subtype (Term.EmptyCollection.uri ());
    }
}
