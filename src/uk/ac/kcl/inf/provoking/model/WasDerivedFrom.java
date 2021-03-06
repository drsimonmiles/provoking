package uk.ac.kcl.inf.provoking.model;

import static uk.ac.kcl.inf.provoking.model.Description.toString;
import uk.ac.kcl.inf.provoking.model.util.AttributeHolder;
import uk.ac.kcl.inf.provoking.model.util.Term;

public class WasDerivedFrom extends AttributeHolder {
    private Entity _derived;
    private Entity _derivedFrom;
    private Activity _deriver;
    private WasGeneratedBy _generation;
    private Used _usage;

    public WasDerivedFrom (Entity derived, Entity derivedFrom, Activity deriver, WasGeneratedBy generation, Used usage) {
        _derived = derived;
        _derivedFrom = derivedFrom;
        _deriver = deriver;
        _generation = generation;
        _usage = usage;
    }

    public WasDerivedFrom (Entity derived, Entity derivedFrom, Activity deriver) {
        this (derived, derivedFrom, deriver, null, null);
    }

    public WasDerivedFrom (Entity derived, Entity derivedFrom) {
        this (derived, derivedFrom, null, null, null);
    }

    public WasDerivedFrom (Object identifier, Entity derived, Entity derivedFrom, Activity deriver, WasGeneratedBy generation, Used usage) {
        super (identifier);
        _derived = derived;
        _derivedFrom = derivedFrom;
        _deriver = deriver;
        _generation = generation;
        _usage = usage;
    }

    public WasDerivedFrom (Object identifier, Entity derived, Entity derivedFrom, Activity deriver) {
        this (identifier, derived, derivedFrom, deriver, null, null);
    }

    public WasDerivedFrom (Object identifier, Entity derived, Entity derivedFrom) {
        this (identifier, derived, derivedFrom, null, null, null);
    }

    public Entity getDerived () {
        return _derived;
    }

    public void setDerived (Entity derived) {
        _derived = derived;
    }

    public Entity getDerivedFrom () {
        return _derivedFrom;
    }

    public void setDerivedFrom (Entity derivedFrom) {
        _derivedFrom = derivedFrom;
    }

    public Activity getDeriver () {
        return _deriver;
    }

    public void setDeriver (Activity deriver) {
        _deriver = deriver;
    }

    public WasGeneratedBy getGeneration () {
        return _generation;
    }

    public void setGeneration (WasGeneratedBy generation) {
        _generation = generation;
    }

    public Used getUsage () {
        return _usage;
    }

    public void setUsage (Used usage) {
        _usage = usage;
    }

    @Override
    public String toString () {
        return toString (this, getAttributes (), new String[] {"derived from", "derived", "deriver", "generation", "usage"},
                         _derivedFrom, _derived, _deriver, _generation, _usage);
    }
    
    public static WasDerivedFrom reference (Object identifier) {
        WasDerivedFrom reference = new WasDerivedFrom (identifier, null, null);
        reference.setIsReference (true);
        return reference;
    }

    private static Term[] CLASS_TERMS = terms (Term.Derivation);
    @Override
    public Term[] getClassTerms () {
        return CLASS_TERMS;
    }
}
