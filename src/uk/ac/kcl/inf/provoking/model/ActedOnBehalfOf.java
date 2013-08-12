package uk.ac.kcl.inf.provoking.model;

import uk.ac.kcl.inf.provoking.model.util.AttributeHolder;
import uk.ac.kcl.inf.provoking.model.util.Term;

public class ActedOnBehalfOf extends AttributeHolder implements Description {
    private static Term[] CLASS_TERMS = terms (Term.Delegation);
    private Agent _acter;
    private Agent _onBehalfOf;
    private Activity _activity;
    
    public ActedOnBehalfOf (Agent acter, Agent onBehalfOf, Activity activity) {
        _acter = acter;
        _onBehalfOf = onBehalfOf;
        _activity = activity;
    }

    public ActedOnBehalfOf (Agent acter, Agent onBehalfOf) {
        this (acter, onBehalfOf, (Activity) null);
    }
    
    public ActedOnBehalfOf (Object identifier, Agent acter, Agent onBehalfOf, Activity activity) {
        super (identifier);
        _acter = acter;
        _onBehalfOf = onBehalfOf;
        _activity = activity;
    }

    public ActedOnBehalfOf (Object identifier, Agent acter, Agent onBehalfOf) {
        this (identifier, acter, onBehalfOf, null);
    }
    
    public Agent getActer () {
        return _acter;
    }

    public Activity getActivity () {
        return _activity;
    }

    @Override
    public Term[] getClassTerms () {
        return CLASS_TERMS;
    }

    public Agent getOnBehalfOf () {
        return _onBehalfOf;
    }

    public void setActer (Agent acter) {
        _acter = acter;
    }

    public void setActivity (Activity activity) {
        _activity = activity;
    }    

    public void setOnBehalfOf (Agent onBehalfOf) {
        _onBehalfOf = onBehalfOf;
    }

    public static ActedOnBehalfOf reference (Object identifier) {
        ActedOnBehalfOf reference = new ActedOnBehalfOf (identifier, null, null);
        reference.setIsReference (true);
        return reference;
    }
}
