package uk.ac.kcl.inf.provoking.model;

import uk.ac.kcl.inf.provoking.model.util.AttributeHolder;
import uk.ac.kcl.inf.provoking.model.util.Influenceable;
import uk.ac.kcl.inf.provoking.model.util.Term;

public class WasInfluencedBy extends AttributeHolder implements Description {
    private Influenceable _influencer;
    private Influenceable _influenced;
    
    public WasInfluencedBy (Influenceable influenced, Influenceable influencer) {
        _influenced = influenced;
        _influencer = influencer;
    }

    public WasInfluencedBy (Object identifier, Influenceable influenced, Influenceable influencer) {
        super (identifier);
        _influencer = influencer;
        _influenced = influenced;
    }

    public Influenceable getInfluenced () {
        return _influenced;
    }

    public void setInfluenced (Influenceable influenced) {
        _influenced = influenced;
    }

    public Influenceable getInfluencer () {
        return _influencer;
    }

    public void setInfluencer (Influenceable influencer) {
        _influencer = influencer;
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
