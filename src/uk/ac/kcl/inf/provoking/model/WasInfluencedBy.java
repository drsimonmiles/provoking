package uk.ac.kcl.inf.provoking.model;

import uk.ac.kcl.inf.provoking.model.util.AttributeHolder;
import uk.ac.kcl.inf.provoking.model.util.Influenceable;
import uk.ac.kcl.inf.provoking.model.util.Term;

public class WasInfluencedBy extends AttributeHolder implements Description {
    private Influenceable _influencer;
    private Influenceable _influenced;
    
    public WasInfluencedBy (Influenceable informed, Influenceable informer) {
        _influencer = informed;
        _influenced = informer;
    }

    public WasInfluencedBy (Object identifier, Influenceable informed, Influenceable informer) {
        super (identifier);
        _influencer = informed;
        _influenced = informer;
    }

    public Influenceable getInfluenced () {
        return _influenced;
    }

    public void setInfluenced (Influenceable informed) {
        _influencer = informed;
    }

    public Influenceable getInfluencer () {
        return _influencer;
    }

    public void setInfluencer (Influenceable informer) {
        _influenced = informer;
    }

    public static WasInfluencedBy reference (Object identifier) {
        WasInfluencedBy reference = new WasInfluencedBy (identifier, null, null);
        reference.setIsReference (true);
        return reference;
    }

    private static Term[] CLASS_TERMS = terms (Term.Influence);
    @Override
    public Term[] getClassTerms () {
        return CLASS_TERMS;
    }
}
