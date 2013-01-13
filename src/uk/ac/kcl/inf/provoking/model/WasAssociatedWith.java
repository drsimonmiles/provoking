package uk.ac.kcl.inf.provoking.model;

import uk.ac.kcl.inf.provoking.model.util.AttributeHolder;
import uk.ac.kcl.inf.provoking.model.util.Term;

public class WasAssociatedWith extends AttributeHolder implements Description {
    private Agent _responsible;
    private Activity _responsibleFor;
    private Entity _plan;
    private Role _role;
    
    public WasAssociatedWith (Activity responsibleFor, Agent responsible, Entity plan) {
        _responsible = responsible;
        _responsibleFor = responsibleFor;
        _plan = plan;
        _role = null;
    }

    public WasAssociatedWith (Activity responsibleFor, Agent responsible) {
        this (responsibleFor, responsible, null);
    }

    public WasAssociatedWith (Object identifier, Activity responsibleFor, Agent responsible, Entity plan) {
        super (identifier);
        _responsible = responsible;
        _responsibleFor = responsibleFor;
        _plan = plan;
        _role = null;
    }

    public WasAssociatedWith (Object identifier, Activity responsibleFor, Agent responsible) {
        this (identifier, responsibleFor, responsible, null);
    }

    public Agent getResponsible () {
        return _responsible;
    }

    public void setResponsible (Agent responsible) {
        _responsible = responsible;
    }

    public Activity getResponsibleFor () {
        return _responsibleFor;
    }

    public void setResponsibleFor (Activity responsibleFor) {
        _responsibleFor = responsibleFor;
    }

    public Entity getPlan () {
        return _plan;
    }

    public void setPlan (Entity plan) {
        _plan = plan;
    }
    
    public Role getRole () {
        return _role;
    }
    
    public void setRole (Role role) {
        _role = role;
    }
    
    public static WasAssociatedWith reference (Object identifier) {
        WasAssociatedWith reference = new WasAssociatedWith (identifier, null, null);
        reference.setIsReference (true);
        return reference;
    }

    private static Term[] CLASS_TERMS = terms (Term.Association);
    @Override
    public Term[] getClassTerms () {
        return CLASS_TERMS;
    }
}
