package uk.ac.kcl.inf.provoking.model;

import uk.ac.kcl.inf.provoking.model.util.Term;

public class Collection extends Entity {
    public Collection (Object identifier) {
        super (identifier);
        subtype (Term.Collection);
    }

    private static Term[] CLASS_TERMS = terms (Term.Entity, Term.Collection);
    @Override
    public Term[] getClassTerms () {
        return CLASS_TERMS;
    }
}
