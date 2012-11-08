package uk.ac.kcl.inf.provoking.model;

public class ActedOnBehalfOf implements Description {
    private Agent _acter;
    private Agent _onBehalfOf;
    private Activity _activity;
    
    public ActedOnBehalfOf (Agent acter, Agent onBehalfOf, Activity activity) {
        _acter = acter;
        _onBehalfOf = onBehalfOf;
        _activity = activity;
    }

    public ActedOnBehalfOf (Agent acter, Agent onBehalfOf) {
        _acter = acter;
        _onBehalfOf = onBehalfOf;
        _activity = null;
    }
    
    public ActedOnBehalfOf () {
        _acter = null;
        _onBehalfOf = null;
        _activity = null;
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
}
