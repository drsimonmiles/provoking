package uk.ac.kcl.inf.provoking.model;

import java.util.Date;
import uk.ac.kcl.inf.provoking.model.util.Term;

public class WasGeneratedBy extends InstantaneousEvent {
    private Activity _generater;
    private Entity _generated;
    
    public WasGeneratedBy (Entity generated, Activity generater) {
        _generater = generater;
        _generated = generated;
    }

    public WasGeneratedBy (Entity generated, Activity generater, Date time) {
        super (time);
        _generater = generater;
        _generated = generated;
    }

    public WasGeneratedBy (Object identifier, Entity generated, Activity generater) {
        super (identifier);
        _generater = generater;
        _generated = generated;
    }

    public WasGeneratedBy (Object identifier, Entity generated, Activity generater, Date time) {
        super (identifier);
        _generater = generater;
        _generated = generated;
        setTime (time);
    }
    
    public Activity getGenerater () {
        return _generater;
    }
    
    public void setGenerater (Activity generater) {
        _generater = generater;
    }
    
    public Entity getGenerated () {
        return _generated;
    }
    
    public void setGenerated (Entity generated) {
        _generated = generated;
    }

    public static WasGeneratedBy reference (Object identifier) {
        WasGeneratedBy reference = new WasGeneratedBy (identifier, null, null);
        reference.setIsReference (true);
        return reference;
    }

    private static Term[] CLASS_TERMS = terms (Term.Generation);
    @Override
    public Term[] getClassTerms () {
        return CLASS_TERMS;
    }
}