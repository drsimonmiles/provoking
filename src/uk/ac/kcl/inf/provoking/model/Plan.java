package uk.ac.kcl.inf.provoking.model;

import uk.ac.kcl.inf.provoking.model.util.Term;

public class Plan extends Entity {
    public Plan (Object identifier) {
        super (identifier);
    }
    
    private static Term[] CLASS_TERMS = terms (Term.Entity, Term.Plan);
    @Override
    public Term[] getClassTerms () {
        return CLASS_TERMS;
    }
    
    @Override
    public Term getClassTerm () {
        return Term.Plan;
    }
}
