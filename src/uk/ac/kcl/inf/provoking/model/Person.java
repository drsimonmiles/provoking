package uk.ac.kcl.inf.provoking.model;

import uk.ac.kcl.inf.provoking.model.util.Term;

public class Person extends Agent {
    public Person (Object identifier) {
        super (identifier);
        subtype (Term.Person.uri ());
    }
}
