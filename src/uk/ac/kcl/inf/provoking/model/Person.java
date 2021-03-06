package uk.ac.kcl.inf.provoking.model;

import uk.ac.kcl.inf.provoking.model.util.Term;

public class Person extends Agent {
    public Person (Object identifier) {
        super (identifier);
    }
    
    private static Term[] CLASS_TERMS = terms (Term.Agent, Term.Person);
    @Override
    public Term[] getClassTerms () {
        return CLASS_TERMS;
    }
    
    @Override
    public Term getClassTerm () {
        return Term.Person;
    }
}
