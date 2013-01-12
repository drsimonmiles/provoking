package uk.ac.kcl.inf.provoking.model;

import uk.ac.kcl.inf.provoking.model.util.AttributeHolder;
import uk.ac.kcl.inf.provoking.model.util.Term;

public class Agent extends AttributeHolder implements Description {
    private static Term[] CLASS_TERMS = terms (Term.Delegation);

    public Agent (Object identifier) {
        super (identifier);
    }

    public static Agent reference (Object identifier) {
        Agent reference = new Agent (identifier);
        reference.setIsReference (true);
        return reference;
    }

    @Override
    public Term[] getClassTerms () {
        return CLASS_TERMS;
    }
}
