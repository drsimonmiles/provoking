package uk.ac.kcl.inf.provoking.model;

import uk.ac.kcl.inf.provoking.model.util.Term;

public class Organization extends Agent {
    public Organization (Object identifier) {
        super (identifier);
        subtype (Term.Organization);
    }

    private static Term[] CLASS_TERMS = terms (Term.Agent, Term.Organization);
    @Override
    public Term[] getClassTerms () {
        return CLASS_TERMS;
    }
}
