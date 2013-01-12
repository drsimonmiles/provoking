package uk.ac.kcl.inf.provoking.model;

import uk.ac.kcl.inf.provoking.model.util.Term;

public class SoftwareAgent extends Agent {
    public SoftwareAgent (Object identifier) {
        super (identifier);
        subtype (Term.SoftwareAgent);
    }
    
    private static Term[] CLASS_TERMS = terms (Term.Agent, Term.SoftwareAgent);
    @Override
    public Term[] getClassTerms () {
        return CLASS_TERMS;
    }
}
