package uk.ac.kcl.inf.provoking.model;

import uk.ac.kcl.inf.provoking.model.util.Term;

public class EmptyCollection extends Collection {
    public EmptyCollection (Object identifier) {
        super (identifier);
    }

    private static Term[] CLASS_TERMS = terms (Term.Entity, Term.Collection, Term.EmptyCollection);
    @Override
    public Term[] getClassTerms () {
        return CLASS_TERMS;
    }
    
    @Override
    public Term getClassTerm () {
        return Term.EmptyCollection;
    }
}
