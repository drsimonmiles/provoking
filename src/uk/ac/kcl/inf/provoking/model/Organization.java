package uk.ac.kcl.inf.provoking.model;

import uk.ac.kcl.inf.provoking.model.util.Term;

public class Organization extends Agent {
    public Organization (Object identifier) {
        super (identifier);
        subtype (Term.Organization.uri ());
    }
}
