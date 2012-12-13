package uk.ac.kcl.inf.provoking.model;

import uk.ac.kcl.inf.provoking.model.util.AttributeHolder;

public class ActedOnBehalfOf extends AttributeHolder implements Description {
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

    public void setActer (Agent acter) {
        _acter = acter;
    }

    public Agent getOnBehalfOf () {
        return _onBehalfOf;
    }

    public void setOnBehalfOf (Agent onBehalfOf) {
        _onBehalfOf = onBehalfOf;
    }

    public Activity getActivity () {
        return _activity;
    }

    public void setActivity (Activity activity) {
        _activity = activity;
    }    

    public static ActedOnBehalfOf reference (Object identifier) {
        ActedOnBehalfOf reference = new ActedOnBehalfOf (identifier, null, null);
        reference.setIsReference (true);
        return reference;
    }
}
